package vn.com.lacviet.laclongquan.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
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

interface BaseViewModel {
    val _loading: MutableLiveData<Boolean>
    val loading: LiveData<Boolean>
        get() = _loading

    val _error: MutableLiveData<String>
    val error: LiveData<String>
        get() = _error

    val _success: MutableLiveData<Boolean>
    val success: LiveData<Boolean>
        get() = _success

    val networkError: MutableResetLiveData<Result.Error<out Any>?>
    var isErrorHandling: Boolean

    val handler: CoroutineExceptionHandler
}