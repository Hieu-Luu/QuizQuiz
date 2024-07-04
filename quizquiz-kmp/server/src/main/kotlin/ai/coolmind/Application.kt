package ai.coolmind

import ai.coolmind.plugins.configureRouting
import ai.coolmind.plugins.configureSecurity
import ai.coolmind.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.netty.*

//fun main() {
//    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
//        .start(wait = true)
//}

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    val env = environment.config.tryGetString("environment") ?: "dev"
    println("Environment: $env")

    configureRouting()
    configureSecurity(environment.config)
    configureSerialization()
}