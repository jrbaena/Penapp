package com.jrbaena.penapp.model

import java.util.*

data class User (
  val id : Int,
  val club_id : Int,
  val name : String,
  val email : String,
  val password : String,
  val is_admin : Boolean,
  val created_at : Date,
  val updated_at : Date
  )