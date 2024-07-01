package org.quizquiz.cars.data.model.authentication

import vn.com.lacviet.laclongquan.context.getLocale

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

data class SocialAuthRequest(
    var socialAccountId: String = "",
    var socialAuthorizeToken: String = "",
    var socialProvider: String = "",
    var deviceId: String = "",
    var email: String = "",
    var password: String? = null,
    var language: String? = getLocale()
)