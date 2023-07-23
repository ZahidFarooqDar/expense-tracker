package com.zahid.expenseTracker.controller;

import com.zahid.expenseTracker.controller.dto.SignInInput;
import com.zahid.expenseTracker.controller.dto.SignUpOutput;
import com.zahid.expenseTracker.model.Expense;
import com.zahid.expenseTracker.model.User;
import com.zahid.expenseTracker.service.AuthenticationService;
import com.zahid.expenseTracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("user/signUp")
    public SignUpOutput signUpUser(@RequestBody User user)
    {

        return userService.signUpUser(user);
    }

    @PostMapping("user/signIn")
    public String sigInUser(@RequestBody SignInInput signInInput ){
        return userService.signInUser(signInInput);
    }

    @DeleteMapping("user/signOut")
    public String sigOutUser(String email, String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.sigOutUser(email);
        }
        else {
            return "Sign out not allowed for non authenticated user.";
        }

    }

//    @PostMapping
//    public ResponseEntity<User> addUser(@RequestBody User user, @RequestParam String email, @RequestParam String token) {
//        if (authenticationService.authenticate(email, token)) {
//            User savedUser = userService.saveUser(user, email);
//            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        }
//    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping("/{userId}/expense")
//    public ResponseEntity<Expense> addExpenseToUser(@PathVariable Long userId, @RequestBody Expense expense) {
//        Expense savedExpense = userService.addExpenseToUser(userId, expense);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedExpense);
//    }
//
//    @GetMapping("/{userId}/expense")
//    public ResponseEntity<Expense> getExpenseByUserId(@PathVariable Long userId) {
//        Expense expense = userService.getExpenseByUserId(userId);
//        if (expense != null) {
//            return ResponseEntity.ok(expense);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/{userId}/expense")
//    public ResponseEntity<Void> deleteExpenseByUserId(@PathVariable Long userId) {
//        userService.deleteExpenseByUserId(userId);
//        return ResponseEntity.noContent().build();
//    }
  }

