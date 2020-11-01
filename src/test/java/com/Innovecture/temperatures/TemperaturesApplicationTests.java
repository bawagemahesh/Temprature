package com.Innovecture.temperatures;

import com.Innovecture.temperatures.controller.TemperatureController;
import com.Innovecture.temperatures.model.Hourly;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TemperaturesApplicationTests {

	@Autowired
	TemperatureController  temperatureController;

	@Test
	void contextLoads() {
	}

	@Test
	public void getTemperayureHoulry() throws Exception {
		//Given
		Hourly hourly = new Hourly();
		hourly.setVisibility(10000);
		hourly.setPressure(1022);

		//when
 		List<Hourly> hourlyList = temperatureController.getTemperature("94040");
 		Hourly hourlyByAPI = hourlyList.get(0);

 		//then
		assertThat(hourly.getVisibility()).isEqualTo(hourlyByAPI.getVisibility());
		assertThat(hourly.getPressure()).isEqualTo(hourlyByAPI.getPressure());
	}
}
