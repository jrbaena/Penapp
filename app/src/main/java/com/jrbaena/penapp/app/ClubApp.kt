package com.jrbaena.penapp.app

import android.app.Application
import androidx.room.Room
import com.jrbaena.penapp.data.persistance.db.ClubDataBase

class ClubApp : Application() {
  lateinit var room: ClubDataBase

  override fun onCreate() {
    super.onCreate()
    room = Room.databaseBuilder(this, ClubDataBase::class.java, "club.db").build()
  }
}
