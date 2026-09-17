package com.hotel.reservation.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI hotelReservationOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Hotel Reservation System API")
                        .description(
                                "REST APIs for managing hotel rooms, " +
                                        "customers, reservations, payments and amenities."
                        )
                        .version("1.0"));
    }
}