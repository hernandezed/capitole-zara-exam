package com.edh.capitole.data_access.entities;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Price(@Id Long id,
                    Long productId,
                    Integer priority,
                    // Debido a que R2DBC aun no soporta relaciones, debo mapear directamente el valor de la columna
                    Long brandId,
                    BigDecimal price,
                    String currency,
                    LocalDateTime startDate,
                    LocalDateTime endDate
) {
}
