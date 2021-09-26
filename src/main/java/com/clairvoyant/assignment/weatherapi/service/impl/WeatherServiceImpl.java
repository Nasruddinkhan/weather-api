package com.clairvoyant.assignment.weatherapi.service.impl;

import com.clairvoyant.assignment.weatherapi.documents.WeatherDocument;
import com.clairvoyant.assignment.weatherapi.dto.WeatherDto;
import com.clairvoyant.assignment.weatherapi.mapper.MapperUtil;
import com.clairvoyant.assignment.weatherapi.repository.WeatherRepo;
import com.clairvoyant.assignment.weatherapi.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sun.reflect.generics.tree.VoidDescriptor;

import java.time.Duration;
import java.util.stream.Stream;

@Service
@Transactional
@AllArgsConstructor
public class WeatherServiceImpl implements WeatherService {
    private final WeatherRepo weatherRepo;
    private static final String FORMAT = "classpath:video/%s.mp4";
    private final ResourceLoader resourceLoader;
    private final ReactiveRedisTemplate<String, WeatherDto> redisTemplate;
    private final ReactiveRedisOperations<String, WeatherDto> weatherOps;


    @Override
    public void saveWeather(WeatherDto weatherDto) {
       // System.out.println("WeatherServiceImpl.saveWeather ["+ redisTemplate.opsForValue().set(weatherDto.getName(), weatherDto).subscribe()+"]");;
        WeatherDocument weatherDocument = MapperUtil.map(weatherDto, WeatherDocument.class);
         weatherRepo.save(weatherDocument).log().subscribe();

    }

    @Override
    public Flux<WeatherDto> findAll() {
        return weatherRepo.findAll()
                .map(m -> MapperUtil.map(m, WeatherDto.class)).cast(WeatherDto.class);
    }

    @Override
    public Mono<WeatherDto> findByName(String name) {
        System.out.println("WeatherServiceImpl.findByName");
       return redisTemplate.opsForValue().get(name)
               .log().switchIfEmpty(getObjectFormDB(name)).log();

    }

    private Mono< WeatherDto> getObjectFormDB(String name) {

       return weatherRepo.findById(name)
                .log().handle((weatherDocument, synchronousSink) -> {
            System.out.println("WeatherServiceImpl.putting the object in cache");
                    WeatherDto weatherDto =     MapperUtil.map(weatherDocument, WeatherDto.class);

            redisTemplate.opsForValue().set(weatherDocument.getName(), weatherDto).log().subscribe();
            synchronousSink.next(weatherDto);
            synchronousSink.complete();
        });
    }


    @Override
    public Flux<Resource> getVideo(String title) {
        System.out.println("WeatherServiceImpl.getVideo ["+this.resourceLoader.getResource( String.format(FORMAT, title)+"]"));
        return Flux.fromStream(() -> Stream.of( this.resourceLoader.getResource( String.format(FORMAT, title))))
                .delayElements(Duration.ofSeconds(1))
                .publish()
                .autoConnect(0);
    }

    @Override
    public Mono<Void> deleteDocument(String name) {

        return Mono.fromRunnable(()->redisTemplate.opsForValue().delete(name).log().subscribe());

    }

    @Override
    public Flux<WeatherDto> allChache() {
       return weatherOps.keys("*")
               .flatMap(weatherOps.opsForValue()::get);

    }

    @Override
    public Mono<Void> deleteALlCache() {
        return
                Mono.fromRunnable(()-> weatherOps.keys("*")
                        .flatMap(weatherOps.opsForValue()::delete).subscribe());

    }
}
