package com.tenniscourts.reservations.services;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.reservations.models.CreateReservationRequestDTO;
import com.tenniscourts.reservations.models.ReservationDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("reservations")
public class ReservationController extends BaseRestController {

    @Autowired
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationDTO> bookReservation(@RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        ReservationDTO reservationDTO = reservationService.bookReservation(createReservationRequestDTO);

        return ResponseEntity.created(locationByEntity(reservationDTO.getId())).body(reservationDTO);
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @PutMapping("/{reservationId}/schedules/{scheduleId}")
    public ResponseEntity<ReservationDTO> rescheduleReservation(
            @PathVariable Long reservationId,
            @PathVariable Long scheduleId
    ) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}
