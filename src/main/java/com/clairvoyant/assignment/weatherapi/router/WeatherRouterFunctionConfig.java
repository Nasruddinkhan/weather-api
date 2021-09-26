package com.clairvoyant.assignment.weatherapi.router;

import com.clairvoyant.assignment.weatherapi.dto.WeatherDto;
import com.clairvoyant.assignment.weatherapi.handler.WeatherHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
@AllArgsConstructor
public class WeatherRouterFunctionConfig {
    private final WeatherHandler weatherHandler;

    @RouterOperations(value = {
            @RouterOperation(path = "/weather",
                    beanClass = WeatherHandler.class,
                    beanMethod = "weatherResponse",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            summary = "Trigger an exception",
                            responses = @ApiResponse(
                                    responseCode = "200",
                                    description = "successful operation",
                                    content = @Content(
                                            array = @ArraySchema(arraySchema = @Schema(implementation = WeatherDto.class))
                                    ))

                    )
            ),
            @RouterOperation(path = "/weather/{id}", beanClass = WeatherHandler.class,method = RequestMethod.GET, beanMethod = "getWeathersByName"
                    ,operation = @Operation(operationId = "get name",
                    parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "name", required = true)},
                    responses = @ApiResponse(content = @Content(schema = @Schema(implementation = WeatherDto.class)))
            )
            )}
    )

    @Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunction() {
        System.out.println("WeatherRouterFunctionConfig.serverResponseRouterFunction");
        return RouterFunctions.route(RequestPredicates.GET("/weather").and(accept(MediaType.APPLICATION_JSON)),
                weatherHandler::weatherResponse)
                .andRoute(RequestPredicates.GET("/weather/{id}").and(accept(MediaType.APPLICATION_JSON)), weatherHandler::getWeathersByName)
                .andRoute(RequestPredicates.GET("/video/{title}").and(accept(MediaType.APPLICATION_JSON)), weatherHandler::videoHandler);
    }
}
