# build stage
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app
COPY . /app
# 와플 공통 모듈(GitHub Packages) 빌드 시 GITHUB_TOKEN 필요
RUN --mount=type=secret,id=github_token \
    GITHUB_TOKEN=$(cat /run/secrets/github_token) ./gradlew bootJar --no-daemon

# runtime stage
FROM eclipse-temurin:25-jre AS runtime
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/waffle-alert.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/waffle-alert.jar"]
