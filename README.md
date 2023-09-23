# infra-gitpod-template

- Template for create repos to use with Gitpod

## Steps to run
```
wget https://github.com/graalvm/graalvm-ce-dev-builds/releases/download/24.0.0-dev-20230907_0337/graalvm-community-java21-linux-amd64-dev.tar.gz
tar -zxvf graalvm-community-java21-linux-amd64-dev.tar.gz 
sdk install java graalvm-ce-21g /workspace/graalvm-community-openjdk-21+35.1
sdk default java graalvm-ce-21g
java -version
sdk use java graalvm-ce-21g
java -version
cd ../test-java21-gp/
./gradlew bootRun
./gradlew nativeCompile
./build/native/nativeCompile/demo
```
- https://spring.io/blog/2023/09/09/all-together-now-spring-boot-3-2-graalvm-native-images-java-21-and-virtual
- https://docs.spring.io/spring-boot/docs/3.2.0-SNAPSHOT/reference/htmlsingle/#actuator.metrics.export.datadog
- https://docs.datadoghq.com/getting_started/site/

- Github actions to build img docker