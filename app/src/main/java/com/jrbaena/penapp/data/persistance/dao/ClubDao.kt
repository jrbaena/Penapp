package com.jrbaena.penapp.data.persistance.dao

import androidx.room.*
import com.jrbaena.penapp.data.entities.ClubEntity

@Dao
interface ClubDao {
    @Query("SELECT * FROM ClubEntity WHERE id = :id")
    suspend fun findById(id: Int): ClubEntity
    @Query("SELECT * FROM ClubEntity")
    suspend fun getAll(): List<ClubEntity>
    @Query("SELECT ClubCode FROM ClubEntity")
    suspend fun getCode(): String
    @Query("SELECT * FROM ClubEntity WHERE ClubCode = :code")
    suspend fun findOneByCode(code : String): ClubEntity?
    @Query("SELECT * FROM ClubEntity WHERE name = :name")
    suspend fun findOneByName(name : String): ClubEntity?
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(clubEntity : ClubEntity) : Long
    @Update
    suspend fun update(clubEntity : ClubEntity)
    @Delete
    suspend fun delete(clubEntity : ClubEntity)
}