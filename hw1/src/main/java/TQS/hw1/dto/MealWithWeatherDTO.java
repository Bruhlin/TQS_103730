package TQS.hw1.dto;

import java.time.LocalDate;

public class MealWithWeatherDTO {
    
    private String restaurant;
    private LocalDate date;
    private String description;
    private String weather;

    public MealWithWeatherDTO() {}

    public MealWithWeatherDTO(String restaurant, LocalDate date, String description, String weather) {
        this.restaurant = restaurant;
        this.date = date;
        this.description = description;
        this.weather = weather;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
