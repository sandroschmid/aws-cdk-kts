plugins {
  id("com.sandrolabs.serverless.kotlin-application-conventions")
}

application {
  mainClass.set("com.sandrolabs.serverless.cdk.DemoApp")
}

dependencies {
  val cdkVersion = "2.0.0"
  implementation("software.amazon.awscdk:aws-cdk-lib:$cdkVersion")
  implementation("software.constructs:constructs:10.0.0")
}
