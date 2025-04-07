package TQS.hw1.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import TQS.hw1.model.Meal;
import TQS.hw1.repository.MealRepository;

@Service
public class MealService {
    
    private final MealRepository mealRepository;

    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public List <Meal> getUpcomingMeals(String restaurant) {
        return mealRepository.findByRestaurantAndDateAfter(restaurant, LocalDate.now().minusDays(1));
    }
}
