package com.tenniscourts.guests.services;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.config.properties.PaginationProperties;
import com.tenniscourts.core.networking.GenericResponseDTO;
import com.tenniscourts.guests.models.Guest;
import com.tenniscourts.guests.models.GuestDTO;
import com.tenniscourts.guests.models.GuestRegistrationRequestDTO;
import com.tenniscourts.guests.models.GuestUpdateRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("guests")
@AllArgsConstructor
public class GuestController extends BaseRestController {
    @Autowired
    private final GuestService guestService;

    @Autowired
    private final PaginationProperties paginationProperties;

    @PostMapping
    public ResponseEntity<GenericResponseDTO<GuestDTO>> registerGuest(@RequestBody final GuestRegistrationRequestDTO requestDTO) {
        GuestDTO guestDTO = guestService.registerGuest(requestDTO);

        if (guestDTO == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponseDTO<>(null, "Could not register guest"));
        }

        return ResponseEntity.created(locationByEntity(guestDTO.getId())).body(new GenericResponseDTO<>(guestDTO, "Guest registered successfully"));
    }

    @GetMapping
    public ResponseEntity<Page<GuestDTO>> getListOfGuests(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "direction", required = false) Sort.Direction direction,
            @RequestParam(value = "name", required = false) String name
    ) {
        if (page == null) {
            page = paginationProperties.getPage();
        }

        if (size == null) {
            size = paginationProperties.getSize();
        }

        if (direction == null) {
            direction = paginationProperties.getDirection();
        }

        return ResponseEntity.ok(guestService.getPageOfGuests(page, size, direction, name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuestDTO> getGuestById(@PathVariable Long id) {
        Optional<GuestDTO> guestDTO = guestService.findGuestById(id);

        return guestDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<GuestDTO> updateGuest(@RequestBody GuestUpdateRequestDTO requestDTO) {
        Optional<GuestDTO> guestDTO = guestService.updateGuest(requestDTO);

        return guestDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GuestDTO> deleteGuest(@PathVariable Long id) {
        Optional<GuestDTO> guestDTO =  guestService.deleteGuest(id);

        return guestDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
