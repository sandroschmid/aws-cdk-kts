package com.sandrolabs.serverless.lambda.util

import kotlinx.serialization.Serializable

@Serializable
data class ErrorDto(
  val statusCode: Int,
  val message: String,
)
