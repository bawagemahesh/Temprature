package com.Innovecture.temperatures.services;

import com.Innovecture.temperatures.model.Hourly;

import java.io.IOException;
import java.util.List;

public interface TemperatureService {
    List<Hourly> getTemperature(String zip) throws IOException;
}
