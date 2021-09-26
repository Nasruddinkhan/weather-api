package com.clairvoyant.assignment.weatherapi.repository;

import com.clairvoyant.assignment.weatherapi.documents.WeatherDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface WeatherRepo extends ReactiveMongoRepository<WeatherDocument, String> {
    Mono<WeatherDocument> findAllByName(String name);

    @Tailable
    Flux<WeatherDocument> findWithTailableCursorBy();
}
