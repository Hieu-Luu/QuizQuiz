import com.google.cloud.tools.gradle.appengine.appyaml.AppEngineAppYamlExtension

plugins {
    application
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor.serer)
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
    implementation(libs.ktor.server.serialization.json)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.config.yaml)
    implementation(libs.ktor.server.status.pages)
    implementation(libs.ktor.server.sessions)
    implementation(libs.ktor.server.websockets)
    implementation(libs.ktor.server.netty)
    implementation(libs.mongodb.driver.core)
    implementation(libs.mongodb.driver.sync)
    implementation(libs.mongodb.bson)
    implementation(libs.ktor.swagger.ui)
//    implementation("com.kborowy:firebase-auth-provider:1+")
    implementation(libs.ktor.client.core.jvm)
    implementation(libs.ktor.client.content.negotiation.jvm)
    implementation(libs.ktor.client.apache.jvm)
    // handle errors in your Ktor application
    implementation(libs.logback)
    implementation(libs.postgresql)
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.dao)
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.jsonpath)
    testImplementation(libs.junit.junit)
}