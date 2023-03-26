package com.sandrolabs.serverless.lambda.helloworld

import kotlinx.serialization.Serializable

@Serializable
data class RequestBody(val name: String? = null)
