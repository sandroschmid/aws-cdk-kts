# AWS CDK with Kotlin

Serverless "Hello World"-API written in Kotlin and using IaC using AWS CDK.

## Project structure

    ├── buildSrc               # Shared Gradle build scripts
    ├── infrastructure         # Infrastructure code via CDK (Java)
    ├── lambda                 # Holds business logic in AWS lambda functions
    │   ├── hello-world        # Lambda function module
    └── ...

## Build AWS infrastructure

[Getting started with CDK](https://docs.aws.amazon.com/cdk/v2/guide/getting_started.html)

Build fat-jars for all Lambda functions

```bash
./gradlew shadowJar
```

### Go to the infrastructure/AWS CDK module

```bash
cd infrastructure
```

### Synthesize AWS CloudFormation template

```bash
cdk synth
```

### Deploy AWS CloudFormation stack

```bash
cdk bootstrap # for first-time use
cdk deploy
```

## Integration with SAM

[Getting started with SAM and CDK](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-cdk-getting-started.html)

### Generate test events

```bash
sam local generate-event apigateway http-api-proxy --method="GET" --path="hello-world" --body="eyAibmFtZSI6ICJEZXZlbG9wZXIiIH0=" > ../lambda/hello-world/src/test/resources/events/hello-world.json
```

### Invoke function locally with SAM

```bash
sam local invoke hello-world-handler -t ./cdk.out/demo-stack.template.json -e ../lambda/hello-world/src/test/resources/events/request-default.json
sam local invoke hello-world-handler -t ./cdk.out/demo-stack.template.json -e ../lambda/hello-world/src/test/resources/events/request-body.json
sam local invoke hello-world-handler -t ./cdk.out/demo-stack.template.json -e ../lambda/hello-world/src/test/resources/events/request-query-param.json
```

### Start API locally

```bash
sam local start-api -t ./cdk.out/demo-stack.template.json
```
