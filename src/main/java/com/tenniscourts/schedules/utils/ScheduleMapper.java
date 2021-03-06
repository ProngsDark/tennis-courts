package com.tenniscourts.schedules.utils;

import com.tenniscourts.schedules.models.CreateScheduleRequestDTO;
import com.tenniscourts.schedules.models.Schedule;
import com.tenniscourts.schedules.models.ScheduleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleMapper {

    Schedule map(ScheduleDTO source);

    ScheduleDTO map(Schedule source);

    List<ScheduleDTO> map(List<Schedule> source);

    @Mapping(target = "tennisCourt.id", source = "tennisCourtId")
    Schedule map(CreateScheduleRequestDTO source);
}
