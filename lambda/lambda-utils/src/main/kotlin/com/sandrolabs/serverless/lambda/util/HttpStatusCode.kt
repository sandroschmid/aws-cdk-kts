package com.sandrolabs.serverless.lambda.util

enum class HttpStatusCode(val intValue: Int) {
  OK(200),
  NO_CONTENT(201),
  BAD_REQUEST(400),
  NOT_FOUND(404),
  SERVER_ERROR(500),
}
