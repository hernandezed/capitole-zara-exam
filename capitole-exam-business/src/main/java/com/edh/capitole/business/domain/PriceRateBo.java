package com.edh.capitole.business.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PriceRateBo {

    private final Long productId;
    private final Long brandId;
    private final Long rateId;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final BigDecimal price;

    public PriceRateBo(Long productId, Long brandId, Long rateId, LocalDateTime startDate, LocalDateTime endDate, BigDecimal price) {
        this.productId = productId;
        this.brandId = brandId;
        this.rateId = rateId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public Long getRateId() {
        return rateId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
