package TQS.lab3.ex2;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CarController {

    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/newcar")
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        return new ResponseEntity<Car>(carService.save(car), HttpStatus.CREATED);
    }

    @GetMapping("/cars")
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/cars/{carId}")
    public ResponseEntity<Car> getCarByCarId(@PathVariable Long carId) {
        Car car = carService.getCarDetails(carId);
        HttpStatus code;

        if (car == null) {
            code = HttpStatus.NOT_FOUND;
        } else {
            code = HttpStatus.OK;
        }

        return new ResponseEntity<Car>(car, code);
    }

    @GetMapping("/cars/{carId}/replacement")
    public ResponseEntity<?> findReplacement(@PathVariable Long carId) {
        Optional<Car> replacement = carService.findReplacementCar(carId);
        return replacement.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
