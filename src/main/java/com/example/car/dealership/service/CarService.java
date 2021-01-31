package com.example.car.dealership.service;

import com.example.car.dealership.model.Car;
import com.example.car.dealership.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    public Car updateCar(Long id,Car updatedCar) {
        carRepository.deleteById(id);
        return carRepository.save(updatedCar);
    }

    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }
}
