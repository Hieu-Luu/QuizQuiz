package vn.com.lacviet.laclongquan.domain

import retrofit2.Response
import vn.com.lacviet.laclongquan.data.api.BaseDto
import vn.com.lacviet.laclongquan.domain.model.authentication.AuthRequest
import vn.com.lacviet.laclongquan.domain.model.authentication.AuthResponse

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

interface AuthRepository {
    suspend fun signin(request: AuthRequest): Response<AuthResponse>

    suspend fun<T, R> signin(request: T): Response<*>

    suspend fun <T, R> signInWithSocial(request: T ): R
}