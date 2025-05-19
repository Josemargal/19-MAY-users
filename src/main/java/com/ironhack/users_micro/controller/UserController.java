package com.ironhack.users_micro.controller;

import com.ironhack.users_micro.dto.AccountDTO;
import com.ironhack.users_micro.dto.UserPatchAccountDTO;
import com.ironhack.users_micro.exception.UserNotFoundException;
import com.ironhack.users_micro.model.User;
import com.ironhack.users_micro.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final RestTemplate restTemplate;

    public UserController(UserService userService, RestTemplate restTemplate) {
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable long id) {
        try {
            User foundUser = userService.getUserById(id);
            return new ResponseEntity<>(foundUser, HttpStatus.FOUND);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/{id}/with-account")
    public ResponseEntity<?> getUserWithAccount(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);

            // Llama al servicio de cuentas
            String accountUrl = "http://localhost:8081/accounts/by-user/" + id;
            AccountDTO accountDTO = restTemplate.getForObject(accountUrl, AccountDTO.class);

            // Mapea a UserAccountDTO
            AccountDTO AccountDTO = new AccountDTO();
            AccountDTO.setName(user.getName());
            AccountDTO.setEmail(user.getEmail());
            if (accountDTO != null) {
                AccountDTO.setAccountNumber(accountDTO.getAccountNumber());
                AccountDTO.setBalance(accountDTO.getBalance());
            }

            return ResponseEntity.ok(AccountDTO);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching account info", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PatchMapping("/account/{userId}")
    public ResponseEntity<?> patchAccountId(@RequestBody UserPatchAccountDTO userPatchAccountDTO, @PathVariable("userId") Long userId) {
        try {
            User updatedUser = userService.patchAccountId(userId, userPatchAccountDTO.getAccountID());
            return new ResponseEntity<>(updatedUser, HttpStatus.ACCEPTED);
        } catch (UserNotFoundException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
