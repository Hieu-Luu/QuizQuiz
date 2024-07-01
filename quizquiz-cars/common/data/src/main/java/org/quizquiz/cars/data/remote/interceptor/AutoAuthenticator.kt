package org.quizquiz.cars.data.remote.interceptor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.quizquiz.cars.data.remote.AUTHORIZATION
import org.quizquiz.cars.data.remote.BEARER
import org.quizquiz.cars.data.remote.RETRY_COUNT
import vn.com.lacviet.laclongquan.preference.BasePreference
import vn.com.lacviet.laclongquan.preference.accessToken

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

class AutoAuthenticator(
    private val prefs: BasePreference,
    var newTokenRequest: (suspend () -> String?)? = null
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        println("[AUTHENTICATION] need refresh token at here.")
        val oldToken = kotlin.runCatching {
            response.request.header(AUTHORIZATION)?.split(" ")?.get(1)
        }.getOrNull() ?: ""

        if (prefs.accessToken!= oldToken) return null
        if (responseCount(response) >= RETRY_COUNT) return null

        // request new token
        val newToken = runBlocking(Dispatchers.IO) { newTokenRequest?.invoke() } ?: return null

        println("[AUTHENTICATION] New token from refresh : $newToken")
        return if (newToken.isNotEmpty()) {
            // retry the failed 401 request with new access token
            response.request.newBuilder()
                .header(AUTHORIZATION, "$BEARER $newToken") // use the new access token
                .build()
        } else {
            null
        }
    }

    private fun responseCount(res: Response?): Int {
        var response = res
        var result = 1
        while (response != null) {
            response = response.priorResponse
            result++
        }
        return result
    }
}