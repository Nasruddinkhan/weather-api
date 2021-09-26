package com.clairvoyant.assignment.weatherapi.handler;

import com.clairvoyant.assignment.weatherapi.dto.WeatherDto;
import com.clairvoyant.assignment.weatherapi.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Service
@AllArgsConstructor
public class WeatherHandler {
    private final WeatherService service;


    public Mono<ServerResponse> weatherResponse(ServerRequest serverRequest){
        System.out.println("WeatherHandler.weatherResponse");
        return ServerResponse.ok().body(service.findAll(), WeatherDto.class);
    }

    public Mono<ServerResponse> getWeathersByName(ServerRequest serverRequest) {
        String name = serverRequest.pathVariable("id");
        return ServerResponse.ok().body(service.findByName(name), WeatherDto.class);
    }

    public Mono<ServerResponse> videoHandler(ServerRequest serverRequest){
        String title = serverRequest.pathVariable("title");
        return ServerResponse.ok()
                .contentType(MediaType.valueOf("video/mp4"))
                .body(service.getVideo(title), Resource.class);
    }
}
