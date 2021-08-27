package com.jrbaena.penapp.data.entities.mapper

import com.jrbaena.penapp.data.entities.OrderEntity
import com.jrbaena.penapp.model.Order

object OrderEntityMapper {
  fun transform(orderEntity: OrderEntity) : Order = orderEntity.let {
    Order(
      it.id,
      it.user_id,
      it.name,
      it.description,
      it.amount
    )
  }
}