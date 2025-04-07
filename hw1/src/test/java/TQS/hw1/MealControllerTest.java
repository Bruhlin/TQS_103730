package TQS.hw1;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import TQS.hw1.model.Meal;
import TQS.hw1.repository.MealRepository;

@SpringBootTest
@AutoConfigureMockMvc
class MealControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MealRepository mealRepository;

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
    }

    @Test
    void testGetMealsForRestaurant() throws Exception {
        mockMvc.perform(get("/api/meals?restaurant=CantinaMoliceiro"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].restaurant", is("CantinaMoliceiro")))
                .andExpect(jsonPath("$[0].description").exists());
    }

    @Test
    void testGetMealsEmptyForUnknownRestaurant() throws Exception {
        mockMvc.perform(get("/api/meals?restaurant=RestauranteInexistente"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
