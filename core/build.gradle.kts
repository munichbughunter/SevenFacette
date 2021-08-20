

plugins {
    kotlin("multiplatform") version "1.4.32"
    kotlin("plugin.serialization") version "1.4.0"
    id("maven-publish")
    id("com.jfrog.bintray") version "1.8.4"
    id("com.github.johnrengelman.shadow") version "2.0.2"
    id("net.akehurst.kotlin.kt2ts") version("1.4.0")
}

var GROUP_ID = "de.p7s1.qa.sevenfacette"
var ARTIFACT_ID = "core"
//var BINTRAY_REPOSITORY = "sevenfacette-jvm"
/*
That is only for testing!!!
 */
var LIBRARY_VERSION_NAME=0.6 // The current version of your library. You will need to update this value whenever you upload a new release. For example: 1.0
version = "0.7.0"
group = "de.p7s1.qa.sevenfacette"
//var version = System.properties['VERSION']
//version = version.toString().substring(1,6)
//LIBRARY_VERSION_NAME=version.toString().substring(0,3)

//group = "de.p7s1.qa.sevenfacette"
//version = "1.0.0"



repositories {
    mavenCentral()
    jcenter()
    maven(url = "https://dl.bintray.com/aakira/maven")
    maven(url = "https://jitpack.io")
    maven(url = "https://kotlin.bintray.com/kotlinx")
}
kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        //nodejs {}
        useCommonJs()
        binaries.executable()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.postgresql:postgresql:42.2.5")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:1.0-M1-1.4.0-rc-218")

                implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.21")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")

                implementation("io.ktor:ktor-client-core:1.5.4")
                implementation("io.ktor:ktor-client-serialization:1.5.4")
                implementation("io.ktor:ktor-client-json:1.5.4")
                implementation("io.ktor:ktor-client-auth:1.5.4")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                api("com.charleskorn.kaml:kaml:0.35.2")
                implementation("io.ktor:ktor-client-core-jvm:1.5.4")
                implementation("io.ktor:ktor-client-apache:1.5.4")


                implementation("com.jayway.jsonpath:json-path:2.4.0")
                implementation("org.skyscreamer:jsonassert:1.5.0")
                implementation("org.apache.kafka:kafka-clients:2.0.0")
                implementation("org.apache.avro:avro:1.9.2")

                implementation("org.awaitility:awaitility-kotlin:4.1.0")

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:1.0-M1-1.4.0-rc-218")
                implementation("com.googlecode.json-simple:json-simple:1.1.1")
                implementation("com.fasterxml.jackson.core:jackson-core:2.12.1")
                implementation("com.fasterxml.jackson.core:jackson-databind:2.12.1")
                implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.12.1")
                implementation("com.microsoft.playwright:playwright:0.180.0")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit5"))
                implementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
                runtimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
                implementation("com.h2database:h2:1.4.197")
            }
        }
        val jsMain by getting {
            dependencies {
                //implementation (npm( "kafkajs", "^1.12.0"))
                implementation("org.jetbrains.kotlinx:kotlinx-nodejs:0.0.7")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:1.0-M1-1.4.0-rc")
                implementation("io.ktor:ktor-client-core-js:1.5.4")
                implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.21")
            }
        }
        val jsTest by getting
    }
}

kt2ts {
    classPatterns.set(listOf(
        "com.example.my.module.common.*"
    ))
}
