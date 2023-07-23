package com.zahid.expenseTracker.service;

import com.zahid.expenseTracker.controller.dto.SignInInput;
import com.zahid.expenseTracker.controller.dto.SignUpOutput;
import com.zahid.expenseTracker.model.AuthenticationToken;
import com.zahid.expenseTracker.model.User;
import com.zahid.expenseTracker.repository.UserRepository;
import com.zahid.expenseTracker.service.emailUtility.EmailHandler;
import com.zahid.expenseTracker.service.hashingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationService authenticationService;

    public SignUpOutput signUpUser(User user) {

        boolean signUpStatus = false;
        String signUpStatusMessage = null;

        String newEmail = user.getUserEmail();

        if(newEmail == null)
        {
            signUpStatusMessage = "Invalid email";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //check if this user email already exists ??
        User existingUser = userRepository.findFirstByUserEmail(newEmail);

        if(existingUser != null)
        {
            signUpStatusMessage = "Email already registered!!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(user.getPassword());

            //saveAppointment the user with the new encrypted password

            user.setPassword(encryptedPassword);
            userRepository.save(user);

            return new SignUpOutput(signUpStatus, "User registered successfully!!!");
        }
        catch(Exception e)
        {
            signUpStatusMessage = "Internal error occurred during sign up";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }
    }
//        public String signInUser(SignInInput signInInput) throws NoSuchAlgorithmException {
//            String signInStatusMessage = null;
//            String signInEmail = signInInput.getEmail();
//
//            if (signInEmail == null) {
//                signInStatusMessage = "Invalid email";
//                return signInStatusMessage;
//            }
//
//            // Check if this user email already exists
//            User existingUser = userRepository.findFirstByUserEmail(signInEmail);
//
//            if (existingUser == null) {
//                // If the user does not exist, create and save the new user
//                signInInput.setPassword(PasswordEncrypter.encryptPassword(signInInput.getPassword()));
//                userRepository.save(user);
//
//                // Session should be created since password matched and user id is valid
//                AuthenticationToken authToken = new AuthenticationToken(signInInput);
//                authenticationService.saveAuthToken(authToken);
//
//                EmailHandler.sendEmail("raahizaahid@gmail.com", "email testing", authToken.getTokenValue());
//                return "Token sent to your email";
//            } else {
//                // If the user exists, match passwords
//                try {
//                    String encryptedPassword = PasswordEncrypter.encryptPassword(user.getPassword());
//                    if (existingUser.getPassword().equals(encryptedPassword)) {
//                        // Session should be created since password matched and user id is valid
//                        AuthenticationToken authToken = new AuthenticationToken(existingUser);
//                        authenticationService.saveAuthToken(authToken);
//
//                        EmailHandler.sendEmail("raahizaahid@gmail.com", "email testing", authToken.getTokenValue());
//                        return "Token sent to your email";
//                    } else {
//                        signInStatusMessage = "Invalid credentials!!!";
//                        return signInStatusMessage;
//                    }
//                } catch (Exception e) {
//                    signInStatusMessage = "Internal error occurred during sign in";
//                    return signInStatusMessage;
//                }
//            }
//        }

    public String signInUser(SignInInput signInInput) {


        String signInStatusMessage = null;

        String signInEmail = signInInput.getEmail();

        if(signInEmail == null)
        {
            signInStatusMessage = "Invalid email";
            return signInStatusMessage;


        }

        //check if this user email already exists ??
        User existingUser = userRepository.findFirstByUserEmail(signInEmail);

        if(existingUser == null)
        {
            signInStatusMessage = "Email not registered!!!";
            return signInStatusMessage;

        }

        //match passwords :

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
            if(existingUser.getPassword().equals(encryptedPassword))
            {
                //session should be created since password matched and user id is valid
                AuthenticationToken authToken  = new AuthenticationToken(existingUser);
                authenticationService.saveAuthToken(authToken);

                EmailHandler.sendEmail("raahizaahid@gmail.com","email testing",authToken.getTokenValue());
                return "Token sent to your email";
            }
            else {
                signInStatusMessage = "Invalid credentials!!!";
                return signInStatusMessage;
            }
        }
        catch(Exception e)
        {
            signInStatusMessage = "Internal error occurred during sign in";
            return signInStatusMessage;
        }

    }



    public String sigOutUser(String email) {

        User user = userRepository.findFirstByUserEmail(email);
        AuthenticationToken token = authenticationService.findFirstByUser(user);
        authenticationService.removeToken(token);
        return "User Signed out successfully";
    }
    public User saveUser(User user, String email) {
        User postUser = userRepository.findFirstByUserEmail(email);
        return userRepository.save(postUser);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

//    public Expense addExpenseToUser(Long userId, Expense expense) {
//        Optional<User> userOptional = userRepository.findById(userId);
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            expense.setUser(user);
//            user.setExpense(expense);
//            userRepository.save(user);
//            return expense;
//        } else {
//            throw new IllegalArgumentException("User with ID " + userId + " not found.");
//        }
//    }
//
//    public Expense getExpenseByUserId(Long userId) {
//        Optional<User> userOptional = userRepository.findById(userId);
//        return userOptional.map(User::getExpense).orElse(null);
//    }
//
//    public void deleteExpenseByUserId(Long userId) {
//        Optional<User> userOptional = userRepository.findById(userId);
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            user.setExpense(null);
//            userRepository.save(user);
//        } else {
//            throw new IllegalArgumentException("User with ID " + userId + " not found.");
//        }
//    }
}

