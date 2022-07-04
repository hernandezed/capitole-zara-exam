package com.edh.capitole.infrastructure;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public class GetPriceAtDayTest extends CapitoleExamInfrastructureApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @ParameterizedTest
    @MethodSource("getAllParams")
    void doGet_withParams_doReturnHttpStatus200WithPrice(Long brandId, Long productId, LocalDateTime date, String jsonResponse) {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/prices")
                        .queryParam("brandId", brandId)
                        .queryParam("productId", productId)
                        .queryParam("date", date)
                        .build())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody().json(jsonResponse);
    }

    @Test
    void doGet_withoutBrandId_returnHttpStatus400() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/prices")
                        .queryParam("productId", 1L)
                        .queryParam("date", LocalDateTime.now())
                        .build())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody().json("{\"code\": \"BAD_REQUEST\", \"message\": \"Required Long parameter 'brandId' is not present\"}");
    }

    @Test
    void doGet_withoutProductId_returnHttpStatus400() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/prices")
                        .queryParam("brandId", 1L)
                        .queryParam("date", LocalDateTime.now())
                        .build())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody().json("{\"code\": \"BAD_REQUEST\", \"message\": \"Required Long parameter 'productId' is not present\"}");
    }

    @Test
    void doGet_withoutDate_returnHttpStatus400() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/prices")
                        .queryParam("brandId", 1L)
                        .queryParam("productId", 1L)
                        .build())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody().json("{\"code\": \"BAD_REQUEST\", \"message\": \"Required LocalDateTime parameter 'date' is not present\"}");
    }

    @Test
    void doGet_whenProductNotExists_returnHttpStatus404() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/prices")
                        .queryParam("brandId", 1L)
                        .queryParam("productId", 50L)
                        .queryParam("date", LocalDateTime.of(2020, 6, 14, 10, 0))
                        .build())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectBody().json("{\"code\": \"NOT_FOUND\"}");
    }

    @Test
    void doGet_whenRateNotExists_returnHttpStatus404() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/prices")
                        .queryParam("brandId", 1L)
                        .queryParam("productId", 35455L)
                        .queryParam("date", LocalDateTime.now())
                        .build())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectBody().json("{\"code\": \"NOT_FOUND\"}");
    }

    private static Stream<Arguments> getAllParams() {
        return Stream.of(
                Arguments.arguments(1L, 35455L, LocalDateTime.of(2020, 6, 14, 10, 0), "{\"productId\":35455,\"brandId\":1,\"startDate\":\"2020-06-14T00:00:00\",\"endDate\":\"2020-12-31T23:59:59\",\"price\":30.50}"),
                Arguments.arguments(1L, 35455L, LocalDateTime.of(2020, 6, 14, 16, 0), "{\"productId\":35455,\"brandId\":1,\"startDate\":\"2020-06-14T15:00:00\",\"endDate\":\"2020-06-14T18:30:00\",\"price\":24.4}"),
                Arguments.arguments(1L, 35455L, LocalDateTime.of(2020, 6, 14, 21, 0), "{\"productId\":35455,\"brandId\":1,\"startDate\":\"2020-06-14T00:00:00\",\"endDate\":\"2020-12-31T23:59:59\",\"price\":30.50}"),
                Arguments.arguments(1L, 35455L, LocalDateTime.of(2020, 6, 15, 10, 0), "{\"productId\":35455,\"brandId\":1,\"startDate\":\"2020-06-15T00:00:00\",\"endDate\":\"2020-06-15T11:00:00\",\"price\":30.50}"),
                Arguments.arguments(1L, 35455L, LocalDateTime.of(2020, 6, 16, 21, 0), "{\"productId\":35455,\"brandId\":1,\"startDate\":\"2020-06-15T16:00:00\",\"endDate\":\"2020-12-31T23:59:59\",\"price\":39.65}")
        );
    }
}