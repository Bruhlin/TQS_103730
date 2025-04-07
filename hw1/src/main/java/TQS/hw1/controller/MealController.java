package TQS.hw1.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import TQS.hw1.dto.MealWithWeatherDTO;
import TQS.hw1.service.MealService;

@RestController
@RequestMapping("/api/meals")
public class MealController {
    
    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping
    public List<MealWithWeatherDTO> getMeals(@RequestParam String restaurant) {
        return mealService.getMealsWithWeather(restaurant);
    }
}
