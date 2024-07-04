package ai.coolmind.plugins

import model.AuthToken
import model.User
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.test.*

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

class SecurityTest {
    private var jwtToken = ""
    private lateinit var client: HttpClient

    @BeforeTest
    fun setup() {
        client = ApplicationTestBuilder().createClient {
            install(ContentNegotiation) {
                json()
            }
        }
    }

    @AfterTest
    fun tearDown() {
        client.close()
    }

    @Test
    fun testGetJwtTokenApiSuccess() = testApplication {
        val response = client.post("login") {
            contentType(ContentType.Application.Json)
            setBody(User("admin", "123456"))
        }
        jwtToken = response.body<AuthToken>().token

        assertEquals(HttpStatusCode.OK, response.status)
        assertNotNull(jwtToken)}

    @Test
    fun testVerifyJwtTokenApiSuccess() = testApplication {
        val response = client.get("verifyjwt") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $jwtToken")
            }
        }.bodyAsText()
        assertTrue {
            response.contains("Hello, admin!")
        }
    }
}