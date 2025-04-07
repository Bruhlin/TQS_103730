package TQS.hw1.controller;

import java.time.LocalDate;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import TQS.hw1.model.Meal;
import TQS.hw1.repository.MealRepository;
import TQS.hw1.service.WeatherService;

@SpringBootTest
@AutoConfigureMockMvc
class MealControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MealRepository mealRepository;

    @MockBean
    private WeatherService  weatherService;

    @BeforeEach
    void setUp() {
        mealRepository.deleteAll();

        Meal meal1 = new Meal();

        meal1.setRestaurant("CantinaMoliceiro");
        meal1.setDate(LocalDate.now().plusDays(1));
        meal1.setDescription(("Bacalhau com natas"));

        Meal meal2 = new Meal();
        meal2.setRestaurant("CantinaMoliceiro");
        meal2.setDate(LocalDate.now().plusDays(2));
        meal2.setDescription("Arroz de Pato");

        mealRepository.save(meal1);
        mealRepository.save(meal2);

        when(weatherService.getWeatherForecastByDate()).thenReturn(
            Map.of(
                meal1.getDate(), "Sunny",
                meal2.getDate(), "Rainy"
        ));
    }

    @Test
    void testGetMealsForRestaurant() throws Exception {
        mockMvc.perform(get("/api/meals?restaurant=CantinaMoliceiro"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].restaurant", is("CantinaMoliceiro")))
                .andExpect(jsonPath("$[0].description").exists())
                .andExpect(jsonPath("$[0].weather", is("Sunny")))
                .andExpect(jsonPath("$[1].weather", is("Rainy")));
    }

    @Test
    void testGetMealsEmptyForUnknownRestaurant() throws Exception {
        mockMvc.perform(get("/api/meals?restaurant=RestauranteInexistente"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
