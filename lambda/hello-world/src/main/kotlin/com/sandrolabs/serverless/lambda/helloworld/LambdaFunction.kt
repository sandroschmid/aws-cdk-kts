package com.sandrolabs.serverless.lambda.helloworld

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.sandrolabs.serverless.lambda.util.LambdaApiHandler

class LambdaFunction : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

  override fun handleRequest(request: APIGatewayProxyRequestEvent, context: Context): APIGatewayProxyResponseEvent {
    val handler = LambdaApiHandler(request, context)

    return handler.handle {
      val name = handler.getQueryParam("name")
        ?: handler.getBody<RequestBody>()?.name
        ?: "World"

      val message = "Hello, $name!"
      handler.log("Received name '$name', responding with message '$message'")
      ResponseBody(message)
    }
  }
}
