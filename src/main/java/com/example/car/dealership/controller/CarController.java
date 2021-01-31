package com.example.car.dealership.controller;

import com.example.car.dealership.model.Car;
import com.example.car.dealership.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CarController {

    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("cars")
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @PostMapping("cars")
    @ResponseStatus(HttpStatus.CREATED)
    public Car addCar(@RequestBody Car car) {
        return carService.addCar(car);
    }

    @GetMapping("/cars/{id}")
    public Optional<Car> getCarById(@PathVariable Long id) {
        return carService.getCarById(id);
    }

    @PutMapping("/cars/{id}")
    public Car updateCar(@PathVariable Long id, @RequestBody Car updatedCar) {
        return carService.updateCar(id,updatedCar);
    }

    @DeleteMapping("/cars/{id}")
    public void deleteById(@PathVariable Long id) {
        carService.deleteById(id);
    }
}
