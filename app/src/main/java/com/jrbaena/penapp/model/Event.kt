package com.jrbaena.penapp.model

import java.time.LocalDate
import java.util.*

data class Event(
  val id : Int,
  val club_id : Int,
  val date : Date,
  val description : String,
  val location : String,
  val is_internal : Boolean
)