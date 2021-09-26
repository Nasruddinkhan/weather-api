package com.clairvoyant.assignment.weatherapi.confiq;

import com.clairvoyant.assignment.weatherapi.dto.WeatherDto;
import com.clairvoyant.assignment.weatherapi.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;

@Configuration
@EnableScheduling
@AllArgsConstructor
public class MyConfig {
    private final WeatherService weatherService;

 //  @Scheduled(fixedRate = 100000)
    private void publish() {
        String url = "https://api.openweathermap.org/data/2.5/weather?zip=" + getPincode() + ",IN&appid=6aa468dc488d1c9a0acc7bf6c9bee316";
        System.out.println("URL = " + url);
        WebClient.builder()
                .baseUrl(url)
                .build()
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(WeatherDto.class)
                .publishOn(Schedulers.parallel())
                .doOnNext(weatherService::saveWeather)
                .onErrorContinue((err, obj) -> {
                    System.out.println("OnErrorPipeline.main ");
                    System.out.println("OnErrorPipeline.main err");
                }).retryWhen(Retry.fixedDelay(2, Duration.ofSeconds(3)))
                .subscribe();
    }

    private static int getPincode() {
        return (int) (Math.random() * (400100 - 400000 + 1) + 400000);
    }


}
