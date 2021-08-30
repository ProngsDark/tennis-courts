package com.tenniscourts.guests.services;

import com.tenniscourts.TennisCourtApplication;
import com.tenniscourts.guests.models.Guest;
import com.tenniscourts.guests.models.GuestDTO;
import com.tenniscourts.guests.models.GuestRegistrationRequestDTO;
import com.tenniscourts.guests.models.GuestUpdateRequestDTO;
import com.tenniscourts.guests.utils.GuestMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TennisCourtApplication.class)
class GuestServiceTest {
    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private GuestService guestService;

    private static final String TEST_GUEST_NAME = "Alexandru Balan";
    private static final String TEST_GUEST_NAME_2 = "Corina Balan";
    private final GuestRegistrationRequestDTO guestRegistrationRequestDTO = new GuestRegistrationRequestDTO();

    private GuestDTO configureMockGuest() {
        return GuestDTO.builder().name(TEST_GUEST_NAME)
                .reservations(null)
                .id(3L)
                .build();
    }

    private GuestUpdateRequestDTO configureMockUpdateRequest_correct() {
        return GuestUpdateRequestDTO.builder()
                .name(TEST_GUEST_NAME_2)
                .id(2L)
                .build();
    }

    private GuestUpdateRequestDTO configureMockUpdateRequest_wrong() {
        return GuestUpdateRequestDTO.builder()
                .name(TEST_GUEST_NAME_2)
                .id(100L)
                .build();
    }

    @BeforeEach
    void setup() {
        guestRegistrationRequestDTO.setName(TEST_GUEST_NAME);
    }

    @Test
    void registerGuest() {
        GuestDTO guestDTO = guestService.registerGuest(guestRegistrationRequestDTO);

        assertNotNull(guestDTO);
        assertEquals(configureMockGuest(), guestDTO, "The guests should match");
        assertNull(guestDTO.getReservations(), "There should be no reservations for new guests");
    }

    @Test
    void getPageOfGuests() {
        Page<GuestDTO> page = guestService.getPageOfGuests(0, 5, Sort.Direction.ASC, "");

        assertNotNull(page);
        assertEquals(2, page.getTotalElements(), "Page should have only 2 elements");
    }

    @Test
    void findGuestById_shouldFind() {
        Optional<GuestDTO> guestDTO = guestService.findGuestById(2);

        assertTrue(guestDTO.isPresent(), "Guest should be present");
    }

    @Test
    void findGuestById_shouldNotFind() {
        Optional<GuestDTO> guestDTO = guestService.findGuestById(100);

        assertFalse(guestDTO.isPresent(), "Guest should not be present");
    }

    @Test
    void updateGuest_shouldUpdate() {
        Optional<GuestDTO> guestDTO = guestService.updateGuest(configureMockUpdateRequest_correct());

        assertTrue(guestDTO.isPresent(), "Guest should be present");
        assertEquals(TEST_GUEST_NAME_2, guestDTO.get().getName(), "Name should have changed");
    }

    @Test
    void updateGuest_shouldNotFind() {
        Optional<GuestDTO> guestDTO = guestService.updateGuest(configureMockUpdateRequest_wrong());

        assertFalse(guestDTO.isPresent(), "Guest should not be present");
    }

    @Test
    void deleteGuest_shouldFind() {
        Optional<GuestDTO> guestDTO = guestService.deleteGuest(1);

        assertTrue(guestDTO.isPresent(), "Guest should be present");
    }

    @Test
    void deleteGuest_shouldNotFind() {
        Optional<GuestDTO> guestDTO = guestService.deleteGuest(100);

        assertFalse(guestDTO.isPresent(), "Guest should not be present");
    }
}