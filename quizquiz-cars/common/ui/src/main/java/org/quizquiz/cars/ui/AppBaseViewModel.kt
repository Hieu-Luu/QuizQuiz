package org.quizquiz.cars.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import vn.com.lacviet.laclongquan.domain.Result
import vn.com.lacviet.laclongquan.util.MutableResetLiveData

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

class AppBaseViewModel : ViewModel() {

    protected val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    protected val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    protected val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean>
        get() = _success

    @PublishedApi // For inline function to access internal variable
    internal val networkError = MutableResetLiveData<Result.Error<out Any>?>()
    internal var isErrorHandling = false
        set(value) {
            field = value
            if (!isErrorHandling) networkError.value = null
        }

    protected val handler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
    }

    /**
     * This function helps handle api call
     * @param api (required) the function that return the response of api
     * @return flow of Result<T,E> T is the generic type of success data
     *                             E is the generic type of error data
     */
    inline fun <T, reified E : Any> callApi(
        crossinline selfErrorHandle: (Result.Error<E>) -> Boolean = { true },
        crossinline api: suspend () -> Response<T>
    ) = flow<Result<T, E>> {
        emit(Result.Loading)
        val res = api.invoke()
        emit(res.asResult())
    }.catch {
        emit(Result.Error(throwable = it))
    }.onEach {
        if (it is Result.Error) {
            // Only emit event to view when the called does not expect to handle error itself
            val selfHandled = selfErrorHandle(it)
            if (!selfHandled) {
                withContext(Dispatchers.Main) {
                    networkError.value = it
                }
            }
        }
    }

    suspend inline fun <T, reified E : Any> apiCall(
        crossinline selfErrorHandle: (Result.Error<E>) -> Boolean = { false },
        crossinline api: suspend () -> Response<T>
    ): Result<T, E> {
        val res = runCatching { api().asResult<T, E>() }.getOrElse { Result.Error(throwable = it)
        }
        // Only emit event to view when the called does not expect to handle error itself
        if (res is Result.Error) {
            viewModelScope.launch(Dispatchers.IO) {
                val code = (res.throwable as HttpException).code()
                val url = (res.throwable as HttpException).response()?.raw()?.request()?.url()
            }
        }
        if ((res is Result.Error) && !selfErrorHandle(res)) {
            networkError.postValue(res)
        }
        return res
    }

    suspend inline fun <T, reified E : Any> apiToFlow(
        crossinline selfErrorHandle: (Result.Error<E>) -> Boolean = { false },
        crossinline api: suspend () -> Response<T>
    ): Flow<Result<T, E>> {
        val res = runCatching { api().asResult<T, E>() }.getOrElse {
            Result.Error(throwable = it)
        }
        // Only emit event to view when the called does not expect to handle error itself
        if (res is Result.Error) {
            viewModelScope.launch(Dispatchers.IO) {
                val code = (res.throwable as HttpException).code()
                val url = (res.throwable as HttpException).response()?.raw()?.request()?.url()
            }
        }
        if ((res is Result.Error) && !selfErrorHandle(res)) {
            networkError.postValue(res)
        }
        return flowOf(res)
    }
}

/**
 * This function helps transform api response to Result<T,E>
 * @return Result<T,E> T is the generic type of success data
 *                             E is the generic type of error data
 */
inline fun <T, reified E> Response<T>.asResult(): Result<T, E> = if (!isSuccessful) {
    val response = kotlin.runCatching {
        Gson().fromJson(errorBody()?.string(), E::class.java)
    }.getOrNull()
    Result.Error(response, HttpException(this))
} else {
    Result.Success(body())
}
