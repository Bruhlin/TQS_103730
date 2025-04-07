package TQS.hw1.service;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Service;

import TQS.hw1.model.Reservation;
import TQS.hw1.repository.ReservationRepository;

@Service
public class ReservationService {
    
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation createReservation(String restaurant, LocalDate date) {
        Reservation reservation = new Reservation();
        reservation.setRestaurant(restaurant);
        reservation.setDate(date);
        reservation.setUsed(false);
        reservation.setToken(UUID.randomUUID().toString().substring(0, 8));

        return reservationRepository.save(reservation);
    }

    public Reservation getReservationByToken(String token) {
        return reservationRepository.findByToken(token).orElse(null);
    }

    public boolean deleteReservation(String token) {
        return reservationRepository.findByToken(token).map(reservation -> {
            reservationRepository.delete(reservation);
            return true;
        }).orElse(false);
    }

    public boolean checkInReservation(String token) {
        return reservationRepository.findByToken(token).map(reservation -> {
            if (!reservation.isUsed()) {
                reservation.setUsed(true);
                reservationRepository.save(reservation);
                return true;
            }
            return false;
        }).orElse(false);
    }
}
