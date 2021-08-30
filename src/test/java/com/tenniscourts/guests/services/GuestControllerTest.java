package com.tenniscourts.guests.services;

import com.tenniscourts.TennisCourtApplication;
import com.tenniscourts.guests.models.GuestDTO;
import com.tenniscourts.guests.models.GuestRegistrationRequestDTO;
import com.tenniscourts.guests.models.GuestUpdateRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TennisCourtApplication.class)
class GuestControllerTest {

    @Autowired
    private GuestController guestController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void registerGuest() {
        assertEquals(
                HttpStatus.CREATED,
                guestController.registerGuest(new GuestRegistrationRequestDTO("Alexandru Balan")).getStatusCode(),
                "New guests should always be created"
        );
    }

    @Test
    void getListOfGuests() {
        ResponseEntity<Page<GuestDTO>> responseEntity = guestController.getListOfGuests(0, 5, Sort.Direction.ASC, null);

        assertEquals(
                HttpStatus.OK,
                responseEntity.getStatusCode(),
                "Should retrieve the page of guests"
        );

        assertEquals(3, responseEntity.getBody().getTotalElements(), "Should have 3 elements");
    }

    @Test
    void getGuestById_shouldFind() {
        ResponseEntity<GuestDTO> responseEntity = guestController.getGuestById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Should find a guest");
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void getGuestById_shouldNotFind() {
        ResponseEntity<GuestDTO> responseEntity = guestController.getGuestById(100L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode(), "Should not find a guest");
        assertNull(responseEntity.getBody());
    }

    @Test
    void updateGuest() {
        ResponseEntity<GuestDTO> responseEntity = guestController.updateGuest(new GuestUpdateRequestDTO(1L, "Some other name"));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Should return the updated guest");
        assertNotNull(responseEntity.getBody());
        assertEquals("Some other name", responseEntity.getBody().getName());
    }

    @Test
    void deleteGuest_shouldDelete() {
        ResponseEntity<GuestDTO> responseEntity = guestController.getGuestById(2L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Should delete a guest");
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void deleteGuest_shouldNotDelete() {
        ResponseEntity<GuestDTO> responseEntity = guestController.getGuestById(200L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode(), "Should delete a guest");
        assertNull(responseEntity.getBody());
    }
}