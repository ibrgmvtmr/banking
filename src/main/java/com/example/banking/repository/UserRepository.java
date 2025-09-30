package com.example.banking.repository;

import com.example.banking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true,
            value = "SELECT CASE WHEN COUNT(1) > 0 THEN TRUE ELSE FALSE END " +
                    "FROM users u WHERE u.email = :email"
    )
    boolean existsByEmail(@Param("email") String email);

    @Query(nativeQuery = true,
            value = "SELECT * FROM users u WHERE u.email = :email LIMIT 1"
    )
    Optional<User> findByEmail(@Param("email") String email);
}
