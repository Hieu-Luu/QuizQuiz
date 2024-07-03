package org.quizquiz.cars.domain.gemini

import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.quizquiz.cars.data.model.generativeai.GeminiModel
import org.quizquiz.cars.data.remote.service.gemini.GeminiService
import javax.inject.Inject

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

class GeminiRepositoryUnitTest {

    @Inject lateinit var geminiService: GeminiService

    @Test
    fun startChat_Gemini_1_5_flash_WithRoleAndText_Success() = runTest {
        val aiRepository = GeminiRepositoryImpl(geminiService)
        val contents = listOf(
            content(role = "user") { text("Give me five subcategories of jazz?") }
        )
        val response = aiRepository.startChat(GeminiModel.GEMINI_1_5_FLASH.name, contents)
        val chat = response.body()

        assert(response.isSuccessful)
        assert(!chat.isNullOrEmpty())
    }

    @Test
    fun startChat_Gemini_1_5_flash_MultipleRolesAndText_Success() = runTest {
        val aiRepository = GeminiRepositoryImpl(geminiService)
        val contents = listOf(
            content(role = "user") { text("Give me five subcategories of jazz") },
            content(role = "model") { text("Great to meet you. What would you like to know?") }
        )
        val response = aiRepository.startChat(GeminiModel.GEMINI_1_5_FLASH.name, contents)
        val chat = response.body()
        assert(response.isSuccessful)
        assert(!chat.isNullOrEmpty())
    }
}