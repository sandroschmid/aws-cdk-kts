package com.sandrolabs.serverless.cdk

import software.amazon.awscdk.Duration
import software.amazon.awscdk.Stack
import software.amazon.awscdk.StackProps
import software.amazon.awscdk.services.lambda.Code
import software.amazon.awscdk.services.lambda.Function
import software.amazon.awscdk.services.lambda.Runtime
import software.constructs.Construct

class DemoStack(
  scope: Construct,
  id: String,
  props: StackProps,
) : Stack(scope, id, props) {
  init {
    Function.Builder.create(this, "hello-world-handler")
      .code(Code.fromAsset("../lambda/hello-world/build/libs/hello-world-all.jar"))
      .handler("com.sandrolabs.serverless.lambda.HelloWorldFunction")
      .timeout(Duration.seconds(5))
      .memorySize(512)
      .runtime(Runtime.JAVA_11)
      .build()
  }
}
