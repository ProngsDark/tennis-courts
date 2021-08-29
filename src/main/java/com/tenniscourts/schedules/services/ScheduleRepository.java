package com.tenniscourts.schedules.services;

import com.tenniscourts.schedules.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("select s from Schedule s where s.tennisCourt.id = ?1 order by s.startDateTime")
    List<Schedule> findSchedulesByTennisCourt(Long id);

    @Query("select s from Schedule s where s.startDateTime >= ?1 and s.endDateTime <= ?2")
    List<Schedule> findSchedulesByDates(LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Query("select s from Schedule s where s.startDateTime >= ?1 and s.endDateTime <= ?2 and s.open is true")
    List<Schedule> findFreeSchedulesByDates(LocalDateTime startDateTime, LocalDateTime endDateTime);
}