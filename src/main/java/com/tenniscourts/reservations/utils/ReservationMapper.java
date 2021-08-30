package com.tenniscourts.reservations.utils;

import com.tenniscourts.reservations.models.CreateReservationRequestDTO;
import com.tenniscourts.reservations.models.Reservation;
import com.tenniscourts.reservations.models.ReservationDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationMapper {

    Reservation map(ReservationDTO source);

    @InheritInverseConfiguration
    ReservationDTO map(Reservation source);

    @Mapping(target = "guest.id", source = "guestId")
    @Mapping(target = "schedule.id", source = "scheduleId")
    Reservation map(CreateReservationRequestDTO source);

    Set<ReservationDTO> map(Set<Reservation> source);
}
