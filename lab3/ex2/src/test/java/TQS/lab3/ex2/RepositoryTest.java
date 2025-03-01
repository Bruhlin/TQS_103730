package TQS.lab3.ex2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class RepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    @Test
    void whenFindCarByCarId_thenReturnCar() {
        Car roma = new Car("Ferrari", "Roma");
        entityManager.persistAndFlush(roma);

        Car fromDb = carRepository.findByCarId(roma.getCarId());
        assertThat(fromDb).isNotNull();
        assertThat(fromDb.getModel()).isEqualTo(roma.getModel());
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        Car car = carRepository.findByCarId(-20L);
        assertThat(car).isNull();
    }

    @Test
    void givenSetOfCars_whenFindAll_thenReturnAllCars() {
        Car roma = new Car("Ferrari", "Roma");
        Car urus = new Car("Lamborghini", "Urus");
        Car evoque = new Car("Range Rover", "Evoque");

        entityManager.persist(roma);
        entityManager.persist(urus);
        entityManager.persist(evoque);
        entityManager.flush();

        List<Car> allCars = carRepository.findAll();
        assertThat(allCars).hasSize(3).extracting(Car::getMaker).containsOnly(roma.getMaker(), urus.getMaker(), evoque.getMaker());
    }
}
