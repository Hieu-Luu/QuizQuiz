package org.quizquiz.cars.features.chat

import androidx.compose.runtime.toMutableStateList
import org.quizquiz.cars.data.model.ChatMessage

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

class ChatUiState(messages: List<ChatMessage> = emptyList()) {

    private val _messages: MutableList<ChatMessage> = messages.toMutableStateList()
    val messages = _messages

    fun addMessage(message: ChatMessage) {
        _messages.add(message)
    }

    fun replaceLastPendingMessage() {
        val lastMessage = _messages.lastOrNull()
        lastMessage?.let {
            val newMessage = lastMessage.apply { isPending = false }
            _messages.removeLast()
            _messages.add(newMessage)
        }
    }
}