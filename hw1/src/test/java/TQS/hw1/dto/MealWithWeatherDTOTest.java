package TQS.hw1.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class MealWithWeatherDTOTest {
    
    @Test
    void testGettersAndSetters() {
        MealWithWeatherDTO dto = new MealWithWeatherDTO();
        dto.setRestaurant("Cantina Castro");
        dto.setDate(LocalDate.of(2025, 4, 10));
        dto.setDescription("Bacalhau com Natas");
        dto.setWeather("Rain");

        assertEquals("Cantina Castro", dto.getRestaurant());
        assertEquals(LocalDate.of(2025, 4, 10), dto.getDate());
        assertEquals("Bacalhau com Natas", dto.getDescription());
        assertEquals("Rain", dto.getWeather());
    }

    @Test
    void testConstructor() {
        LocalDate date = LocalDate.of(2025, 4, 10);
        MealWithWeatherDTO dto = new MealWithWeatherDTO("Cantina Santiago", date, "Arroz de Pato", "Sunny");

        assertEquals("Cantina Santiago", dto.getRestaurant());
        assertEquals(date, dto.getDate());
        assertEquals("Arroz de Pato", dto.getDescription());
        assertEquals("Sunny", dto.getWeather());
    }
}
