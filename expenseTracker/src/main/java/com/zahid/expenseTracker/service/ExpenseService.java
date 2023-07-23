package com.zahid.expenseTracker.service;

import com.zahid.expenseTracker.model.Expense;
import com.zahid.expenseTracker.model.User;
import com.zahid.expenseTracker.repository.ExpenseRepository;
import com.zahid.expenseTracker.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

@Service
public class ExpenseService {
    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    UserRepository userRepository;

    public Expense saveExpense(Expense expense, String email) {
        User authenticatedUser = userRepository.findFirstByUserEmail(email);

        if (authenticatedUser != null) {
            // Associate the authenticated user with the expense
            expense.setUser(authenticatedUser);
            authenticatedUser.getExpenseList().add(expense);
            userRepository.save(authenticatedUser);
            return expenseRepository.save(expense);
        } else {
            // Handle the case when the authenticated user is not found
            // For example, you can throw an exception or return null
            return null;
        }
    }

//    public List<Expense> getAllExpensesForDate(Long userId, LocalDate date, LocalTime time) {
//        if (time != null) {
//            return expenseRepository.findAllByUserIdAndDateAndTime(userId, date, time);
//        } else {
//            return expenseRepository.findAllByUserIdAndDate(userId, date);
//        }
//    }
//
//    public Expense getExpenseById(Long id) {
//        return expenseRepository.findById(id).orElse(null);
//    }
//
//    public void deleteExpense(Long id) {
//        expenseRepository.deleteById(id);
//    }
//
//    public List<Expense> getAllExpensesForMonth(Long userId, YearMonth month) {
//        LocalDate startOfMonth = month.atDay(1);
//        LocalDate endOfMonth = month.atEndOfMonth();
//        return expenseRepository.findAllByUserIdAndDateBetween(userId, startOfMonth, endOfMonth);
//    }
//
//    public double getTotalExpenditureForMonth(Long userId, YearMonth month) {
//        List<Expense> expenses = getAllExpensesForMonth(userId, month);
//        return expenses.stream().mapToDouble(Expense::getPrice).sum();
//    }
}

