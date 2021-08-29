package com.tenniscourts.schedules.services;

import com.tenniscourts.exceptions.AlreadyExistsEntityException;
import com.tenniscourts.schedules.models.CreateScheduleRequestDTO;
import com.tenniscourts.schedules.models.Schedule;
import com.tenniscourts.schedules.models.ScheduleDTO;
import com.tenniscourts.schedules.utils.ScheduleMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ScheduleService {

    @Autowired
    private final ScheduleRepository scheduleRepository;

    @Autowired
    private final ScheduleMapper scheduleMapper;

    public ScheduleDTO addSchedule(CreateScheduleRequestDTO createScheduleRequestDTO) {
        boolean scheduleExists = scheduleRepository
                .findSchedulesByTennisCourt(createScheduleRequestDTO.getTennisCourtId())
                .stream()
                .anyMatch(schedule -> schedule.getStartDateTime().equals(createScheduleRequestDTO.getStartDateTime()));

        if (scheduleExists) {
            throw new AlreadyExistsEntityException("Schedule already exists for this start time");
        }

        Schedule schedule = scheduleMapper.map(createScheduleRequestDTO);
        schedule.setEndDateTime(schedule.getStartDateTime().plusHours(1));

        return scheduleMapper.map(scheduleRepository.save(schedule));
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate, boolean isFree) {
        List<Schedule> schedules;

        if (isFree) {
            schedules = scheduleRepository.findFreeSchedulesByDates(startDate, endDate);
        }
        else {
            schedules = scheduleRepository.findSchedulesByDates(startDate, endDate);
        }

        return schedules.stream()
                .map(scheduleMapper::map)
                .collect(Collectors.toList());
    }

    public Optional<ScheduleDTO> findSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).map(scheduleMapper::map);
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findSchedulesByTennisCourt(tennisCourtId));
    }
}
