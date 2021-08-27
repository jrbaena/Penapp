package com.jrbaena.penapp.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
  foreignKeys = [ForeignKey(
    entity = UserEntity::class,
    parentColumns = ["id"],
    childColumns = ["user_id"],
    onDelete = ForeignKey.NO_ACTION
  )]
)
data class OrderEntity(
  @PrimaryKey(autoGenerate = true)
  var id: Int,
  val user_id: Int,
  val name: String,
  val description: String,
  val amount: Int
) {
  constructor(
    user_id: Int,
    name: String,
    description: String,
    amount: Int
  ) : this(
    0,
    user_id,
    name,
    description,
    amount
  )
}