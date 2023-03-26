package com.sandrolabs.serverless.cdk

import software.amazon.awscdk.*
import software.amazon.awscdk.services.apigateway.LambdaIntegration
import software.amazon.awscdk.services.apigateway.RestApi
import software.amazon.awscdk.services.lambda.Code
import software.amazon.awscdk.services.lambda.Function
import software.amazon.awscdk.services.lambda.Runtime
import software.constructs.Construct
import software.amazon.awscdk.services.s3.assets.AssetOptions as S3AssetOptions

class DemoStack(
  scope: Construct,
  id: String,
  props: StackProps,
) : Stack(scope, id, props) {
  init {
    val mavenLocalVolume = DockerVolume.builder()
      .hostPath(System.getProperty("user.home") + "/.m2/")
      .containerPath("/root/.m2/")
      .build()

    val helloWorldPackagingInstructions = listOf(
      "/bin/sh",
      "-c",
      "gradle :lambda:hello-world:shadowJar " +
          "&& cp /asset-input/lambda/hello-world/build/libs/hello-world-all.jar /asset-output/"
    )

    val helloWorldBundlingOptions = BundlingOptions.builder()
      .command(helloWorldPackagingInstructions)
      .image(Runtime.JAVA_11.bundlingImage)
      .volumes(listOf(mavenLocalVolume))
      .user("root")
      .outputType(BundlingOutput.ARCHIVED)

    val helloWorldAsset = S3AssetOptions.builder()
      .bundling(helloWorldBundlingOptions.command(helloWorldPackagingInstructions).build())
      .build()

    val helloWorldFunction = Function.Builder.create(this, "hello-world-handler")
      .code(Code.fromAsset("../lambda", helloWorldAsset))
      .handler("com.sandrolabs.serverless.lambda.HelloWorldFunction")
      .timeout(Duration.seconds(5))
      .memorySize(256)
      .runtime(Runtime.JAVA_11)
      .build()

    val api = RestApi.Builder.create(this, "aws-cdk-kts-api")
      .restApiName("aws-cdk-kts")
      .description("Write and deploy AWS serverless code with Kotlin")
      .build()

    val helloWorldIntegration = LambdaIntegration.Builder.create(helloWorldFunction)
      .allowTestInvoke(true)
      .build()

    val helloWorldResource = api.root.addResource("hello-world")
    helloWorldResource.addMethod("GET", helloWorldIntegration)
    helloWorldResource.addMethod("POST", helloWorldIntegration)
  }
}
