import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
        jcenter()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:${Versions.springBoot}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("org.jetbrains.kotlin:kotlin-allopen:${Versions.kotlin}")
        classpath("org.junit.platform:junit-platform-gradle-plugin:${Versions.junitGradlePlugin}")
    }
}

plugins {
    id("kotlin")
    id("org.jetbrains.kotlin.plugin.spring") version Versions.kotlin
    id("org.springframework.boot") version Versions.springBoot
    id("io.spring.dependency-management") version Versions.depManagement
}

group = "de.p7s1.qa.sevenfacette"
version = "0.1.0"

dependencies {
    api(project(":core"))
    api(fileTree("lib/ifxjdbc.jar"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Kafka
    implementation("org.apache.kafka:kafka-clients:${Versions.kafkaClient}")
    implementation("io.confluent:kafka-avro-serializer:${Versions.kafkaAvro}")

    // Testing
    testCompile("io.kotlintest:kotlintest-core:3.0.2")
    testCompile("io.kotlintest:kotlintest-assertions:3.0.2")
    testCompile("io.kotlintest:kotlintest-runner-junit5:3.0.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
}


tasks.withType(KotlinCompile::class.java).all {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs =
            listOf(*kotlinOptions.freeCompilerArgs.toTypedArray(), "-Xjsr305=strict")
}
