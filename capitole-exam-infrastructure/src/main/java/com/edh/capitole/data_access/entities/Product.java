package com.edh.capitole.data_access.entities;

import org.springframework.data.annotation.Id;

public record Product(@Id Long id,
                      Long productCode,
                      Long brandId,
                      String name) {
}
