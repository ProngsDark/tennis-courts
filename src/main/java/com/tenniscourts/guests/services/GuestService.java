package com.tenniscourts.guests.services;

import com.tenniscourts.guests.models.Guest;
import com.tenniscourts.guests.models.GuestDTO;
import com.tenniscourts.guests.models.GuestRegistrationRequestDTO;
import com.tenniscourts.guests.models.GuestUpdateRequestDTO;
import com.tenniscourts.guests.utils.GuestMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Page<GuestDTO> getPageOfGuests(int page, int size, Sort.Direction direction, String name) {
        return this.getPage(page, size, direction, name).map(guestMapper::map);
    }

    public Optional<GuestDTO> findGuestById(long id) {
        return this.findById(id).map(guestMapper::map);
    }

    public Optional<GuestDTO> updateGuest(GuestUpdateRequestDTO requestDTO) {
        return this.update(requestDTO).map(guestMapper::map);
    }

    public Optional<GuestDTO> deleteGuest(long id) {
        return this.delete(id).map(guestMapper::map);
    }

    private Guest register(GuestRegistrationRequestDTO requestDTO) {
        return guestRepository.save(guestMapper.map(requestDTO));
    }

    private Page<Guest> getPage(int page, int size, Sort.Direction direction, String name) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "name"));

        if (name != null && !name.isEmpty()) {
            return guestRepository.findAllByName(name, pageable);
        }

        return guestRepository
                .findAll(pageable);
    }

    private Optional<Guest> findById(long id) {
        return guestRepository.findById(id);
    }

    private Optional<Guest> update(GuestUpdateRequestDTO requestDTO) {
        Optional<Guest> guest = guestRepository.findById(requestDTO.getId());

        if (!guest.isPresent()) {
            return guest;
        }

        return Optional.of(guestRepository.save(guestMapper.map(requestDTO)));
    }

    private Optional<Guest> delete(long id) {
        Optional<Guest> guest = guestRepository.findById(id);

        if (guest.isPresent()) {
            guestRepository.deleteById(id);
        }

        return guest;
    }
}
