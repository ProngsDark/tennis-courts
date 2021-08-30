package com.tenniscourts.guests.models;

import com.tenniscourts.reservations.models.ReservationDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestDTO {
    private Long id;

    private String name;

    private Set<ReservationDTO> reservations;
}
