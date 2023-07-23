package com.zahid.expenseTracker.repository;

import com.zahid.expenseTracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByUserEmail(String newEmail);
}

