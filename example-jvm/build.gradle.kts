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
        classpath("org.junit.platform:junit-platform-gradle-plugin:1.1.0")
    }
}

plugins {
    id("kotlin")
    id("org.jetbrains.kotlin.plugin.spring") version Versions.kotlin
    id("org.springframework.boot") version Versions.springBoot
    id("io.spring.dependency-management") version Versions.depManagement
    //id("io.kotest") version "1.0.2"
}

group = "de.p7s1.qa.sevenfacette"
version = "0.0.1"

dependencies {
    api(project(":core"))
    api(fileTree("lib/ifxjdbc.jar"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.bugsnag:bugsnag-spring:${Versions.bugsnagJvm}")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")


    // Kafka
    implementation("org.apache.kafka:kafka-clients:2.0.0")
    implementation("io.confluent:kafka-avro-serializer:5.0.0")

    // Http
    implementation("com.github.kittinunf.fuel:fuel:2.2.1")


    //testImplementation("io.kotlintest:kotlintest-runner-junit5:3.4.2")
    testCompile("io.kotlintest:kotlintest-core:3.0.2")
    testCompile("io.kotlintest:kotlintest-assertions:3.0.2")
    testCompile("io.kotlintest:kotlintest-runner-junit5:3.0.2")
    //    testCompile 'io.kotest:kotest-runner-junit5:3.4.1993-SNAPSHOT'
}

tasks.withType<Test> {
    useJUnitPlatform()
}


tasks.withType(KotlinCompile::class.java).all {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs =
            listOf(*kotlinOptions.freeCompilerArgs.toTypedArray(), "-Xjsr305=strict")
}
