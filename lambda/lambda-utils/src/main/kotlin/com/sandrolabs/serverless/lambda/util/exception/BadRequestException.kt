package com.sandrolabs.serverless.lambda.util.exception

class BadRequestException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)
