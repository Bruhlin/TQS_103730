package TQS.lab3.ex2;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ServiceTest {
    
    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    public void setUp() {
        Car roma = new Car("Ferrari", "Roma", "Sports", "Petrol");
        roma.setCarId(111L);

        Car urus = new Car("Lamborghini", "Urus", "SUV", "Petrol");
        urus.setCarId(222L);

		Car evoque = new Car("Range Rover", "Evoque", "SUV", "Diesel");
        evoque.setCarId(333L);

        when(carRepository.findByCarId(111L)).thenReturn(roma);
        when(carRepository.findBySegmentAndMotorType("Sports", "Petrol"))
                .thenReturn(Arrays.asList(roma));

        when(carRepository.findByCarId(222L)).thenReturn(urus);
        when(carRepository.findBySegmentAndMotorType("SUV", "Petrol"))
                .thenReturn(Arrays.asList(urus, evoque));
            
        when(carRepository.findByCarId(-20L)).thenReturn(null);

        List<Car> cars = Arrays.asList(roma, urus, evoque);
        when(carRepository.findAll()).thenReturn(cars);
    }

    @Test
    void whenValidId_thenCarShouldBeFound() {
        Car car = carService.getCarDetails(111L);

        assertThat(car.getMaker()).isEqualTo("Ferrari");
    }

    @Test
    void whenInvalidId_thenCarShouldNotBeFound() {
        Car car = carService.getCarDetails(-20L);

        assertThat(car).isNull();
    }

    @Test
    void given3Cars_whenGetAll_thenReturn3Records() {
        Car roma = new Car("Ferrari", "Roma");
        Car urus = new Car("Lamborghini", "Urus");
        Car evoque = new Car("Range Rover", "Evoque");

        List<Car> allCars = carService.getAllCars();
        verifyFindAllCarsIsCalledOnce();
        assertThat(allCars).hasSize(3).extracting(Car::getMaker).contains(roma.getMaker(), urus.getMaker(), evoque.getMaker());
    }

    @Test
    void whenReplacementsExists_thenReturnReplacement() {
        Optional<Car> replacement = carService.findReplacementCar(222L);
        assertThat(replacement).isPresent();
        assertThat(replacement.get().getMaker()).isEqualTo("Range Rover");
    }

    @Test
    void whenNoReplacementExists_thenReturnEmpty() {
        Optional<Car> replacement = carService.findReplacementCar(111L);
        assertThat(replacement).isEmpty();
    }

    private void verifyFindAllCarsIsCalledOnce() {
        verify(carRepository, VerificationModeFactory.times(1)).findAll();
    }

}
