package org.quizquiz.cars.data.remote

import org.quizquiz.cars.data.BuildConfig

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

const val AUTHORIZATION = "Authorization"
const val BEARER = "Bearer"
const val CONTENT_TYPE = "Content-Type"
const val CLIENT = "Client"
const val RETRY_COUNT = 3
const val BASE_URL = BuildConfig.BASE_URL
const val CALL_API_TIMEOUT = 30000L // ms