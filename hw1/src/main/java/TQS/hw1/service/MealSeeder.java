package TQS.hw1.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import TQS.hw1.model.Meal;
import TQS.hw1.repository.MealRepository;

@Component
public class MealSeeder implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MealSeeder.class);

    @SuppressWarnings("java:S2245") // SonarCloud: Random not secure (not needed for seed data)
    private static final Random random = new Random();

    private final MealRepository mealRepository;

    public MealSeeder(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Override
    public void run(String... args) {
        logger.info("Seeding meals...");

        List<String> meals = List.of(
            "Hambúrger no prato",
            "Bacalhau à Brás",
            "Francesinha",
            "Arroz com frango",
            "Costeletas de porco",
            "Bolonhesa de vitela",
            "Arroz de Pato",
            "Lasanha",
            "Empadão de carne",
            "Bife de frango");
        
        List<String> restaurants = List.of(
            "CantinaCastro",
            "CantinaSantiago",
            "CampiGrelhados",
            "CampiTresDe",
            "CantinaESTGA",
            "RestauranteUniversitario"
        );

        List<LocalDate> futureDates = IntStream.rangeClosed(1, 5).mapToObj(i ->
            LocalDate.now().plusDays(i))
            .toList();


        for (String restaurant: restaurants) {
            for (LocalDate date : futureDates) {
                if (!mealRepository.existsByRestaurantAndDate(restaurant, date)) {
                    String randomMeal = meals.get(random.nextInt(meals.size()));
                    mealRepository.save(new Meal(restaurant, date, randomMeal));
                    logger.info("Saved meal for {} on {}: {}", restaurant, date, randomMeal);
                }
            }
        }
        logger.info("Seeding completed");
    }
}
