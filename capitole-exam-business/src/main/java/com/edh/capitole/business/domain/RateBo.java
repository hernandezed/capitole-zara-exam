package com.edh.capitole.business.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RateBo {

    private final BigDecimal rate;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public RateBo(BigDecimal rate, LocalDateTime startDate, LocalDateTime endDate) {
        this.rate = rate;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public BigDecimal apply(BigDecimal price) {
        return price.multiply(rate);
    }
}
