package com.tenniscourts.reservations.services;

import com.tenniscourts.TennisCourtApplication;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.reservations.models.CreateReservationRequestDTO;
import com.tenniscourts.reservations.models.Reservation;
import com.tenniscourts.reservations.models.ReservationDTO;
import com.tenniscourts.reservations.utils.ReservationStatus;
import com.tenniscourts.schedules.models.Schedule;
import com.tenniscourts.schedules.services.ScheduleRepository;
import com.tenniscourts.tenniscourts.services.TennisCourtRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TennisCourtApplication.class)
class ReservationServiceTest {
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private TennisCourtRepository tennisCourtRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setup() {
        LocalDateTime startTime = LocalDateTime.now().plusHours(2);

        scheduleRepository.save(Schedule.builder()
                .startDateTime(startTime)
                .endDateTime(startTime.plusHours(1))
                .tennisCourt(tennisCourtRepository.findById(1L).get()
                )
                .build()
        );

        scheduleRepository.save(Schedule.builder()
                .startDateTime(startTime.plusHours(4))
                .endDateTime(startTime.plusHours(5))
                .tennisCourt(tennisCourtRepository.findById(1L).get()
                )
                .build()
        );

        reservationRepository.save(Reservation.builder()
                .schedule(scheduleRepository.getOne(2L))
                .reservationStatus(ReservationStatus.READY_TO_PLAY)
                .value(BigDecimal.TEN)
                .build()
        );
    }

    @Test
    void bookReservation() {
        ReservationDTO reservationDTO = reservationService.bookReservation(
                new CreateReservationRequestDTO(1L, 1L)
        );

        assertNotNull(reservationDTO);
    }

    @Test
    void findReservation_shouldFind() {
        ReservationDTO reservationDTO = reservationService.findReservation(1L);

        assertNotNull(reservationDTO);
    }

    @Test
    void findReservation_shouldNotFind() {
        assertThrows(EntityNotFoundException.class, () -> reservationService.findReservation(100L));
    }

    @Test
    void cancelReservation_shouldCancel() {
        ReservationDTO reservationDTO = reservationService.cancelReservation(2L);

        assertNotNull(reservationDTO);
    }

    @Test
    void getRefundValue() {
    }

    @Test
    void rescheduleReservation() {
    }
}