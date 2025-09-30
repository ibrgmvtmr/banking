package com.example.banking.repository;

import com.example.banking.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;


import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(nativeQuery = true,
            value = "SELECT CASE WHEN COUNT(1) > 0 THEN TRUE ELSE FALSE END " +
                    "FROM accounts a WHERE a.number = :number"
    )
    boolean existsByNumber(@Param("number") String number);

    @Query(nativeQuery = true,
            value = "SELECT * FROM accounts a WHERE a.number = :number LIMIT 1"
    )
    Optional<Account> findByNumber(@Param("number") String number);

    @Query(nativeQuery = true,
            value = "SELECT * FROM accounts a WHERE a.user_id = :userId ORDER BY a.id"
    )
    Page<Account> findByUserId(@Param("userId") Long userId, Pageable pageable);
}
