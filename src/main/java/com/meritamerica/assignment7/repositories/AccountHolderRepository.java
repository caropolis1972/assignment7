package com.meritamerica.assignment7.repositories;

import com.meritamerica.assignment7.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.meritamerica.assignment7.models.AccountHolder;

import java.util.Optional;

public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {
    Optional<AccountHolder> findByUserId(int userId);
}
