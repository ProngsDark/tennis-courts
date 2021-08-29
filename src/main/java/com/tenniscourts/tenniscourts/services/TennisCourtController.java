package com.tenniscourts.tenniscourts.services;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.models.TennisCourtCreationRequestDTO;
import com.tenniscourts.tenniscourts.models.TennisCourtDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("tennis-courts")
public class TennisCourtController extends BaseRestController {

    @Autowired
    private final TennisCourtService tennisCourtService;

    @PostMapping
    public ResponseEntity<TennisCourtDTO> addTennisCourt(@RequestBody TennisCourtCreationRequestDTO requestDTO) {
        TennisCourtDTO tennisCourtDTO = tennisCourtService.addTennisCourt(requestDTO);

        return ResponseEntity.created(locationByEntity(tennisCourtDTO.getId())).body(tennisCourtDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(
            @PathVariable Long id,
            @RequestParam(value = "withSchedules", defaultValue = "false") Boolean withSchedules
    ) {
        try {
            TennisCourtDTO tennisCourtDTO;

            if (Boolean.FALSE.equals(withSchedules)) {
                tennisCourtDTO = tennisCourtService.findTennisCourtById(id);
            }
            else {
                tennisCourtDTO = tennisCourtService.findTennisCourtWithSchedulesById(id);
            }

            return ResponseEntity.ok(tennisCourtDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
