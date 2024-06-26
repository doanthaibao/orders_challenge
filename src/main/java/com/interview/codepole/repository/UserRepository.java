package com.interview.codepole.repository;

import com.interview.codepole.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{

    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<User> findUserById(UUID id);
}
