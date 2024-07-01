package org.quizquiz.cars.data.remote.service.authentication

import org.quizquiz.cars.data.model.authentication.*
import retrofit2.Response
import retrofit2.http.*

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

interface AuthService {
    /**
     * See <a href="TODO("Add API documentation link here)"</a>
     */
    @POST("qq/api/v1/auth/signin")
    suspend fun signIn(@Body req: AuthRequest): Response<AuthResponse>

    /**
     * See <a href="TODO("Add API documentation link here)"</a>
     */
    @POST("qq/api/v1/signin-social")
    suspend fun signInWithSocial(@Body req: SocialAuthRequest): Response<AuthResponse>

    /**
     * See <a href="TODO("Add API documentation link here)"</a>
     */
    @POST("qq/api/v1/link-social")
    suspend fun linkSocialAccount(@Body req: SocialAuthRequest): Response<AuthResponse>

    /**
     * See <a href="TODO("Add API documentation link here)"</a>
     */
    @POST("qq/api/v1/signup")
    suspend fun signUp(@Body signUpRequestDto: SignUpRequestDto): Response<Void>

    /**
     * See <a href="TODO("Add API documentation link here)"</a>
     */
    @POST("qq/api/v1/send-otp")
    suspend fun sendOtp(@Body otpRequestDto: SendOtpRequestDto): Response<Void>

    /**
     * See <a href="TODO("Add API documentation link here)"</a>
     */
    @POST("qq/api/v1/verify-account")
    suspend fun verifyEmail(@Body verifyEmailRequestDto: VerifyEmailRequestDto): Response<Void>

    /**
     * See <a href="TODO("Add API documentation link here)"</a>
     */
    @POST("qq/api/v1/forgot-password")
    suspend fun resetPassword(@Body resetPasswordRequestDto: ResetPasswordRequestDto): Response<Void>

    /**
     * See <a href="TODO("Add API documentation link here)"</a>
     */
    @POST("qq/api/v1/auth/logout")
    suspend fun logOut(): Response<Void>

    /**
     * See <a href="TODO("Add API documentation link here)"</a>
     */
    @PUT("qq/api/v1/user/change-password")
    suspend fun changePassword(@Body request: ChangePasswordRequestDto): Response<Void>

    /**
     * See <a href="TODO("Add API documentation link here)"</a>
     */
    @GET("qq/api/v1/user/social")
    suspend fun getSocialAccount(): Response<SocialConnectResponse>

    /**
     * See <a href="TODO("Add API documentation link here)"</a>
     */
    @POST("qq/api/v1/user/link-social")
    suspend fun userLinkSocialAccount(@Body req: SocialAuthRequest): Response<Void>

    /**
     * See <a href="TODO("Add API documentation link here)"</a>
     */
    @PUT("qq/api/v1/user/disconnect-social")
    suspend fun disconnectSocialAccount(@Body req: SocialAuthRequest): Response<Void>

    /**
     * See <a href="TODO("Add API documentation link here)"</a>
     */
    @HTTP(method = "DELETE", path = "qq/api/v1/user/delete-account", hasBody = true)
    suspend fun deleteAccount(@Body password: DeleteAccountRequest): Response<Void>

    @HTTP(method = "DELETE", path = "qq/api/v1/user/delete-account-1", hasBody = true)
    suspend fun deleteSocialAccount(@Body password: DeleteSocialAccountRequest): Response<Void>

    /**
     * See <a href="TODO("Add API documentation link here)"</a>
     */
    @POST("qq/api/v1/auth/verify-password")
    suspend fun verifyPass(@Body req: VerifyPassRequest): Response<Boolean>

    /**
     * See <a href="TODO("Add API documentation link here)"</a>
     */
    @POST("qq/api/v1/user/send-otp")
    suspend fun sendChangeUserNameOtp(@Body otpRequest: SendOtpChangeUserNameRequest): Response<Void>

    /**
     * See <a href="TODO("Add API documentation link here)"</a>
     */
    @POST("qq/api/v1/user/change-email")
    suspend fun changeEmail(@Body req: ChangeEmailRequest): Response<ChangeEmailPhoneResponse>

    /**
     * See <a href="TODO("Add API documentation link here)"</a>
     */
    @POST("qq/api/v1/user/change-phone-number")
    suspend fun changePhone(@Body req: ChangePhoneRequest): Response<ChangeEmailPhoneResponse>
}