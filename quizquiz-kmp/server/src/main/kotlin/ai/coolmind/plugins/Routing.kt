package ai.coolmind.plugins

import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(StatusPages) {
        exception<IllegalStateException> { call, cause ->
            call.respondText("App in illegal state as ${cause.message}")
        }
    }
//    install(Webjars) {
//        path = "/webjars" //defaults to /webjars
//    }
    install(SwaggerUI) {
        swagger {
            swaggerUrl = "swagger-ui"
            forwardRoot = true
        }
        info {
            title = "Example API"
            version = "latest"
            description = "Example API for testing and demonstration purposes."
        }
        server {
            url = "http://localhost:8080"
            description = "Development Server"
        }
    }
    routing {
        staticResources("/content", "content")
        staticResources("/task-ui", "task-ui")
        staticResources("/static", "static")

        get("/") {
            call.respondText("Hello World!")
        }

//        get("/webjars") {
//            call.respondText("<script src='/webjars/jquery/jquery.js'></script>", ContentType.Text.Html)
//        }

        get("/error-test") {
            throw IllegalStateException("Too Busy")
        }
    }
}