package com.jrbaena.penapp.data.entities.mapper

import com.jrbaena.penapp.data.entities.ClubEntity
import com.jrbaena.penapp.model.Club

object ClubEntityMapper {

  fun transform(clubEntity : ClubEntity) : Club = clubEntity.let {
    Club(
      it.id,
      it.name,
      it.ClubCode
    )
  }
}