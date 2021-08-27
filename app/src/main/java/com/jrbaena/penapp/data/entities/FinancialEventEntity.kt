package com.jrbaena.penapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FinancialEventEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Int,
  val name: String,
  val description: String,
  val value: Double
) {
  constructor(
    name: String,
    description: String,
    value: Double
  ) : this(
    0,
    name,
    description,
    value
  )
}