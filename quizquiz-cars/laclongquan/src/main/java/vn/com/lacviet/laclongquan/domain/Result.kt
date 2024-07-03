package vn.com.lacviet.laclongquan.domain

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

sealed class Result<out T, out E>(
    open val data: T? = null,
    open val error: E? = null
) {
    data object Loading : Result<Nothing, Nothing>()

    data class Success<T>(override val data: T?) : Result<T, Nothing>(data)

    data class Error<E>(override val error: E? = null, val throwable: Throwable? = null) :
        Result<Nothing, E>(error = error)
}