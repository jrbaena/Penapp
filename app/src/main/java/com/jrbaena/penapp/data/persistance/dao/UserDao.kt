package com.jrbaena.penapp.data.persistance.dao

import androidx.room.*
import com.jrbaena.penapp.data.entities.ClubEntity
import com.jrbaena.penapp.data.entities.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity WHERE id = :id")
    suspend fun findById(id: Int): UserEntity
    @Query("SELECT * FROM UserEntity")
    suspend fun getAll(): List<UserEntity>
    @Query("SELECT * FROM UserEntity WHERE name = :name AND password = :password")
    suspend fun findUserByNameAndPassword(name : String, password : String): UserEntity?
    @Query("SELECT * FROM UserEntity WHERE email = :mail")
    suspend fun findUserByMail(mail : String): UserEntity?
    @Query("SELECT * FROM UserEntity WHERE email = :password")
    suspend fun findUserByPassword(password : String): UserEntity?
    @Query("SELECT club_id FROM UserEntity WHERE name = :name")
    suspend fun findClubIdByName(name : String): Long
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userEntity : UserEntity) : Long
    @Update
    suspend fun update(userEntity : UserEntity)
    @Delete
    suspend fun delete(userEntity : UserEntity)
}