package com.eduardo.eventosapi.repositories;

import com.eduardo.eventosapi.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository <Event, Long> {

    Page<Event> findAll(Pageable pageable);
}
