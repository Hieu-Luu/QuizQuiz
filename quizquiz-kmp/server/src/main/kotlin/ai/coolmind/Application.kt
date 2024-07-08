package ai.coolmind

import ai.coolmind.plugins.configureRouting
import ai.coolmind.plugins.configureSecurity
import ai.coolmind.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

@Suppress("unused")
fun Application.module() {
    configureRouting()
    configureSecurity(environment.config)
    configureSerialization()
}