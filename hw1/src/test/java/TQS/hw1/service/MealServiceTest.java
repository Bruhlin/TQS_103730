package TQS.hw1.service;

import TQS.hw1.dto.MealWithWeatherDTO;
import TQS.hw1.model.Meal;
import TQS.hw1.repository.MealRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MealServiceTest {

    private MealRepository mealRepository;
    private WeatherService weatherService;
    private MealService mealService;

    @BeforeEach
    void setUp() {
        mealRepository = mock(MealRepository.class);
        weatherService = mock(WeatherService.class);
        mealService = new MealService(mealRepository, weatherService);
    }

    @Test
    void testGetMealsWithWeather() {
        LocalDate date = LocalDate.now().plusDays(1);
        Meal meal = new Meal("Restaurant1", date, "Bacalhau com Natas");

        when(mealRepository.findByRestaurantAndDateAfter("Restaurant1", LocalDate.now()))
                .thenReturn(List.of(meal));
        when(weatherService.getWeatherForecastByDate())
                .thenReturn(Map.of(date, "rain"));

        List<MealWithWeatherDTO> result = mealService.getMealsWithWeather("Restaurant1");

        assertEquals(1, result.size());
        assertEquals("rain", result.get(0).getWeather());
        assertEquals("Bacalhau com Natas", result.get(0).getDescription());
    }
}
