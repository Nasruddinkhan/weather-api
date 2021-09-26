package com.clairvoyant.assignment.weatherapi.controller;

import com.clairvoyant.assignment.weatherapi.documents.WeatherDocument;
import com.clairvoyant.assignment.weatherapi.dto.WeatherDto;
import com.clairvoyant.assignment.weatherapi.repository.WeatherRepo;
import com.clairvoyant.assignment.weatherapi.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.ReactiveListOperations;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class WeatherApiController {
    private final WeatherService weatherService;

    @GetMapping(value = "/fluxstream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    private Flux<WeatherDto> returnFluxStream() {
        return weatherService.findAll();
    }

    @GetMapping(value = "/video/{title}", produces = "video/mp4")
    public  Flux<Resource> getVideo(@PathVariable String title, @RequestHeader("Range") String range) {
        System.out.println(range);
        return weatherService.getVideo(title);
    }

    @GetMapping(value = "/weather/{name}")
    public Mono<WeatherDto> getWeatherData(@PathVariable String name) {
        System.out.println("WeatherApiController.getWeatherData ["+name+"]");
        return weatherService.findByName(name);
    }

    @DeleteMapping(value = "/weather/{name}")
    public Mono<Void> deleteDocument(@PathVariable String name) {
        System.out.println("WeatherApiController.getWeatherData ["+name+"]");
        return weatherService.deleteDocument(name);
    }

    @GetMapping(value = "/weather/all-cache")
    public Flux<WeatherDto> allChache() {
        return weatherService.allChache();
    }
    @DeleteMapping(value = "/weather/all")
    public Mono<Void> deleteALlCache() {
        return weatherService.deleteALlCache();
    }
}
