package com.sandrolabs.serverless.lambda.util

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.sandrolabs.serverless.lambda.util.exception.BadRequestException
import com.sandrolabs.serverless.lambda.util.exception.NotFoundRequestException
import kotlinx.serialization.Serializable
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever

@Serializable
data class RequestBody(val name: String)

@ExtendWith(MockitoExtension::class)
class LambdaApiHandlerTest {

  @Mock
  private lateinit var request: APIGatewayProxyRequestEvent

  @Mock
  private lateinit var context: Context

  private lateinit var impl: LambdaApiHandler

  @BeforeEach
  fun setUp() {
    impl = LambdaApiHandler(request, context)
  }

  @Test
  fun `get query param - missing`() {
    assertThat(impl.getQueryParam("foo")).isNull()
  }

  @Test
  fun `get query param`() {
    whenever(request.queryStringParameters).thenReturn(mapOf("foo" to "bar"))
    assertThat(impl.getQueryParam("foo")).isEqualTo("bar")
  }

  @Test
  fun `get body - base64`() {
    whenever(request.body).thenReturn("eyAibmFtZSI6ICJEZXZlbG9wZXIiIH0=")
    whenever(request.isBase64Encoded).thenReturn(true)
    assertThat(impl.getBody<RequestBody>()).isEqualTo(RequestBody("Developer"))
  }

  @Test
  fun `get body - JSON`() {
    whenever(request.body).thenReturn(
      """
      { "name": "Developer" }
    """.trimIndent()
    )
    whenever(request.isBase64Encoded).thenReturn(false)
    assertThat(impl.getBody<RequestBody>()).isEqualTo(RequestBody("Developer"))
  }

  @Test
  fun `get body - invalid`() {
    whenever(request.body).thenReturn("invalid")
    assertThatThrownBy { impl.getBody<RequestBody>() }
      .isInstanceOf(BadRequestException::class.java)
  }

  @Test
  fun `handle - ok`() {
    val actual = impl.handle { "test response" }
    assertThat(actual.statusCode).isEqualTo(HttpStatusCode.OK.intValue)
    assertThat(actual.isBase64Encoded).isFalse
    assertThat(actual.body).contains("test response")
  }

  @Test
  fun `handle - no content`() {
    val actual = impl.handle { null }
    assertThat(actual.statusCode).isEqualTo(HttpStatusCode.NO_CONTENT.intValue)
    assertThat(actual.isBase64Encoded).isFalse
    assertThat(actual.body).isNull()
  }

  @Test
  fun `handle - bad request`() {
    whenever(context.logger).thenReturn(LambdaTestLogger(LambdaApiHandler::class))
    val actual = impl.handle { throw BadRequestException("test") }
    assertThat(actual.statusCode).isEqualTo(HttpStatusCode.BAD_REQUEST.intValue)
  }

  @Test
  fun `handle - not found`() {
    whenever(context.logger).thenReturn(LambdaTestLogger(LambdaApiHandler::class))
    val actual = impl.handle { throw NotFoundRequestException("test") }
    assertThat(actual.statusCode).isEqualTo(HttpStatusCode.NOT_FOUND.intValue)
  }

  @Test
  fun `handle - server error`() {
    whenever(context.logger).thenReturn(LambdaTestLogger(LambdaApiHandler::class))
    val actual = impl.handle { throw RuntimeException("test") }
    assertThat(actual.statusCode).isEqualTo(HttpStatusCode.SERVER_ERROR.intValue)
  }

  @Test
  fun `create error`() {
    whenever(context.logger).thenReturn(LambdaTestLogger(LambdaApiHandler::class))
    val actual = impl.error(HttpStatusCode.BAD_REQUEST, "this is a bad request", RuntimeException("cause"))
    assertThat(actual.statusCode).isEqualTo(400)
    assertThat(actual.isBase64Encoded).isFalse
    assertThat(actual.body).contains("this is a bad request")
  }
}