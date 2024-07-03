package org.quizquiz.cars.data.daos

import androidx.room.*
import vn.com.lacviet.laclongquan.data.dao.BaseDao
import vn.com.lacviet.laclongquan.data.entity.BaseEntity

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

abstract class RoomDao<in E : BaseEntity> : BaseDao<E> {

    @Insert
    abstract override suspend fun insert(entity: E): Long

    @Insert
    abstract override suspend fun insert(vararg entity: E)

    @Insert
    abstract override suspend fun insert(entities: List<E>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun update(entity: E)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun update(vararg entity: E)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun update(entities: List<E>)

    @Delete
    abstract override suspend fun delete(entity: E)

    @Delete
    abstract override suspend fun delete(vararg entity: E)

    @Delete
    abstract override suspend fun delete(entities: List<E>)

    @Transaction
    abstract override suspend fun withTransaction(transaction: suspend () -> Unit)

    @Transaction
    override suspend fun upsert(vararg entity: E) {
        super.upsert(*entity)
    }

    @Transaction
    override suspend fun upsert(entities: List<E>) {
        super.upsert(entities)
    }
}