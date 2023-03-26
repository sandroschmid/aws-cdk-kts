package com.sandrolabs.serverless.lambda.util.exception

class NotFoundRequestException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)
