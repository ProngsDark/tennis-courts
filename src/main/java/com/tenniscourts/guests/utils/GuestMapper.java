package com.tenniscourts.guests.utils;

import com.tenniscourts.guests.models.Guest;
import com.tenniscourts.guests.models.GuestDTO;
import com.tenniscourts.guests.models.GuestRegistrationRequestDTO;
import com.tenniscourts.guests.models.GuestUpdateRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GuestMapper {
    Guest map(GuestRegistrationRequestDTO source);

    Guest map(GuestUpdateRequestDTO source);

    Guest map(GuestDTO source);

    GuestDTO map(Guest source);
}
