package com.jrbaena.penapp.data.entities.mapper

import com.jrbaena.penapp.data.entities.UserEntity
import com.jrbaena.penapp.model.User

object UserEntityMapper {

  fun transform(userEntity: UserEntity) : User = userEntity.let {
    User(
      it.id,
      it.club_id,
      it.name,
      it.email,
      it.password,
      it.is_admin,
      it.created_at,
      it.updated_at
    )
  }
}