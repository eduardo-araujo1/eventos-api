package com.eduardo.eventosapi.repositories;

import com.eduardo.eventosapi.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
