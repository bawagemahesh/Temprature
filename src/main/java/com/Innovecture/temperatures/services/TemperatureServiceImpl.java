package com.Innovecture.temperatures.services;

import com.Innovecture.temperatures.model.GoogleCoordinates;
import com.Innovecture.temperatures.model.Hourly;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

@Service
public class TemperatureServiceImpl implements TemperatureService {

    static final String APY_KEY = "b3ed8c1534492444ad9387a5b95f351a";
    static final String APP_ID = "APPID";
    static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    static final String LAT = "lat";
    static final String LON = "lon";
    static final String ZIP = "zip";
    static final String HOURLY = "hourly";
    static final String COORD = "coord";

    @Override
    public List<Hourly> getTemperature(String zip) throws IOException {
        URIBuilder builder;
        HttpGet get = null;
        List<Hourly> hourlyList = null;

        GoogleCoordinates googleCoordinates = getLatitudeLongitute(zip);
        String latitude = googleCoordinates.getLatitute();
        String longitude = googleCoordinates.getLongitute();
        try {
            builder = new URIBuilder(BASE_URL+"onecall?exclude=current,minutely,daily,alerts&");
            builder.setParameter(LAT, latitude)
                    .setParameter(LON, longitude)
                    .setParameter(APP_ID, APY_KEY);
            get = new HttpGet(builder.build());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpClient httpclient = HttpClients.createDefault();
        HttpResponse response = httpclient.execute(get);
        JSONObject jsonGetTemp;
        try {
            if (!(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String rawResult = EntityUtils.toString(entity);
                    jsonGetTemp = new JSONObject(rawResult);
                    JSONArray hourlyArrey = jsonGetTemp.getJSONArray(HOURLY);
                    ObjectMapper mapper = new ObjectMapper();
                    hourlyList = Arrays.asList(mapper.readValue(hourlyArrey.toString(), Hourly[].class));
                    return hourlyList;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hourlyList;
    }

    private GoogleCoordinates getLatitudeLongitute(String zip) {

        URIBuilder builder;
        HttpGet get;
        HttpClient httpclient = HttpClients.createDefault();
        HttpResponse response;
        GoogleCoordinates googleCoordinates = new GoogleCoordinates();
        try {
            builder = new URIBuilder(BASE_URL+"weather?");
            builder.setParameter(ZIP, zip)
                    .setParameter(APP_ID, APY_KEY);
            get = new HttpGet(builder.build());
            response = httpclient.execute(get);
            JSONObject jsonGetTemp;
            if (!(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)) {
                HttpEntity entity = response.getEntity();
                String rawResult = EntityUtils.toString(entity);
                jsonGetTemp = new JSONObject(rawResult);
                JSONObject coord = jsonGetTemp.getJSONObject(COORD);
                googleCoordinates.setLatitute(coord.getString(LAT));
                googleCoordinates.setLongitute(coord.getString(LON));
                return googleCoordinates;
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return googleCoordinates;
    }

}
