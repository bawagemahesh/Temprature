package com.Innovecture.temperatures.controller;


import com.Innovecture.temperatures.model.Hourly;
import com.Innovecture.temperatures.model.Temperature;
import com.Innovecture.temperatures.services.TemperatureService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



import java.io.IOException;
import java.util.List;

@RestController
@ResponseBody
public class TemperatureController {

    @Autowired
    TemperatureService temperatureService;

    @GetMapping(path="/{zip}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Hourly> getTemperature(@PathVariable("zip") String zip){
        List<Hourly> hourlyList= null;
        try {
            hourlyList= temperatureService.getTemperature(zip);
        } catch (IOException e) {
            e.printStackTrace();
        }
       return hourlyList;
    }

}
