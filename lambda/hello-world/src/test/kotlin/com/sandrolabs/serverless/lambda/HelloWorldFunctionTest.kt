package com.sandrolabs.serverless.lambda

import com.amazonaws.services.lambda.runtime.Context
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class HelloWorldFunctionTest {

  @Mock
  private lateinit var context: Context

  private lateinit var function: HelloWorldFunction

  @BeforeEach
  fun setUp() {
    function = HelloWorldFunction()
  }

  @Test
  fun `hello world with default`() {
    whenever(context.logger).thenReturn(LambdaTestLogger(HelloWorldFunction::class))

    val response = function.handleRequest(HelloWorldInput(), context)
    assertThat(response).isEqualToIgnoringWhitespace(
      """
      {
        "message": "Hello, World!"
      }
      """.trimIndent()
    )
  }

  @Test
  fun `hello world with name`() {
    whenever(context.logger).thenReturn(LambdaTestLogger(HelloWorldFunction::class))

    val response = function.handleRequest(HelloWorldInput("Developer"), context)
    assertThat(response).isEqualToIgnoringWhitespace(
      """
      {
        "message": "Hello, Developer!"
      }
      """.trimIndent()
    )
  }
}
