package com.edh.capitole.api.controllers;

import com.edh.capitole.api.dtos.ErrorDto;
import com.edh.capitole.api.dtos.PriceRateDto;
import com.edh.capitole.business.domain.PriceRateBo;
import com.edh.capitole.business.exception.NoSuchPriceException;
import com.edh.capitole.business.usecases.FindPriceRateAtDayUseCase;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("prices")
public class PriceController {

    private final FindPriceRateAtDayUseCase findPriceRateAtDayUseCase;

    public PriceController(FindPriceRateAtDayUseCase findPriceRateAtDayUseCase) {
        this.findPriceRateAtDayUseCase = findPriceRateAtDayUseCase;
    }

    @GetMapping
    public Mono<PriceRateDto> getPrice(@RequestParam Long brandId,
                                       @RequestParam Long productId,
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                       @RequestParam LocalDateTime date) {
        return findPriceRateAtDayUseCase
                .execute(productId, brandId, date)
                .map(this::mapPriceRateBoToPriceRateDto);
    }

    private PriceRateDto mapPriceRateBoToPriceRateDto(PriceRateBo priceRateBo) {
        return new PriceRateDto(priceRateBo.getProductId(), priceRateBo.getBrandId(),
                priceRateBo.getStartDate(), priceRateBo.getEndDate(), priceRateBo.getFinalPrice());
    }


    @ExceptionHandler(ServerWebInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleException(ServerWebInputException e) {
        return new ErrorDto(HttpStatus.BAD_REQUEST.name(), e.getReason());
    }

    @ExceptionHandler(NoSuchPriceException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleException(NoSuchPriceException e) {
        return new ErrorDto(HttpStatus.NOT_FOUND.name());
    }

}
