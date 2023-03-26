plugins {
  id("com.sandrolabs.serverless.kotlin-aws-lambda-conventions")
}

dependencies {
  testFixturesImplementation("com.amazonaws:aws-lambda-java-core:1.2.2")
  testFixturesImplementation("com.amazonaws:aws-lambda-java-events:3.11.1")
}
