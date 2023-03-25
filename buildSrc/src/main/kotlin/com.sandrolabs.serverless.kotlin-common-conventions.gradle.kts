plugins {
  kotlin("jvm")
}

repositories {
  mavenCentral()
}

dependencies {
  constraints {
    implementation("com.amazonaws:aws-java-sdk:1.12.434")
  }

  testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
  testImplementation("org.assertj:assertj-core:3.24.2")
}

tasks.named<Test>("test") {
  useJUnitPlatform()
}
