package TQS.hw1.controller;

import java.time.LocalDate;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody Map<String, String> body) {
        String restaurant = body.get("restaurant");
        LocalDate date = LocalDate.parse(body.get("date"));
        
        logger.info("Creating reservation at {} in {}", restaurant, date);
        Reservation reservation = reservationService.createReservation(restaurant, date);
        
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/{token}")
    public ResponseEntity<Map<String, Object>> get(@PathVariable String token) {
        logger.info("Getting reservation with token {}", token);
        Reservation reservation = reservationService.getReservationByToken(token);

        if (reservation == null) {
            return ResponseEntity.notFound().build();
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
        logger.info("Deleting reservation with token {}", token);
        return reservationService.deleteReservation(token) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/{token}/checkin")
    public ResponseEntity<String> checkin(@PathVariable String token) {
        logger.info("Checking in reservation with token {}", token);
        boolean success = reservationService.checkInReservation(token);
        return success ? ResponseEntity.ok("Checked in successfully") : ResponseEntity.badRequest().body("Reservation already used or not found");
    }
}
