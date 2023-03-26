package com.sandrolabs.serverless.lambda.util

import com.amazonaws.services.lambda.runtime.LambdaLogger
import kotlin.reflect.KClass

class LambdaTestLogger<T : Any>(
  private val functionType: KClass<T>,
) : LambdaLogger {
  override fun log(message: String) = println("${functionType.qualifiedName}: $message")
  override fun log(message: ByteArray) = log(message.toString())
}
