package com.tenniscourts.schedules.services;

import com.tenniscourts.schedules.models.CreateScheduleRequestDTO;
import com.tenniscourts.schedules.models.ScheduleDTO;
import com.tenniscourts.schedules.services.ScheduleRepository;
import com.tenniscourts.schedules.utils.ScheduleMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

    @Autowired
    private final ScheduleRepository scheduleRepository;

    @Autowired
    private final ScheduleMapper scheduleMapper;

    public ScheduleDTO addSchedule(CreateScheduleRequestDTO createScheduleRequestDTO) {
        return scheduleMapper.map(scheduleRepository.save(scheduleMapper.map(createScheduleRequestDTO)));
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        //TODO: implement
        return null;
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        //TODO: implement
        return null;
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findSchedulesByTennisCourt(tennisCourtId));
    }
}
