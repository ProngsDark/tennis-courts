package com.tenniscourts.reservations.services;

import com.tenniscourts.TennisCourtApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TennisCourtApplication.class)
class ReservationControllerTest {
    @Autowired
    private ReservationController reservationController;

    @Test
    void bookReservation() {
    }

    @Test
    void findReservation() {
    }

    @Test
    void cancelReservation() {
    }

    @Test
    void rescheduleReservation() {
    }
}