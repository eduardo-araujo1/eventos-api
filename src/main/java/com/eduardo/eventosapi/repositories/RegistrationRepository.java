package com.eduardo.eventosapi.repositories;

import com.eduardo.eventosapi.entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
}
