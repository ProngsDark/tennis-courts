package com.tenniscourts.tenniscourts.services;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.schedules.services.ScheduleService;
import com.tenniscourts.tenniscourts.models.TennisCourtCreationRequestDTO;
import com.tenniscourts.tenniscourts.models.TennisCourtDTO;
import com.tenniscourts.tenniscourts.utils.TennisCourtMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TennisCourtService {
    @Autowired
    private final TennisCourtRepository tennisCourtRepository;

    @Autowired
    private final ScheduleService scheduleService;

    @Autowired
    private final TennisCourtMapper tennisCourtMapper;

    public TennisCourtDTO addTennisCourt(TennisCourtCreationRequestDTO requestDTO) {
        return tennisCourtMapper.map(tennisCourtRepository.saveAndFlush(tennisCourtMapper.map(requestDTO)));
    }

    public TennisCourtDTO findTennisCourtById(Long id) throws EntityNotFoundException {
        return tennisCourtRepository.findById(id).map(tennisCourtMapper::map).<EntityNotFoundException>orElseThrow(() -> {
            throw new EntityNotFoundException("Tennis Court not found.");
        });
    }

    public TennisCourtDTO findTennisCourtWithSchedulesById(Long tennisCourtId) throws EntityNotFoundException {
        TennisCourtDTO tennisCourtDTO = findTennisCourtById(tennisCourtId);
        tennisCourtDTO.setTennisCourtSchedules(scheduleService.findSchedulesByTennisCourtId(tennisCourtId));
        return tennisCourtDTO;
    }
}
