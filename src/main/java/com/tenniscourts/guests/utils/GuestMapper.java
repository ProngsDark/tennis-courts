package com.tenniscourts.guests.utils;

import com.tenniscourts.guests.models.Guest;
import com.tenniscourts.guests.models.GuestDTO;
import com.tenniscourts.guests.models.GuestRegistrationRequestDTO;
import com.tenniscourts.guests.models.GuestUpdateRequestDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GuestMapper {
    Guest map(GuestRegistrationRequestDTO source);

    Guest map(GuestUpdateRequestDTO source);

    Guest map(GuestDTO source);

    @InheritInverseConfiguration
    GuestDTO map(Guest source);
}
