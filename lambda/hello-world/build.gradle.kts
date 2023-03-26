plugins {
  id("com.sandrolabs.serverless.kotlin-aws-lambda-conventions")
}

dependencies {
  api(project(":lambda:lambda-utils"))
  testImplementation(testFixtures(project(":lambda:lambda-utils")))
}

