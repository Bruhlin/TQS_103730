package TQS.hw1.repository;

import TQS.hw1.model.Meal;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByRestaurantAndDateAfter(String restaurant, LocalDate date);

    Optional<Meal> findByRestaurantAndDate(String restaurant, LocalDate date);

    boolean existsByRestaurantAndDate(String string, LocalDate date);   
}
