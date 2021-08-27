package com.jrbaena.penapp.data.entities.mapper

import com.jrbaena.penapp.data.entities.FinancialEventEntity
import com.jrbaena.penapp.model.FinancialEvent

object FinancialEventEntityMapper {

  fun transform(financialEventEntity: FinancialEventEntity) : FinancialEvent = financialEventEntity.let {
    FinancialEvent(
      it.id,
      it.name,
      it.description,
      it.value
    )
  }
}
