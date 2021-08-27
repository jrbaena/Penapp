package com.jrbaena.penapp.data.entities.mapper

import com.jrbaena.penapp.data.entities.EventEntity
import com.jrbaena.penapp.model.Event
import org.w3c.dom.Entity
import java.util.*

object EventEntityMapper {

  fun transform(eventEntity: EventEntity) : Event = eventEntity.let {
    Event(
      it.id,
      it.club_id,
      it.date,
      it.description,
      it.location,
      it.is_internal
    )
  }
}