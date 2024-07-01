package vn.com.lacviet.laclongquan.preference

import androidx.datastore.preferences.core.stringPreferencesKey

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

val ACCESS_TOKEN = stringPreferencesKey("access_token")
val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
val ID_TOKEN = stringPreferencesKey("id_token")
val USER_ID = stringPreferencesKey("user_id")
val FIREBASE_TOKEN = stringPreferencesKey("firebase_token")
val EMAIL = stringPreferencesKey("email")
val DEVICE_ID = stringPreferencesKey("device_id")
val GOOGLE_ID = stringPreferencesKey("google_id")
val FACEBOOK_ID = stringPreferencesKey("facebook_id")
val LOGIN_TYPE = stringPreferencesKey("login_type")
val SOCIAL_ACCOUNT_ID = stringPreferencesKey("social_account_id")
val SOCIAL_AUTHORIZE_TOKEN = stringPreferencesKey("social_authorize_token")

var BasePreference.accessToken: String
    get() = get(ACCESS_TOKEN, "")
    set(value) = set(ACCESS_TOKEN, value)

var BasePreference.refreshToken: String
    get() = get(REFRESH_TOKEN, "")
    set(value) = set(REFRESH_TOKEN, value)

var BasePreference.idToken: String
    get() = get(ID_TOKEN, "")
    set(value) = set(ID_TOKEN, value)

var BasePreference.userId: String
    get() = get(USER_ID, "")
    set(value) = set(USER_ID, value)

var BasePreference.firebaseToken: String
    get() = get(FIREBASE_TOKEN, "")
    set(value) = set(FIREBASE_TOKEN, value)

var BasePreference.email: String
    get() = get(EMAIL, "")
    set(value) = set(EMAIL, value)

var BasePreference.deviceId: String
    get() = get(DEVICE_ID, "")
    set(value) = set(DEVICE_ID, value)

var BasePreference.googleId: String
    get() = get(GOOGLE_ID, "")
    set(value) = set(GOOGLE_ID, value)

var BasePreference.loginType: String
    get() = get(LOGIN_TYPE, "")
    set(value) = set(LOGIN_TYPE, value)

var BasePreference.facebookId: String
    get() = get(FACEBOOK_ID, "")
    set(value) = set(FACEBOOK_ID, value)

var BasePreference.socialAccountId: String
    get() = get(SOCIAL_ACCOUNT_ID, "")
    set(value) = set(SOCIAL_ACCOUNT_ID, value)

var BasePreference.socialAuthorizeToken: String
    get() = get(SOCIAL_AUTHORIZE_TOKEN, "")
    set(value) = set(SOCIAL_AUTHORIZE_TOKEN, value)