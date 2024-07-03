package org.quizquiz.cars.data.remote.service.gemini

import com.google.ai.client.generativeai.type.Candidate
import com.google.ai.client.generativeai.type.Content
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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

interface GeminiService {

    /**
     * GET method:
     * Batch embed contents.
     * See <a href="TODO("Add API documentation link here)"</a>
     *
     * @param model
     * @return Response<AuthResponse>
     */
    @GET("generativeai/{model}:batchEmbedContents")
    suspend fun batchEmbedContents(@Path("model") model: String): Response<String>

    /**
     * GET method:
     * Get list of AI models.
     * See <a href="TODO("Add API documentation link here)"</a>
     *
     * @return Response<List<String>>
     */
    @GET("qq/api/v1/ai/models")
    suspend fun models(): Response<List<Content>>

    /**
     * GET method:
     * Get AI model detail.
     * See <a href="TODO("Add API documentation link here)"</a>
     *
     * @param name
     * @return Response<AuthResponse>
     */
    @GET("qq/api/v1/ai/{model}")
    suspend fun model(@Path("model") name: String): Response<Content>

    /**
     * POST method:
     * Sign in with email and password.
     * See <a href="TODO("Add API documentation link here)"</a>
     *
     * @param model
     * @return Response<AuthResponse>
     */
    @POST("generativeai/models/{model}:generateContent")
    suspend fun generateContent(
        @Path("model") model: String,
        @Body contents: List<Content>
    ): Response<List<Candidate>>
}