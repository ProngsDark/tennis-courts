package com.tenniscourts.schedules.services;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.core.networking.GenericResponseDTO;
import com.tenniscourts.schedules.models.CreateScheduleRequestDTO;
import com.tenniscourts.schedules.models.ScheduleDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("schedules")
public class ScheduleController extends BaseRestController {

    @Autowired
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<GenericResponseDTO<ScheduleDTO>> addScheduleTennisCourt(@RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
        if (createScheduleRequestDTO.getStartDateTime().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body(new GenericResponseDTO<>(
                    null, "The schedule cannot start in the past"
            ));
        }

        ScheduleDTO scheduleDTO = scheduleService.addSchedule(createScheduleRequestDTO);

        return ResponseEntity.created(locationByEntity(scheduleDTO.getId())).body(new GenericResponseDTO<>(
                scheduleDTO, "Schedule added successfully"
        ));
    }

    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "false") Boolean isFree
    ) {
        return ResponseEntity.ok(scheduleService.findSchedulesByDates(
                LocalDateTime.of(startDate, LocalTime.of(0, 0)),
                LocalDateTime.of(endDate, LocalTime.of(23, 59)),
                isFree
        ));
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleDTO> findByScheduleId(@PathVariable Long scheduleId) {
        return scheduleService.findSchedule(scheduleId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
