package com.zahid.expenseTracker.repository;

import com.zahid.expenseTracker.model.AuthenticationToken;
import com.zahid.expenseTracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationRepo extends JpaRepository<AuthenticationToken,Long> {


    AuthenticationToken findFirstByTokenValue(String authTokenValue);

    AuthenticationToken findFirstByUser(User user);
}
