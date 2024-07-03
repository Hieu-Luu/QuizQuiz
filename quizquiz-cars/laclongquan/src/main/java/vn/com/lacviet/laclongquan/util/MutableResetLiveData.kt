package vn.com.lacviet.laclongquan.util

import android.app.Activity
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.*

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

/**
 * Auto reset to defValue when owner is DESTROYED (not by the configuration changed)
 * This one should be used when a Fragment is added to backstack and when it is popped back,
 * the data is expected to be reset to default value
 * @param defValue (optional) default value of LiveData
 */
class MutableResetLiveData<T>(
    private val defValue: T? = null
) : MutableLiveData<T>(defValue) {

    private var shouldValueReset = true

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T?>) {
        /*
         * when owner is fragment -> get the viewLifecycleOwner of fragment,
         * because fragment will not be destroyed when it was added to backstack
         */
        val lifeCycleOwner = when (owner) {
            is Fragment -> owner.viewLifecycleOwner
            else -> owner
        }
        /*
         * add lifecycle observer in order to listen to configuration change at ON_STOP
         * and reset value at ON_DESTROY (not by configuration change)
         */
        lifeCycleOwner.lifecycle.addObserver(
            object : LifecycleEventObserver {
                override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                    if (event == Lifecycle.Event.ON_STOP) {
                        shouldValueReset = !isConfigurationChanging(owner)
                    }
                    /*
                     * Not reset the value when configuration change
                     */
                    if (shouldValueReset && event == Lifecycle.Event.ON_DESTROY) {
                        value = defValue
                    }
                }
            }
        )
        super.observe(lifeCycleOwner, observer)
    }

    /**
     * use this in order to detect ON_DESTROY is trigger by configuration change or not
     */
    private fun isConfigurationChanging(owner: LifecycleOwner): Boolean = when (owner) {
        is Activity -> owner.isChangingConfigurations
        is Fragment -> owner.activity?.isChangingConfigurations == true
        else -> false
    }
}
