buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven("https://plugins.gradle.org/m2/")
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
    }
}

allprojects {

    repositories {
        google()
        mavenCentral()
        jcenter()
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
        maven("https://dl.bintray.com/kotlin/kotlin-js-wrappers")
        maven("https://kotlin.bintray.com/kotlinx")
        maven("https://packages.confluent.io/maven/")
    }

    apply {
        plugin("maven-publish")
    }

    // TODO remove once https://github.com/Kotlin/kotlin-frontend-plugin/issues/141 is fixed
    plugins.whenPluginAdded {
        if (this.javaClass.name == "org.jetbrains.kotlin.gradle.frontend.FrontendPlugin") {
            val fixTask = tasks.register("webpack-config-fix") {
                this.doLast { file("webpack.config.d").mkdir() }
            }
            afterEvaluate {
                tasks.named("webpack-config").configure {
                    this.dependsOn(fixTask)
                }
            }
        }
    }
}
