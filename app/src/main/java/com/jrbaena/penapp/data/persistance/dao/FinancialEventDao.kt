package com.jrbaena.penapp.data.persistance.dao

import androidx.room.*
import com.jrbaena.penapp.data.entities.FinancialEventEntity

@Dao
interface FinancialEventDao {
  @Query("SELECT * FROM FinancialEventEntity WHERE id = :id")
  suspend fun findById(id: Int): FinancialEventEntity
  @Query("SELECT * FROM FinancialEventEntity")
  suspend fun getAll(): FinancialEventEntity
  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insert(clubEntity : FinancialEventEntity)
  @Update
  suspend fun update(clubEntity : FinancialEventEntity)
  @Delete
  suspend fun delete(clubEntity : FinancialEventEntity)
}