package com.example.car.dealership;

import com.example.car.dealership.model.Car;
import com.example.car.dealership.repository.CarRepository;
import com.example.car.dealership.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class CarServiceTest {

    CarRepository mockCarRepository;
    CarService carService;

    @BeforeEach
    public void setup() {
        mockCarRepository = mock(CarRepository.class);
        carService = new CarService(mockCarRepository);
    }

    @Test
    public void getAll() {

        ArrayList<Car> cars = new ArrayList<>();

        Car car1 = new Car("mazda","cx5");
        Car car2 = new Car("Toyota","Corolla");
        Car car3 = new Car("Honda","Accord");

        mockCarRepository.save(car1);
        mockCarRepository.save(car2);
        mockCarRepository.save(car3);

        cars.add(car1);
        cars.add(car2);
        cars.add(car3);

        when(mockCarRepository.findAll()).thenReturn(cars);

        List<Car> allCars = carService.getAllCars();

        verify(mockCarRepository).findAll();

        assertThat(allCars).isEqualTo(cars);
    }

    @Test
    public void addCar() {

        Car newCar = new Car("mazda","cx5");

        when(mockCarRepository.save(newCar)).thenReturn(newCar);

        Car actualCar = carService.addCar(newCar);

        verify(mockCarRepository).save(newCar);

        assertThat(actualCar).isEqualTo(newCar);
    }

    @Test
    public void getCarById() {

        Car newCar = new Car("mazda","cx5");

        mockCarRepository.save(newCar);

        when(mockCarRepository.findById(newCar.getId())).thenReturn(java.util.Optional.of(newCar));

        Optional<Car> actualCar = carService.getCarById(newCar.getId());

        verify(mockCarRepository).findById(newCar.getId());

        assertThat(actualCar.get()).isEqualTo(newCar);

    }

    @Test
    public void updateCar() {

        Car newCar = new Car("mazda","cx5");

        mockCarRepository.save(newCar);

        newCar.setMake("Volvo");

        mockCarRepository.deleteById(newCar.getId());

        Optional<Car> oldCar = mockCarRepository.findById(newCar.getId());

        assertTrue(oldCar.isEmpty());

        when(mockCarRepository.save(newCar)).thenReturn(newCar);

        Car actualCar = carService.updateCar(newCar.getId(),newCar);

        assertThat(actualCar).isEqualTo(newCar);

    }

    @Test
    public void deleteById() {

        Car newCar = new Car("mazda","cx5");

        mockCarRepository.save(newCar);

        carService.deleteById(newCar.getId());

        verify(mockCarRepository).deleteById(newCar.getId());

        Optional<Car> actualCar = mockCarRepository.findById(newCar.getId());

        assertTrue(actualCar.isEmpty());
    }
}
