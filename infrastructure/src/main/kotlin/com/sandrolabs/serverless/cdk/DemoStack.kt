package com.sandrolabs.serverless.cdk

import software.amazon.awscdk.Duration
import software.amazon.awscdk.Stack
import software.amazon.awscdk.StackProps
import software.amazon.awscdk.services.apigateway.LambdaIntegration
import software.amazon.awscdk.services.apigateway.RestApi
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
    val api = RestApi.Builder.create(this, "aws-cdk-kts-api")
      .restApiName("aws-cdk-kts")
      .description("Write and deploy AWS serverless code with Kotlin")
      .build()

    val helloWorldFunction = Function.Builder.create(this, "hello-world-handler")
      .code(Code.fromAsset("../lambda/hello-world/build/libs/hello-world-all.jar"))
      .handler("com.sandrolabs.serverless.lambda.helloworld.LambdaFunction")
      .timeout(Duration.seconds(5))
      .memorySize(256)
      .runtime(Runtime.JAVA_11)
      .build()

    val helloWorldIntegration = LambdaIntegration.Builder.create(helloWorldFunction)
      .allowTestInvoke(true)
      .build()

    val helloWorldResource = api.root.addResource("hello-world")
    helloWorldResource.addMethod("GET", helloWorldIntegration)
    helloWorldResource.addMethod("POST", helloWorldIntegration)
  }
}
