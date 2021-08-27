package com.jrbaena.penapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ClubEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    val name: String,
    val ClubCode : String
){
    constructor(name : String, clubCode : String) : this(0 , name, clubCode)
}