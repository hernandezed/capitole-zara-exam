package com.edh.capitole.business.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

public class PriceRateBo {

    private final BigInteger productId;
    private final BigInteger brandId;
    private final BigInteger rateId;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final BigDecimal price;

    public PriceRateBo(BigInteger productId, BigInteger brandId, BigInteger rateId, LocalDateTime startDate, LocalDateTime endDate, BigDecimal price) {
        this.productId = productId;
        this.brandId = brandId;
        this.rateId = rateId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public BigInteger getProductId() {
        return productId;
    }

    public BigInteger getBrandId() {
        return brandId;
    }

    public BigInteger getRateId() {
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
