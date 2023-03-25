package com.sandrolabs.serverless.lambda

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class HelloWorldFunction : RequestHandler<HelloWorldInput, String> {

  private val json = Json { encodeDefaults = true }

  override fun handleRequest(input: HelloWorldInput, context: Context): String {
    val logger = context.logger
    val name = input.name ?: "World"
    val message = "Hello, $name!"
    logger.log("Received name $name, responding with message '$message'\n")

    return json.encodeToString(HelloWorldOutput(message))
  }
}
