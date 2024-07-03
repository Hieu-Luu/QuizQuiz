package org.quizquiz.cars.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.quizquiz.cars.domain.authentication.AuthRepositoryImpl
import org.quizquiz.cars.domain.gemini.GeminiRepository
import org.quizquiz.cars.domain.gemini.GeminiRepositoryImpl
import vn.com.lacviet.laclongquan.domain.AuthRepository
import javax.inject.Singleton

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

@InstallIn(SingletonComponent::class)
@Module
abstract class DomainModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(repo: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindGeminiRepository(repo: GeminiRepositoryImpl): GeminiRepository
}