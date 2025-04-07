package TQS.hw1.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import TQS.hw1.dto.MealWithWeatherDTO;
import TQS.hw1.model.Meal;
import TQS.hw1.repository.MealRepository;

@Service
public class MealService {
    
    private final MealRepository mealRepository;
    private final WeatherService weatherService;

    public MealService(MealRepository mealRepository, WeatherService weatherService) {
        this.mealRepository = mealRepository;
        this.weatherService = weatherService;
    }

    public List <MealWithWeatherDTO> getMealsWithWeather(String restaurant) {
        List<Meal> meals = mealRepository.findByRestaurantAndDateAfter(restaurant, LocalDate.now());
        Map<LocalDate, String> forecast = weatherService.getWeatherForecastByDate();

        return meals.stream()
            .map(meal -> new MealWithWeatherDTO(
                meal.getRestaurant(),
                meal.getDate(),
                meal.getDescription(),
                forecast.getOrDefault(meal.getDate(), "not found")
            ))
            .toList();
    }
}
