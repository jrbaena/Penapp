package com.jrbaena.penapp.data.persistance.dao

import androidx.room.*
import com.jrbaena.penapp.data.entities.OrderEntity

@Dao
interface OrderDao {
    @Query("SELECT * FROM OrderEntity WHERE id = :id")
    suspend fun findById(id: Int): OrderEntity
    @Query("SELECT * FROM OrderEntity")
    suspend fun getAll(): List<OrderEntity>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(orderEntity : OrderEntity): Long
    @Update
    suspend fun update(orderEntity : OrderEntity)
    @Delete
    suspend fun delete(orderEntity : OrderEntity)
}