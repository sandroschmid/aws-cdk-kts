package com.sandrolabs.serverless.lambda.helloworld

import kotlinx.serialization.Serializable

@Serializable
data class ResponseBody(val message: String)
