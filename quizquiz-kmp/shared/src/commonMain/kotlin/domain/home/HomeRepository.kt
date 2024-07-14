package domain.home

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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

class HomeRepositoryImpl(
    private val httpClient: HttpClient
) : HomeRepository {
    override suspend fun getHomeData(): Flow<String> = flow {
        val response = httpClient.get("https://jsonplaceholder.typicode.com/todos/1")
        emit(response.body())
    }
}

interface HomeRepository {
    suspend fun getHomeData(): Flow<String>
}