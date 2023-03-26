plugins {
  kotlin("jvm")
  `java-test-fixtures`
}

repositories {
  mavenCentral()
}

kotlin {
  jvmToolchain {
    languageVersion.set(JavaLanguageVersion.of(11))
    vendor.set(JvmVendorSpec.AMAZON)
  }
}

dependencies {
  constraints {
    implementation("com.amazonaws:aws-java-sdk:1.12.434")
  }

  testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
  testImplementation("org.assertj:assertj-core:3.24.2")
  testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
  testImplementation("org.mockito:mockito-junit-jupiter:5.2.0")
}

tasks.named<Test>("test") {
  useJUnitPlatform()
}
