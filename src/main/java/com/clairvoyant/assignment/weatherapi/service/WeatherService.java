package com.clairvoyant.assignment.weatherapi.service;

import com.clairvoyant.assignment.weatherapi.dto.WeatherDto;
import org.springframework.core.io.Resource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WeatherService {
    void saveWeather(WeatherDto weatherDto);

    Flux<WeatherDto> findAll();

    Mono<WeatherDto> findByName(String name);

    Flux<Resource> getVideo(String title);

    Mono<Void> deleteDocument(String name);

    Flux<WeatherDto>  allChache();

    Mono<Void> deleteALlCache();
}
