package com.eduardo.eventosapi.repositories;

import com.eduardo.eventosapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Long> {
}
