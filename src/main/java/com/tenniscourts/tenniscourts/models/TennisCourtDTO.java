package com.tenniscourts.tenniscourts.models;

import com.tenniscourts.schedules.models.ScheduleDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TennisCourtDTO {

    private Long id;

    @NotNull
    private String name;

    private List<ScheduleDTO> tennisCourtSchedules;

}
