import gradle.kotlin.dsl.accessors._f0afde12dfa870152cc2b3595196a9e5.implementation

plugins {
  id("com.sandrolabs.serverless.kotlin-library-conventions")
  kotlin("plugin.serialization")
  id("com.github.johnrengelman.shadow")
}

val lambdaCoreVersion = "1.2.2"
val lambdaEventsVersion = "3.11.1"
dependencies {
  constraints {
    implementation("com.amazonaws:aws-lambda-java-core:$lambdaCoreVersion")
    implementation("com.amazonaws:aws-lambda-java-events:$lambdaEventsVersion")
  }

  implementation("com.amazonaws:aws-lambda-java-core:$lambdaCoreVersion")
  implementation("com.amazonaws:aws-lambda-java-events:$lambdaEventsVersion")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
}
