package com.tenniscourts.reservations;

import com.tenniscourts.config.persistence.BaseEntity;
import com.tenniscourts.guests.models.Guest;
import com.tenniscourts.schedules.models.Schedule;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation extends BaseEntity<Long> {

    @OneToOne(mappedBy = "reservation")
    private Guest guest;

    @ManyToOne
    @NotNull
    private Schedule schedule;

    @NotNull
    private BigDecimal value;

    @NotNull
    private ReservationStatus reservationStatus = ReservationStatus.READY_TO_PLAY;

    private BigDecimal refundValue;
}
