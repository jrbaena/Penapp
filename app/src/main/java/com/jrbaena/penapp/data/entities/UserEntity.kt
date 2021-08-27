package com.jrbaena.penapp.data.entities

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

@Entity(
  foreignKeys = [ForeignKey(
    entity = ClubEntity::class,
    parentColumns = ["id"],
    childColumns = ["club_id"],
    onDelete = ForeignKey.NO_ACTION
  )]
)
data class UserEntity (
  @PrimaryKey(autoGenerate = true)
  var id: Int,
  val club_id: Int,
  val name: String,
  val email: String,
  val password: String,
  val is_admin: Boolean,
  val created_at: Date,
  val updated_at: Date
) {
  @RequiresApi(Build.VERSION_CODES.O)
  constructor(
    club_id: Int,
    name: String,
    email: String,
    password: String,
    is_admin: Boolean
  ) : this(
    0,
    club_id,
    name,
    email,
    password,
    is_admin,
    Date.from(Instant.now()),
    Date.from(Instant.now())
  )
}
