package com.tenniscourts.guests.services;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.core.networking.GenericResponseDTO;
import com.tenniscourts.guests.models.GuestDTO;
import com.tenniscourts.guests.models.GuestRegistrationRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("guests")
@AllArgsConstructor
public class GuestController extends BaseRestController {
    @Autowired
    private final GuestService guestService;

    @PostMapping
    public ResponseEntity<GenericResponseDTO<GuestDTO>> registerGuest(@RequestBody final GuestRegistrationRequestDTO requestDTO) {
        GuestDTO guestDTO = guestService.registerGuest(requestDTO);

        if (guestDTO == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponseDTO<>(null, "Could not register guest"));
        }

        return ResponseEntity.created(locationByEntity(guestDTO.getId())).body(new GenericResponseDTO<>(guestDTO, "Guest registered successfully"));
    }
}
