package com.tenniscourts.tenniscourts.services;

import com.tenniscourts.tenniscourts.models.TennisCourt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TennisCourtRepository extends JpaRepository<TennisCourt, Long> {
}
