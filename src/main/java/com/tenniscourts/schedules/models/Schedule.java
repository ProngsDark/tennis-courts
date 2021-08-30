package com.tenniscourts.schedules.models;

import com.tenniscourts.config.persistence.BaseEntity;
import com.tenniscourts.reservations.models.Reservation;
import com.tenniscourts.tenniscourts.models.TennisCourt;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"reservations"}, callSuper = true)
public class Schedule extends BaseEntity<Long> {

    @ManyToOne
    @NotNull
    private TennisCourt tennisCourt;

    @NotNull
    private LocalDateTime startDateTime;

    @NotNull
    private LocalDateTime endDateTime;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.EAGER)
    private Set<Reservation> reservations;

    private Boolean open;

    public void addReservation(Reservation reservation) {
        if (this.reservations == null) {
            this.reservations = new HashSet<>();
        }

        reservation.setSchedule(this);
        this.reservations.add(reservation);
    }
}
