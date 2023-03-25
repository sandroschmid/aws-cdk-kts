package com.sandrolabs.serverless.lambda

import kotlinx.serialization.Serializable

@Serializable
data class HelloWorldOutput(val message: String)
