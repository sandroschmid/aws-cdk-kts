package com.sandrolabs.serverless.cdk

import software.amazon.awscdk.App
import software.amazon.awscdk.StackProps

object DemoApp {
  @JvmStatic
  fun main(args: Array<String>) {
    val app = App()
    val stackProps = StackProps.builder().build()
    DemoStack(app, "demo-stack", stackProps)
    app.synth()
  }
}
