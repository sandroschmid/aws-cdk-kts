package com.sandrolabs.serverless.lambda.helloworld

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.sandrolabs.serverless.lambda.util.LambdaTestLogger
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class LambdaFunctionTest {

  @Mock
  private lateinit var context: Context

  private lateinit var function: LambdaFunction

  @BeforeEach
  fun setUp() {
    function = LambdaFunction()
    whenever(context.logger).thenReturn(LambdaTestLogger(LambdaFunction::class))
  }

  @Test
  fun `hello world with default`() {
    val request = mock<APIGatewayProxyRequestEvent>()
    val response = function.handleRequest(request, context)
    assertThat(response.body).isEqualToIgnoringWhitespace(
      """
      { "message": "Hello, World!" }
      """.trimIndent()
    )
  }

  @Test
  fun `hello world with query param`() {
    val request = mock<APIGatewayProxyRequestEvent>()
    whenever(request.queryStringParameters).thenReturn(mapOf("name" to "Developer"))

    val response = function.handleRequest(request, context)
    assertThat(response.body).isEqualToIgnoringWhitespace(
      """
      { "message": "Hello, Developer!" }
    """.trimIndent()
    )
  }

  @Test
  fun `hello world with JSON request body`() {
    val request = mock<APIGatewayProxyRequestEvent>()
    whenever(request.body).thenReturn(
      """
      { "name": "Developer" }
      """.trimIndent()
    )
    whenever(request.isBase64Encoded).thenReturn(false)

    val response = function.handleRequest(request, context)
    assertThat(response.body).isEqualToIgnoringWhitespace(
      """
      { "message": "Hello, Developer!" }
      """.trimIndent()
    )
  }

  @Test
  fun `hello world with Base64-encoded request body`() {
    val request = mock<APIGatewayProxyRequestEvent>()
    whenever(request.body).thenReturn("eyAibmFtZSI6ICJEZXZlbG9wZXIiIH0=")
    whenever(request.isBase64Encoded).thenReturn(true)

    val response = function.handleRequest(request, context)
    assertThat(response.body).isEqualToIgnoringWhitespace(
      """
      { "message": "Hello, Developer!" }
      """.trimIndent()
    )
  }
}
