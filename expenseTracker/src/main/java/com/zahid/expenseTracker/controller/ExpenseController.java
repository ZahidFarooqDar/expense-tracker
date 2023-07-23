package com.zahid.expenseTracker.controller;

import com.zahid.expenseTracker.model.Expense;
import com.zahid.expenseTracker.service.AuthenticationService;
import com.zahid.expenseTracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    @Autowired
    ExpenseService expenseService;
    @Autowired
    AuthenticationService authenticationService;
    @PostMapping
    public String addExpense(@RequestBody Expense expense, @RequestParam String email, @RequestParam String token) {
        if (authenticationService.authenticate(email, token)) {
            Expense savedExpense = expenseService.saveExpense(expense,email);
            return "Added Successfully!!!";

            //return ResponseEntity.status(HttpStatus.CREATED).body(savedExpense.getUser().getExpense());
        } else {
            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            return "Unauthorised User!!!";
        }
    }

//    @PostMapping
//    public ResponseEntity<Expense> addExpense(@Valid @RequestBody Expense expense) {
//        Expense savedExpense = expenseService.saveExpense(expense);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedExpense);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Expense>> getExpensesForDate(
//            @RequestParam Long userId,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time) {
//        List<Expense> expenses = expenseService.getAllExpensesForDate(userId, date, time);
//        return ResponseEntity.ok(expenses);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
//        Expense expense = expenseService.getExpenseById(id);
//        if (expense != null) {
//            return ResponseEntity.ok(expense);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
//        expenseService.deleteExpense(id);
//        return ResponseEntity.noContent().build();
//    }

}

