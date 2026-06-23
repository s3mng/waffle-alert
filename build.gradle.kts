plugins {
    kotlin("jvm") version "2.3.0"
    kotlin("plugin.spring") version "2.3.0"
    id("org.springframework.boot") version "4.0.7"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "2.3.0"
}

group = "com.wafflestudio"
version = "0.0.1-SNAPSHOT"
description = "waffle-alert"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2025.1.2"
extra["ociSdkVersion"] = "3.80.1"

dependencies {
    // ── Web (Spring MVC) ─────────────────────────────
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // ── 운영 (헬스체크/메트릭 노출) ──────────────────
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // ── 영속성 (JPA + MySQL + Flyway) ───────────────
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-flyway")
    implementation("org.flywaydb:flyway-mysql")
    runtimeOnly("com.mysql:mysql-connector-j")

    // ── Kotlin 필수 ──────────────────────────────────
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // ── OCI SDK (Prometheus 밖 source 직접 조회) ─────
    implementation("com.oracle.oci.sdk:oci-java-sdk-monitoring:${property("ociSdkVersion")}")
    implementation("com.oracle.oci.sdk:oci-java-sdk-usageapi:${property("ociSdkVersion")}")
    implementation("com.oracle.oci.sdk:oci-java-sdk-common-httpclient-jersey3:${property("ociSdkVersion")}")

    // ── 알림 전송 재시도 / circuit breaker ───────────
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // ── 테스트 ───────────────────────────────────────
    testImplementation("org.springframework.boot:spring-boot-starter-actuator-test")
    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
    testImplementation("org.springframework.boot:spring-boot-starter-flyway-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("io.mockk:mockk:1.14.7")
    testImplementation("com.ninja-squad:springmockk:5.0.1")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:mysql:1.21.3")
    testRuntimeOnly("com.h2database:h2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
