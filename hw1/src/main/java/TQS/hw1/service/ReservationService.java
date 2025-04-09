package TQS.hw1.service;

import java.time.LocalDate;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import TQS.hw1.model.Meal;
import TQS.hw1.model.Reservation;
import TQS.hw1.repository.MealRepository;
import TQS.hw1.repository.ReservationRepository;

@Service
public class ReservationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    private static final String SANITIZE_REGEX = "[\n\r\t]";

    private static final String DEFAULT_VALUE = "unknown";
    
    private final MealRepository mealRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository, MealRepository mealRepository) {
        this.reservationRepository = reservationRepository;
        this.mealRepository = mealRepository;
    }

    public Reservation createReservation(String restaurant, LocalDate date) {
        Reservation reservation = new Reservation();
        reservation.setRestaurant(restaurant);
        reservation.setDate(date);
        reservation.setUsed(false);
        reservation.setToken(UUID.randomUUID().toString().substring(0, 8));

        Reservation saved = reservationRepository.save(reservation);

        String sanitizedRestaurant = restaurant != null ? restaurant.replaceAll(SANITIZE_REGEX, "_") : DEFAULT_VALUE;
        logger.info("Reservation created: {} on {}", saved.getToken(), sanitizedRestaurant);
        return saved;
    }

    public Reservation getReservationByToken(String token) {
        String safeToken = token != null ? token.replaceAll(SANITIZE_REGEX, "_") : DEFAULT_VALUE;
        logger.info("Fetching reservation with token: {}", safeToken);
        return reservationRepository.findByToken(token).orElse(null);
    }

    public boolean deleteReservation(String token) {
        String safeToken = token != null ? token.replaceAll(SANITIZE_REGEX, "_") : DEFAULT_VALUE;
        logger.info("Deleting reservation with token: {}", safeToken);
        return reservationRepository.findByToken(token).map(reservation -> {
            reservationRepository.delete(reservation);
            logger.info("Reservation deleted: {}", safeToken);
            return true;
        }).orElseGet(() -> {
            logger.warn("Reservation not found: {}", safeToken);
            return false;
        });
    }

    public boolean checkInReservation(String token) {
        String safeToken = token != null ? token.replaceAll(SANITIZE_REGEX, "_") : DEFAULT_VALUE;
        logger.info("Checking in reservation with token: {}", safeToken);
        return reservationRepository.findByToken(token).map(reservation -> {
            if (!reservation.isUsed()) {
                reservation.setUsed(true);
                reservationRepository.save(reservation);
                logger.info("Checked in reservation: {}", safeToken);
                return true;
            } else {
                logger.warn("Reservation already used: {}", safeToken);
                return false;
            }
        }).orElse(false);
    }

    public Meal getMealByReservation(Reservation reservation) {
        logger.info("Fetching meal for restaurant {} on {}", reservation.getRestaurant(), reservation.getDate());
        return mealRepository.findByRestaurantAndDate(reservation.getRestaurant(), reservation.getDate()).orElse(null);
    }
}
