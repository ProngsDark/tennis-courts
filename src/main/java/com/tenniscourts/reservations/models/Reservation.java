package com.tenniscourts.reservations.models;

import com.tenniscourts.config.persistence.BaseEntity;
import com.tenniscourts.guests.models.Guest;
import com.tenniscourts.reservations.utils.ReservationStatus;
import com.tenniscourts.schedules.models.Schedule;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"guest", "schedule"}, callSuper = true)
public class Reservation extends BaseEntity<Long> {

    @ManyToOne
    private Guest guest;

    @ManyToOne
    @NotNull
    private Schedule schedule;

    @NotNull
    private BigDecimal value;

    @NotNull
    @Builder.Default
    private ReservationStatus reservationStatus = ReservationStatus.READY_TO_PLAY;

    @Builder.Default
    private BigDecimal refundValue = BigDecimal.ZERO;

    public void openSchedule() {
        this.schedule.setOpen(true);
    }
}
