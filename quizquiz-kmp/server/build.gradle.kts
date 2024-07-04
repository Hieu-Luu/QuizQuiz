import com.google.cloud.tools.gradle.appengine.appyaml.AppEngineAppYamlExtension

plugins {
    application
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.jarShadow) // todo("Can remove?")
    alias(libs.plugins.gcpAppEngine)
}

group = "ai.coolmind"
version = "1.0.0"
application {
//    mainClass.set("ai.coolmind.ApplicationKt")
//    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")

    mainClass.set("io.ktor.server.netty.EngineMain")
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

configure<AppEngineAppYamlExtension> {
    stage {
     setArtifact("build/libs/${rootProject.name}-${version}-all.jar")
    }
    deploy {
     version = "GCLOUD_CONFIG"
     projectId = "GCLOUD_CONFIG"
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

ktor {
    fatJar {
        archiveFileName.set("${rootProject.name}-${version}-all.jar")
    }

//    docker {
//        jreVersion.set(JavaVersion.VERSION_17)
//        localImageName.set("coolmind-backend-docker-image")
//        imageTag.set("${version}-preview")
//        portMappings.set(listOf(
//            io.ktor.plugin.features.DockerPortMapping(
//                8081,
//                8081,
//                io.ktor.plugin.features.DockerPortMappingProtocol.TCP
//            )
//        ))
//
//        externalRegistry.set(
//            io.ktor.plugin.features.DockerImageRegistry.dockerHub(
//                appName = provider { "coolbrain" },
//                username = provider { DOCKER_HUB_USERNAME },
//                password = provider { DOCKER_HUB_PASSWORD }
//            )
//        )
//    }
}

dependencies {
    implementation(projects.shared)
    implementation(libs.ktor.server.core)
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-client-content-negotiation")
    implementation(libs.mongodb.driver.core)
    implementation(libs.mongodb.driver.sync)
    implementation(libs.mongodb.bson)
    implementation("io.ktor:ktor-server-sessions-jvm")
    implementation("io.ktor:ktor-server-websockets-jvm")
    implementation(libs.ktor.swagger.ui)
    implementation("io.ktor:ktor-server-auth-jvm")
//    implementation("com.kborowy:firebase-auth-provider:1+")
    implementation("io.ktor:ktor-client-core-jvm")
    implementation("io.ktor:ktor-client-apache-jvm")
    implementation("io.ktor:ktor-server-auth-jwt-jvm")
    implementation(libs.ktor.server.netty)
    implementation(libs.logback)
    implementation("io.ktor:ktor-server-config-yaml")
    // handle errors in your Ktor application
    implementation("io.ktor:ktor-server-status-pages")
    implementation(libs.postgresql)
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.dao)
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.jsonpath)
    testImplementation(libs.junit.junit)
}