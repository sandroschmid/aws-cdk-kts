package com.sandrolabs.serverless.lambda.util

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.sandrolabs.serverless.lambda.util.exception.BadRequestException
import com.sandrolabs.serverless.lambda.util.exception.NotFoundRequestException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

class LambdaApiHandler(
  val request: APIGatewayProxyRequestEvent,
  val context: Context,
) {

  val base64Decoder: Base64.Decoder = Base64.getDecoder()
  val json = Json { encodeDefaults = true }

  fun log(message: String) = context.logger.log("$message\n")

  fun getQueryParam(key: String) =
    if (request.queryStringParameters != null)
      request.queryStringParameters[key]
    else null

  inline fun <reified T> getBody(): T? {
    if (request.body.isNullOrEmpty()) return null
    try {
      val jsonBody = if (request.isBase64Encoded)
        base64Decoder.decode(request.body).decodeToString()
      else request.body

      return json.decodeFromString(jsonBody)
    } catch (ex: IllegalArgumentException) {
      throw BadRequestException("Could not decode request body", ex)
    }
  }

  inline fun <reified T : Any> handle(getResponse: () -> T?): APIGatewayProxyResponseEvent {
    val responseBody: T? = try {
      getResponse()
    } catch (ex: BadRequestException) {
      return error(HttpStatusCode.BAD_REQUEST, "Could not decode request body", ex)
    } catch (ex: NotFoundRequestException) {
      return error(HttpStatusCode.NOT_FOUND, "Could not find requested entity", ex)
    } catch (ex: Exception) {
      return error(HttpStatusCode.SERVER_ERROR, "Unknown error", ex)
    }

    return respond(HttpStatusCode.OK, responseBody)
  }

  inline fun <reified T : Any> respond(httpStatusCode: HttpStatusCode, body: T?): APIGatewayProxyResponseEvent {
    val responseStatus = if (body != null) httpStatusCode else HttpStatusCode.NO_CONTENT

    return APIGatewayProxyResponseEvent()
      .withHeaders(mapOf("Content-Type" to "application/json"))
      .withStatusCode(responseStatus.intValue)
      .withBody(if (body != null) json.encodeToString(body) else null)
      .withIsBase64Encoded(false)
  }

  fun error(httpStatusCode: HttpStatusCode, message: String, cause: Throwable): APIGatewayProxyResponseEvent {
    val errorDto = ErrorDto(httpStatusCode.intValue, message)
    log("API-Error(${errorDto.statusCode}): ${errorDto.message}")
    log(cause.stackTraceToString())
    return respond(httpStatusCode, errorDto)
  }
}
