package TQS.hw1.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import TQS.hw1.dto.MealWithWeatherDTO;
import TQS.hw1.service.MealService;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    private static final Logger logger = LoggerFactory.getLogger(MealController.class);
    
    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping
    public List<MealWithWeatherDTO> getMeals(@RequestParam String restaurant) {
        logger.info("Meal request to restaurant: {}", restaurant);
        return mealService.getMealsWithWeather(restaurant);
    }
}
