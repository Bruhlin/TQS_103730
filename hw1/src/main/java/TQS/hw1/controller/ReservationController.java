package TQS.hw1.controller;

import java.time.LocalDate;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TQS.hw1.model.Meal;
import TQS.hw1.model.Reservation;
import TQS.hw1.service.ReservationService;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    private static final String SANITIZE_REGEX = "[\n\r\t]";

    private static final String DEFAULT_VALUE = "unknown";
    
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody Map<String, String> body) {
        String restaurant = body.get("restaurant");
        LocalDate date = LocalDate.parse(body.get("date"));
        
        String sanitizedRestaurant = restaurant != null ? restaurant.replaceAll(SANITIZE_REGEX, "_") : DEFAULT_VALUE;

        logger.info("Creating reservation at {} in {}", sanitizedRestaurant, date);
        Reservation reservation = reservationService.createReservation(restaurant, date);
        
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/{token}")
    public ResponseEntity<Map<String, Object>> get(@PathVariable String token) {

        String safeToken = token != null ? token.replaceAll(SANITIZE_REGEX, "_") : DEFAULT_VALUE;

        logger.info("Getting reservation with token {}", safeToken);
        Reservation reservation = reservationService.getReservationByToken(token);

        if (reservation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("error", "Reservation not found")
            );
        }

        Meal meal = reservationService.getMealByReservation(reservation);

        String description = meal != null ? meal.getDescription() : "undefined";

        Map<String, Object> response = Map.of(
        "token", reservation.getToken(),
        "date", reservation.getDate(),
        "restaurant", reservation.getRestaurant(),
        "description", description,
        "used", reservation.isUsed()
    );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{token}")
    public ResponseEntity<Void> delete(@PathVariable String token) {

        String safeToken = token != null ? token.replaceAll(SANITIZE_REGEX, "_") : DEFAULT_VALUE;

        logger.info("Deleting reservation with token {}", safeToken);
        return reservationService.deleteReservation(token) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/{token}/checkin")
    public ResponseEntity<String> checkin(@PathVariable String token) {

        String safeToken = token != null ? token.replaceAll(SANITIZE_REGEX, "_") : DEFAULT_VALUE;

        logger.info("Checking in reservation with token {}", safeToken);
        boolean success = reservationService.checkInReservation(token);
        return success ? ResponseEntity.ok("Checked in successfully") : ResponseEntity.badRequest().body("Reservation already used or not found");
    }
}
