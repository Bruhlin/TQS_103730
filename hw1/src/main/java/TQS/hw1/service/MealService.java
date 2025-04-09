package TQS.hw1.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import TQS.hw1.dto.MealWithWeatherDTO;
import TQS.hw1.model.Meal;
import TQS.hw1.repository.MealRepository;

@Service
public class MealService {
    
    private static final Logger logger = LoggerFactory.getLogger(MealService.class);
    
    private final MealRepository mealRepository;
    private final WeatherService weatherService;

    public MealService(MealRepository mealRepository, WeatherService weatherService) {
        this.mealRepository = mealRepository;
        this.weatherService = weatherService;
    }

    public List <MealWithWeatherDTO> getMealsWithWeather(String restaurant) {

        String sanitizedRestaurant = restaurant != null ? restaurant.replaceAll("[\n\r\t]", "_") : "unknown";

        logger.info("Fetching meals for restaurant: {}", sanitizedRestaurant);
        List<Meal> meals = mealRepository.findByRestaurantAndDateAfter(restaurant, LocalDate.now());
        Map<LocalDate, String> forecast = weatherService.getWeatherForecastByDate();

        logger.info("Fetched {} meals", meals.size());

        return meals.stream()
            .map(meal ->  {
                String weather = forecast.getOrDefault(meal.getDate(), "Not found");
                logger.debug("{}: {} (weather: {})", meal.getDate(), meal.getDescription(), weather);
                return new MealWithWeatherDTO(
                    meal.getRestaurant(),
                    meal.getDate(),
                    meal.getDescription(),
                    weather
                );
            })
            .toList();
    }
}
