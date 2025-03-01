package TQS.lab3.ex2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Car findByCarId(Long carId);
    List<Car> findAll();
    List<Car> findBySegmentAndMotorType(String segment, String motorType);
}
