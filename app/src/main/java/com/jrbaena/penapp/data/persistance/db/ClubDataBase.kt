package com.jrbaena.penapp.data.persistance.db;

import androidx.room.Database;
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jrbaena.penapp.data.entities.*
import com.jrbaena.penapp.data.persistance.dao.*

@Database(
  entities = [
    ClubEntity::class,
    EventEntity::class,
    FinancialEventEntity::class,
    OrderEntity::class,
    UserEntity::class
  ],
  version = 1
)
@TypeConverters(Converters::class)
abstract class ClubDataBase : RoomDatabase() {
  abstract fun ClubDao(): ClubDao
  abstract fun EventDao(): EventDao
  abstract fun FinancialEventsDao(): FinancialEventDao
  abstract fun OrderDao(): OrderDao
  abstract fun UserDao(): UserDao
}
