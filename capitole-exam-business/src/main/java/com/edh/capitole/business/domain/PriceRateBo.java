package com.edh.capitole.business.domain;

import com.edh.capitole.business.exception.NotApplicableRateException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PriceRateBo {

    private final Long priceId;
    private final Long productId;
    private final Long brandId;
    private RateBo rate;
    private final BigDecimal price;

    public PriceRateBo(Long priceId, Long productId, Long brandId, BigDecimal price) {
        this.priceId = priceId;
        this.productId = productId;
        this.brandId = brandId;
        this.rate = rate;
        this.price = price;
    }

    public Long getPriceId() {
        return priceId;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDateTime getStartDate() {
        return rate.getStartDate();
    }

    public LocalDateTime getEndDate() {
        return rate.getEndDate();
    }

    public BigDecimal getFinalPrice() {
        if (rate == null) {
            throw new NotApplicableRateException();
        }
        return rate.apply(price);
    }

    public void applyRate(RateBo rate) {
        this.rate = rate;
    }
}
