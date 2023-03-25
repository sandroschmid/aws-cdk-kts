plugins {
  id("com.sandrolabs.serverless.kotlin-library-conventions")
}

dependencies {
  implementation("com.amazonaws:aws-lambda-java-core:1.2.2")
  implementation("com.amazonaws:aws-lambda-java-events:3.11.1")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
}
