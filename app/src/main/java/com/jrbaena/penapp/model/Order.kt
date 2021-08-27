package com.jrbaena.penapp.model

data class Order(
  val id : Int,
  val user_id : Int,
  val name : String,
  val description : String,
  val amount: Int
)