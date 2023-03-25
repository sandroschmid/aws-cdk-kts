# AWS CDK with Kotlin

## Project structure

    ├── buildSrc               # Shared Gradle build scripts
    ├── infrastructure         # Infrastructure code via CDK (Java)
    ├── lambda                 # Holds business logic in AWS lambda functions
    │   ├── hello-world        # Lambda function module
    └── ...

## Build AWS infrastructure

Build fat-jars for all Lambda functions

```bash
./gradlew shadowJar
```

Go to the infrastructure/AWS CDK module

```bash
cd infrastructure
```

Synthesize AWS resources for CloudFormation

```bash
cdk synth
```
