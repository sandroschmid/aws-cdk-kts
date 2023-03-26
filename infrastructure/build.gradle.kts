plugins {
  id("com.sandrolabs.serverless.kotlin-application-conventions")
}

application {
  mainClass.set("com.sandrolabs.serverless.cdk.DemoApp")
}

dependencies {
  implementation("software.amazon.awscdk:aws-cdk-lib:2.0.0")
  implementation("software.constructs:constructs:10.1.280")
}
