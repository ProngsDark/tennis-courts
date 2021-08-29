package com.tenniscourts.guests.models;

import com.tenniscourts.reservations.models.ReservationDTO;
import lombok.Data;

import java.util.Set;

@Data
public class GuestDTO {
    private Long id;

    private String name;

    private Set<ReservationDTO> reservations;
}
