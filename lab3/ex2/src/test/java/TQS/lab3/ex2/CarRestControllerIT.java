package TQS.lab3.ex2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @AutoConfigureTestDatabase
// switch AutoConfigureTestDatabase with TestPropertySource to use a real database
@TestPropertySource( locations = "classpath:application-integrationtest.properties")
class CarRestControllerIT {
    
    @LocalServerPort
    private int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository repository;

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    void whenValidInput_thenCreateCar() {
        Car ferrari = new Car("Ferrari", "Roma", "Sports", "Petrol");
        restTemplate.postForEntity("/api/newcar", ferrari, Car.class);

        List<Car> found = repository.findAll();
        assertThat(found).extracting(Car::getMaker).containsOnly("Ferrari");
    }

    @Test
    void givenCars_whenGetCars_thenStatus200() {
        createTestCar("Ferrari", "Roma", "Sports", "Petrol");
        createTestCar("Lamborghini", "Urus", "SUV", "Petrol");

        ResponseEntity<List<Car>> response = restTemplate.exchange(
                "/api/cars", HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Car::getMaker).containsExactly("Ferrari", "Lamborghini");
    }

    @Test
    void whenValidCarId_thenFindReplacementCar() {
        Car ferrari = createTestCar("Ferrari", "Roma", "Sports", "Petrol");
        Car porsche = createTestCar("Porsche", "911", "Sports", "Petrol");

        ResponseEntity<Car> response = restTemplate.exchange(
                "/api/cars/" + ferrari.getCarId() + "/replacement",
                HttpMethod.GET, null, Car.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMaker()).isEqualTo("Porsche");
    }

    @Test
    void whenNoReplacementAvailable_thenReturn404() {
        Car ferrari = createTestCar("Ferrari", "Roma", "Sports", "Petrol");

        ResponseEntity<Car> response = restTemplate.exchange(
                "/api/cars/" + ferrari.getCarId() + "/replacement",
                HttpMethod.GET, null, Car.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private Car createTestCar(String maker, String model, String segment, String motorType) {
        Car car = new Car(maker, model, segment, motorType);
        return repository.saveAndFlush(car);
    }
}
