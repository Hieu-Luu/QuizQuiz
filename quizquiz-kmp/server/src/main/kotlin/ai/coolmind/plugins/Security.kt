package ai.coolmind.plugins

import model.AuthToken
import model.User
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import java.util.*

/*
 * Copyright 2024 Hieu Luu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

fun Application.configureSecurity(config: ApplicationConfig) {
    data class MySession(val count: Int = 0)
    install(Sessions) {
        cookie<MySession>("MY_SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }

//     TODO: Add Firebase Auth
//    install(Authentication) {
//        firebase {
//            adminFile = File("path/to/admin/file.json")
//            realm = "QuizQuiz Server"
//            validate { token ->
//                MyAuthenticatedUser(id = token.uid)
//            }
//        }
//    }

    authentication {
        oauth("auth-oauth-google") {
            urlProvider = { "http://localhost:8080/callback" }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "google",
                    authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
                    accessTokenUrl = "https://accounts.google.com/o/oauth2/token",
                    requestMethod = HttpMethod.Post,
                    clientId = System.getenv("GOOGLE_CLIENT_ID"),
                    clientSecret = System.getenv("GOOGLE_CLIENT_SECRET"),
                    defaultScopes = listOf("https://www.googleapis.com/auth/userinfo.profile")
                )
            }
            client = HttpClient(Apache)
        }
    }

    authentication {
        basic(name = "auth-basic") {
            realm = "QuizQuiz Server"
            validate { credentials ->
//                You can skip basic authentication if a session already exists.
//                skipWhen { call -> call.sessions.get<UserSession>() != null }
                if (credentials.name == credentials.password) {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }

        form(name = "auth-form") {
            userParamName = "user"
            passwordParamName = "password"
            challenge {

            }

        }
    }

    authentication {
        bearer("auth-bearer") {
            realm = "QuizQuiz server"
            authenticate { tokenCredential ->
                if (tokenCredential.token == "123456") {
                    UserIdPrincipal("QuizQuiz")
                } else {
                    null
                }
            }

        }
    }

    // Please read the jwt property from the config file if you are using EngineMain
    val jwtAudience = config.tryGetString("jwt.audience") ?: throw IllegalArgumentException("jwt audience is not set")
    val jwtIssuer = config.tryGetString("jwt.issuer") ?: throw IllegalArgumentException("jwt domain is not set")
    val jwtRealm = config.tryGetString("jwt.realm") ?: throw IllegalArgumentException("jwt realm is not set")
    val jwtSecret = config.tryGetString("jwt.secret") ?: throw IllegalArgumentException("jwt secret is not set")
    authentication {
        jwt("auth-jwt") {
            realm = jwtRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtIssuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.getClaim("user").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is invalid or has expired")
            }
        }
    }

    routing {
        get("/session/increment") {
            val session = call.sessions.get<MySession>() ?: MySession()
            call.sessions.set(session.copy(count = session.count + 1))
            call.respondText("Counter is ${session.count}. Refresh to increment.")
        }

        authenticate("auth-bearer") {
            get("/token") {

            }

            get("/login") {
                call.respondText("Hello, ${call.principal<UserIdPrincipal>()?.name}!")
            }
        }

        post("/login") {
            val user = call.receive<User>()
            // Check if user/password is correct
            // ...
            val token = JWT.create()
                .withAudience(jwtAudience)
                .withIssuer(jwtIssuer)
                .withClaim("user", user.name)
                .withExpiresAt(Date(System.currentTimeMillis() + 60000))
                .sign(Algorithm.HMAC256(jwtSecret))
            call.respond(AuthToken(token))
        }

        authenticate("auth-jwt") {
            get("/verifyjwt") {
                val principal = call.principal<JWTPrincipal>()
                val userName = principal!!.payload.getClaim("user").asString()
                val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
                call.respondText("Hello, $userName! Expires at: $expiresAt")
            }
        }
    }
}

