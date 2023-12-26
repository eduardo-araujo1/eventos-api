package com.eduardo.eventosapi.repositories;

import com.eduardo.eventosapi.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepostirory extends JpaRepository <Event, Long> {
}
