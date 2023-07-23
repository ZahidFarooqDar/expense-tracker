package com.zahid.expenseTracker.repository;

import com.zahid.expenseTracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByUserIdAndDateAndTime(Long userId, LocalDate date, LocalTime time);
    List<Expense> findAllByUserIdAndDate(Long userId, LocalDate date);

    List<Expense> findAllByUserIdAndDateBetween(Long userId, LocalDate startOfMonth, LocalDate endOfMonth);
}

