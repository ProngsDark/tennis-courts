package com.tenniscourts.guests.services;

import com.tenniscourts.guests.models.Guest;
import com.tenniscourts.guests.models.GuestDTO;
import com.tenniscourts.guests.models.GuestRegistrationRequestDTO;
import com.tenniscourts.guests.utils.GuestMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GuestService {
    @Autowired
    private final GuestRepository guestRepository;

    @Autowired
    private final GuestMapper guestMapper;

    public GuestDTO registerGuest(GuestRegistrationRequestDTO requestDTO) {
        return guestMapper.map(this.register(requestDTO));
    }

    private Guest register(GuestRegistrationRequestDTO requestDTO) {
        return guestRepository.save(guestMapper.map(requestDTO));
    }
}
