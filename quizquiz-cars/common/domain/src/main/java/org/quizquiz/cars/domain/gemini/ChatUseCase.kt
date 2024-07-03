package org.quizquiz.cars.domain.gemini

import com.google.ai.client.generativeai.type.Candidate
import com.google.ai.client.generativeai.type.Content
import retrofit2.Response
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

class ChatUseCase @Inject constructor(
    private val geminiRepository: GeminiRepository
){
    suspend operator fun invoke(model: String, contents: List<Content>): Response<List<Candidate>> = geminiRepository.startChat(model, contents)
}