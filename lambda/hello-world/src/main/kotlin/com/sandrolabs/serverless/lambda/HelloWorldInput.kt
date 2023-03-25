package com.sandrolabs.serverless.lambda

import kotlinx.serialization.Serializable

@Serializable
data class HelloWorldInput(val name: String? = null)
