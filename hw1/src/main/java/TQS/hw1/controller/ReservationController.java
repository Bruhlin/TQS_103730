package TQS.hw1.controller;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TQS.hw1.model.Reservation;
import TQS.hw1.service.ReservationService;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody Map<String, String> body) {
        String restaurant = body.get("restaurant");
        LocalDate date = LocalDate.parse(body.get("date"));
        
        Reservation reservation = reservationService.createReservation(restaurant, date);
        
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/{token}")
    public ResponseEntity<Reservation> get(@PathVariable String token) {
        Reservation reservation = reservationService.getReservationByToken(token);
        return reservation != null ? ResponseEntity.ok(reservation) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{token}")
    public ResponseEntity<Void> delete(@PathVariable String token) {
        return reservationService.deleteReservation(token) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/{token}/checkin")
    public ResponseEntity<String> checkin(@PathVariable String token) {
        boolean success = reservationService.checkInReservation(token);
        return success ? ResponseEntity.ok("Checked in successfully") : ResponseEntity.badRequest().body("Reservation already used or not found");
    }
}
