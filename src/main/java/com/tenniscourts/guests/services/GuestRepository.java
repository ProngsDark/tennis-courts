package com.tenniscourts.guests.services;

import com.tenniscourts.guests.models.Guest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GuestRepository extends JpaRepository<Guest, Long> {
    Page<Guest> findAllByName(String name, Pageable pageable);
}
