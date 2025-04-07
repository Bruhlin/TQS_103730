package TQS.hw1.controller;

import java.time.LocalDate;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import TQS.hw1.model.Reservation;
import TQS.hw1.service.ReservationService;

@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReservationService reservationService;

    @Test
    void testCreateReservation() throws Exception {
        Map<String, String> body = Map.of(
            "restaurant", "CantinaMoliceiro",
            "date", LocalDate.now().plusDays(1).toString()
        );

        mockMvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.restaurant").value("CantinaMoliceiro"))
            .andExpect(jsonPath("$.date").value(LocalDate.now().plusDays(1).toString()));
    }

    @Test
    void testGetReservationByToken() throws Exception {
    Reservation reservation = reservationService.createReservation("CantinaMoliceiro", LocalDate.now().plusDays(1));

    mockMvc.perform(get("/api/reservations/" + reservation.getToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.restaurant").value("CantinaMoliceiro"))
            .andExpect(jsonPath("$.token").value(reservation.getToken()));
    }

    @Test
    void testDeleteReservation() throws Exception {
        Reservation reservation = reservationService.createReservation("CantinaMoliceiro", LocalDate.now().plusDays(1));
    
        mockMvc.perform(delete("/api/reservations/" + reservation.getToken()))
                .andExpect(status().isNoContent());
    
        mockMvc.perform(get("/api/reservations/" + reservation.getToken()))
                .andExpect(status().isNotFound());   
    }

    @Test
    void testCheckInReservation() throws Exception {
        Reservation reservation = reservationService.createReservation("CantinaMoliceiro", LocalDate.now().plusDays(1));
    
        mockMvc.perform(post("/api/reservations/" + reservation.getToken() + "/checkin"))
                .andExpect(status().isOk())
                .andExpect(content().string("Checked in successfully"));
    
        mockMvc.perform(post("/api/reservations/" + reservation.getToken() + "/checkin"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Reservation already used or not found"));
    }
}
