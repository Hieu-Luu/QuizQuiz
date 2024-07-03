package vn.com.lacviet.laclongquan.data.dao

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

interface BaseDao<in E : BaseEntity> {

    /**
     * Inserts the given entity into the database and returns the generated ID.
     *
     * @param entity the entity to be inserted
     * @return the generated ID of the inserted entity
     */
    suspend fun insert(entity: E): Long

    /**
     * Inserts the given entities into the database.
     *
     * @param entity the entities to be inserted
     */
    suspend fun insert(vararg entity: E)

    /**
     * Inserts the given list of entities into the database.
     *
     * @param entities the list of entities to be inserted
     */
    suspend fun insert(entities: List<E>)

    /**
     * Updates the given entity in the database.
     *
     * @param entity the entity to be updated
     */
    suspend fun update(entity: E)

    /**
     * Updates the given entities in the database.
     *
     * @param entity the entities to be updated
     */
    suspend fun update(vararg entity: E)

    /**
     * Updates the given list of entities in the database.
     *
     * @param entities the list of entities to be updated
     */
    suspend fun update(entities: List<E>)

    /**
     * Deletes the given entity from the database.
     *
     * @param entity the entity to be deleted
     */
    suspend fun delete(entity: E)

    /**
     * Deletes the given entities from the database.
     *
     * @param entity the entities to be deleted
     */
    suspend fun delete(vararg entity: E)

    /**
     * Deletes the given list of entities from the database.
     *
     * @param entities the list of entities to be deleted
     */
    suspend fun delete(entities: List<E>)

    /**
     * Executes a transaction block. The transaction is a suspend function that takes no parameters and returns Unit.
     *
     * @param transaction the transaction to be executed
     */
    suspend fun withTransaction(transaction: suspend () -> Unit) = transaction.invoke()

    /**
     * Upsert the given entity into the database.
     *
     * @param entity the entity to be upserted
     * @return the generated ID of the upserted entity
     */
    private suspend fun upsert(entity: E): Long {
        if (0L == entity.id) return insert(entity)
        update(entity)
        return entity.id
    }

    /**
     * Upsert the given entities into the database.
     *
     * @param entity the entities to be upserted
     */
    suspend fun upsert(vararg entity: E) {
        entity.forEach { upsert(it) }
    }

    /**
     * Upsert the given list of entities into the database.
     *
     * @param entities the list of entities to be upserted
     */
    suspend fun upsert(entities: List<E>) {
        entities.forEach { upsert(it) }
    }
}