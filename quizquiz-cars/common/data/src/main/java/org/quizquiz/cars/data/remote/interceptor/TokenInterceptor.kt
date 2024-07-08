package org.quizquiz.cars.data.remote.interceptor

import android.os.Build
import androidx.core.os.BuildCompat
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.quizquiz.cars.data.BuildConfig
import vn.com.lacviet.laclongquan.data.preference.BasePreference
import vn.com.lacviet.laclongquan.data.preference.accessToken
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

class TokenInterceptor @Inject constructor(private val prefs: BasePreference) : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val isUpload =
            runCatching {
                request.url.encodedPath.endsWith("/upload")
            }.getOrDefault(false)
        val isDownloadMedia = request.header("Download")?.toBoolean() ?: false
        val token = prefs.accessToken

        val newRequest = request.newBuilder().apply {
            if (token.isEmpty() && !isDownloadMedia) {
                header("Authorization", "Bearer ${prefs.accessToken}")
            }
            val contentType = if (isUpload) "multipart/form-data" else "application/json"
            header("Content-Type", contentType)
        }
            .build()
        return chain.proceed(newRequest)
    }
}