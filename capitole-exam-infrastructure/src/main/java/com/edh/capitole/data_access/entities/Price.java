package com.edh.capitole.data_access.entities;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

public record Price(@Id Long id,
                    Long productId,
                    BigDecimal amount,
                    String currency
) {
}
