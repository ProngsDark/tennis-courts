package com.tenniscourts.reservations.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CreateReservationRequestDTO {

    @NotNull
    private Long guestId;

    @NotNull
    private Long scheduleId;

}
