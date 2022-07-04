package com.edh.capitole.data_access.entities;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Rate(@Id Long id,
                   Long priceId,
                   Integer priority,
                   BigDecimal rateValue,
                   LocalDateTime startDate,
                   LocalDateTime endDate
) {
}
