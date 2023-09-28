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
sdk install java 21-graalce
./gradlew bootRun
./gradlew bootJar
./gradlew nativeCompile
./build/native/nativeCompile/demo
```
- https://spring.io/blog/2023/09/09/all-together-now-spring-boot-3-2-graalvm-native-images-java-21-and-virtual
- https://docs.spring.io/spring-boot/docs/3.2.0-SNAPSHOT/reference/htmlsingle/#actuator.metrics.export.datadog
- https://docs.datadoghq.com/getting_started/site/

- Github actions to build img docker

docker build -t demoj21:latest .

Create secrets for login at dockerhub

username: ${{ secrets.DOCKERHUB_USERNAME }}
password: ${{ secrets.DOCKERHUB_TOKEN }}

- Apply to k8s
```
kubectl apply -f _deployment/demoj21.yaml
```


## Github Actions
- For compatibility porpuses use "runs-on: ubuntu-20.04" on Github actions for native compability with oraclelinux-8-slim 
- Required libs: GLIBC_2.32

## Validating Health
- Commands to check times readiness/liveness
```
$ kubectl exec -it podname -n namespace -- /bin/sh
sh-4.4# curl -X POST http://localhost:8080/demoj21/actuator/shutdown
{"message":"Shutting down, bye..."}sh-4.4# command terminated with exit code 137
```

## Test Redis
- Post:
```
curl --location --request POST 'http://k3s-04e4.java.rs/demoj21/api/redis/strings' \
  --header 'Content-Type: application/json' \
  --data-raw '{ "database:redis:creator": "Leonardo Savio" }'

curl --location --request GET 'http://k3s-04e4.java.rs/demoj21/api/redis/strings/database:redis:creator'

```

- Troubleshooting:
- Redis:
```
kubectl run redis-test --rm -i --tty --image=redis:7.2.1 -- bash
root@redis-test:/data# redis-cli -h redis-host 
redis-host:6379> 
kubectl get svc --all-namespaces | grep redis-host
```
- Base64 for secrets:
```
echo -n "redis-host" | base64

echo -n "6379" | base64

```
- Simulating variables:
```
export REDIS_HOST=127.0.0.1
export REDIS_PORT=1234
```