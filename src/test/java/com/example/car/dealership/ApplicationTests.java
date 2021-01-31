package com.example.car.dealership;

import com.example.car.dealership.model.Car;
import com.example.car.dealership.repository.CarRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

	@Autowired
	MockMvc mockMvc;

	ObjectMapper  objectMapper;

	@Autowired
	CarRepository carRepository;

	@BeforeEach
	void setup() {
		objectMapper = new ObjectMapper();
	}

	@Test
	public void getAllCars() throws Exception {

		String expectedCars = objectMapper.writeValueAsString(carRepository.findAll());

		String actualCars = mockMvc.perform(get("/cars"))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		assertThat(expectedCars).isEqualTo(actualCars);
	}

	@Test
	public void addCar() throws Exception {

		Car car3 = new Car("Honda","Accord");

		String body = objectMapper.writeValueAsString(car3);

		mockMvc.perform(post("/cars")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.make").value("Honda"))
				.andExpect(jsonPath("$.model").value("Accord"))
				.andExpect(jsonPath("$.id").exists());
	}

	@Test
	public void getCarById() throws Exception {

		Car newCar = carRepository.save(new Car("mazda","cx5"));

		mockMvc.perform(get("/cars/"+newCar.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(newCar.getId()))
				.andExpect(jsonPath("$.make").value("mazda"))
				.andExpect(jsonPath("$.model").value("cx5"));

	}

	@Test
	public void updateCar() throws Exception {
		Car newCar = carRepository.save(new Car("BMW","i3"));
		newCar.setMake("Tesla");
		String body = objectMapper.writeValueAsString(newCar);
		mockMvc.perform(put("/cars/"+newCar.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.make").value("Tesla"))
				.andExpect(jsonPath("$.model").value("i3"))
				.andExpect(jsonPath("$.id").exists());

		mockMvc.perform(get("/cars/"+newCar.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").doesNotExist());
	}

	@Test
	public void deleteCarById() throws Exception {
		Car newCar = carRepository.save(new Car("Hyundai","Sonata"));
		mockMvc.perform(delete("/cars/"+newCar.getId()))
				.andExpect(status().isOk());
		mockMvc.perform(get("/cars/"+newCar.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").doesNotExist());
	}


}
