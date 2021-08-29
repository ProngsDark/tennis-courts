package com.tenniscourts.schedules.services;

import com.tenniscourts.schedules.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("select s from Schedule s where s.tennisCourt.id = ?1 order by s.startDateTime")
    List<Schedule> findSchedulesByTennisCourt(Long id);
}