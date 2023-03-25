package com.sandrolabs.serverless.lambda

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DummyFunctionTest {

  @Test
  fun dummy() {
    assertThat("foo").isNotEmpty
  }
}
