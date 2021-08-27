package com.jrbaena.penapp.data.persistance.dao

import androidx.room.*
import com.jrbaena.penapp.data.entities.EventEntity

@Dao
interface EventDao {
    @Query("SELECT * FROM EventEntity WHERE id = :id")
    suspend fun findById(id: Int): EventEntity
    @Query("SELECT * FROM EventEntity")
    suspend fun getAll(): List<EventEntity>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(eventEntity : EventEntity)
    @Update
    suspend fun update(eventEntity : EventEntity)
    @Delete
    suspend fun delete(eventEntity : EventEntity)
}