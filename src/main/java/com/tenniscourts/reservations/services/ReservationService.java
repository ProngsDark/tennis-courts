package com.tenniscourts.reservations.services;

import com.tenniscourts.config.properties.CourtAdminProperties;
import com.tenniscourts.exceptions.AlreadyExistsEntityException;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.models.Guest;
import com.tenniscourts.guests.services.GuestRepository;
import com.tenniscourts.reservations.utils.ReservationMapper;
import com.tenniscourts.reservations.utils.ReservationStatus;
import com.tenniscourts.reservations.models.CreateReservationRequestDTO;
import com.tenniscourts.reservations.models.Reservation;
import com.tenniscourts.reservations.models.ReservationDTO;
import com.tenniscourts.schedules.models.Schedule;
import com.tenniscourts.schedules.services.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReservationService {

    @Autowired
    private final ReservationRepository reservationRepository;

    @Autowired
    private final ScheduleRepository scheduleRepository;

    @Autowired
    private final GuestRepository guestRepository;

    @Autowired
    private final CourtAdminProperties courtAdminProperties;

    @Autowired
    private final ReservationMapper reservationMapper;

    /**
     * Reserves a free timeslot for a tennis match. This method checks that the guest and schedule do exist and
     * that the schedule is not already filled.
     * @param createReservationRequestDTO An object containing the schedule and guest ids
     * @return The new reservation object
     */
    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        Schedule schedule = findSchedule(createReservationRequestDTO.getScheduleId());

        if (Boolean.FALSE.equals(schedule.getOpen())
        ) {
            throw new AlreadyExistsEntityException("This timeslot is not available for reservations");
        }

        Reservation reservation = reservationMapper.map(createReservationRequestDTO);

        reservation.setSchedule(schedule);
        schedule.addReservation(reservation);
        schedule.setOpen(false);

        Guest guest = findGuest(createReservationRequestDTO.getGuestId());

        reservation.setGuest(guest);
        guest.addReservation(reservation);

        reservation.setValue(BigDecimal.valueOf(courtAdminProperties.getDeposit()));

        reservationRepository.save(reservation);
        scheduleRepository.save(schedule);
        guestRepository.save(guest);

        return reservationMapper.map(reservation);
    }

    public ReservationDTO findReservation(Long reservationId) throws EntityNotFoundException {
        return reservationRepository.findById(reservationId).map(reservationMapper::map).<EntityNotFoundException>orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    public ReservationDTO cancelReservation(Long reservationId) {
        return reservationMapper.map(this.cancel(reservationId));
    }

    private Schedule findSchedule(long scheduleId) throws EntityNotFoundException {
        return scheduleRepository.findById(scheduleId).<EntityNotFoundException>orElseThrow(() -> {
            throw new EntityNotFoundException("Schedule not found.");
        });
    }

    private Guest findGuest(long guestId) throws EntityNotFoundException {
        return guestRepository.findById(guestId).<EntityNotFoundException>orElseThrow(() -> {
            throw  new EntityNotFoundException("Guest not found");
        });
    }

    private Reservation cancel(Long reservationId) throws EntityNotFoundException {
        return reservationRepository.findById(reservationId).map(reservation -> {

            this.validateCancellation(reservation);

            BigDecimal refundValue = getRefundValue(reservation);
            return this.updateReservation(reservation, refundValue);

        }).<EntityNotFoundException>orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    private Reservation updateReservation(Reservation reservation, BigDecimal refundValue) {
        reservation.setReservationStatus(ReservationStatus.CANCELLED);
        reservation.setValue(reservation.getValue().subtract(refundValue));
        reservation.setRefundValue(refundValue);
        reservation.openSchedule();

        return reservationRepository.save(reservation);
    }

    private void validateCancellation(Reservation reservation) {
        if (!ReservationStatus.READY_TO_PLAY.equals(reservation.getReservationStatus())) {
            throw new IllegalArgumentException("Cannot cancel/reschedule because it's not in ready to play status.");
        }

        if (reservation.getSchedule().getStartDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Can cancel/reschedule only future dates.");
        }
    }

    public BigDecimal getRefundValue(Reservation reservation) {
        long hours = ChronoUnit.HOURS.between(LocalDateTime.now(), reservation.getSchedule().getStartDateTime());
        long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), reservation.getSchedule().getStartDateTime());

        if (hours >= 24) {
            return reservation.getValue();
        }

        if (hours >= 12) {
            return BigDecimal.valueOf(reservation.getValue().doubleValue() * 75D / 100D);
        }

        if (hours >= 2) {
            return BigDecimal.valueOf(reservation.getValue().doubleValue() * 50D / 100D);
        }

        if (minutes >= 1) {
            return BigDecimal.valueOf(reservation.getValue().doubleValue() * 25D / 100D);
        }

        return BigDecimal.ZERO;
    }

    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {
        Optional<Reservation> optionalPreviousReservation = reservationRepository.findById(previousReservationId);

        if (!optionalPreviousReservation.isPresent()) {
            throw new EntityNotFoundException("Cannot find this reservation");
        }

        Reservation previousReservation = optionalPreviousReservation.get();

        if (scheduleId.equals(previousReservation.getSchedule().getId())) {
            throw new IllegalArgumentException("Cannot reschedule to the same slot.");
        }

        previousReservation = this.cancel(previousReservationId);

        previousReservation.setReservationStatus(ReservationStatus.RESCHEDULED);
        reservationRepository.save(previousReservation);

        ReservationDTO newReservation = bookReservation(new CreateReservationRequestDTO(
                previousReservation.getGuest().getId(),
                scheduleId)
        );
        newReservation.setPreviousReservation(reservationMapper.map(previousReservation));

        return newReservation;
    }
}
