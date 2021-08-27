package com.jrbaena.penapp.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.jrbaena.penapp.model.Club
import java.time.LocalDate
import java.util.*

@Entity(
  foreignKeys = [ForeignKey(
    entity = ClubEntity::class,
    parentColumns = ["id"],
    childColumns = ["club_id"],
    onDelete = ForeignKey.NO_ACTION
  )]
)
data class EventEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Int,
  val club_id: Int,
  val date: Date,
  val description: String,
  val location: String,
  val is_internal: Boolean
) {
  constructor(
    club_id: Int,
    date: Date,
    description: String,
    location: String,
    is_internal: Boolean
  ) : this(
    0,
    club_id,
    date,
    description,
    location,
    is_internal
  )
}