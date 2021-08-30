package com.tenniscourts.guests.models;

import com.tenniscourts.config.persistence.BaseEntity;
import com.tenniscourts.reservations.models.Reservation;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"reservations"}, callSuper = true)
public class Guest extends BaseEntity<Long> {
    @Column
    @NotNull
    private String name;

    @OneToMany(mappedBy = "guest", fetch = FetchType.EAGER)
    private Set<Reservation> reservations;

    public void addReservation(Reservation reservation) {
      if (this.reservations == null) {
        this.reservations = new HashSet<>();
      }

      this.reservations.add(reservation);
    }
}
