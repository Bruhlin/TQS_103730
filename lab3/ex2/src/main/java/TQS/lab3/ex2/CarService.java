package TQS.lab3.ex2;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCarDetails(Long carId) {
        return carRepository.findByCarId(carId);
    }

    public Car getCar(Long carId) {
        return carRepository.findByCarId(carId);
    }

    public Optional<Car> findReplacementCar(Long carId) {
        Car originalCar = carRepository.findByCarId(carId);
        if (originalCar == null) {
            return Optional.empty();
        }

        List<Car> potentialReplacements = carRepository.findBySegmentAndMotorType(originalCar.getSegment(), originalCar.getMotorType());

        return potentialReplacements.stream()
                .filter(car -> !car.getCarId().equals(carId))
                .findFirst();
    }
}
