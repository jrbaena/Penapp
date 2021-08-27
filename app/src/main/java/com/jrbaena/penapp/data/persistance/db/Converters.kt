package com.jrbaena.penapp.data.persistance.db

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

import java.util.Date


class Converters {

  @TypeConverter
  fun fromTimestamp(value: Long?): Date? {
    return value?.let { Date(it) }
  }

  @TypeConverter
  fun dateToTimestamp(date: Date?): Long? {
    return date?.time?.toLong()
  }

//  @RequiresApi(Build.VERSION_CODES.O)
//  @TypeConverter
//  fun localDateToTimestamp(date: LocalDate?): Long {
//    val zdt = ZonedDateTime.of(date!!.atTime(0, 0, 0), ZoneId.systemDefault())
//    return zdt.toInstant().toEpochMilli()
//  }
}