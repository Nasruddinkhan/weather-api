package com.clairvoyant.assignment.weatherapi.documents;

import com.clairvoyant.assignment.weatherapi.dto.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class WeatherDocument {
    private Coord coord;
    private List<Weather> weather;
    private String base;
    private Main main;
    private int visibility;
    private Wind wind;
    private Clouds clouds;
    private int dt;
    private Sys sys;
    private int timezone;
    private int id;
    @Id
    private String name;
    private int cod;
}

