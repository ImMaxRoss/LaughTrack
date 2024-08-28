package com.cognixia.service;

import com.cognixia.dao.*;
import com.cognixia.daoClass.*;
import com.cognixia.models.*;


import java.util.Scanner;

public class UserService {

    private UserDao userDao;

    public UserService() {
        this.userDao = new UserDaoClass();
    }

    public void registerUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.next();

        System.out.print("Enter password: ");
        String password = scanner.next();

        User user = new User(0, username, password);
        boolean isRegistered = userDao.registerUser(user);

        if (isRegistered) {
            System.out.println("User registered successfully!");
        } else {
            System.out.println("Failed to register user.");
        }
    }

    public User loginUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.next();

        System.out.print("Enter password: ");
        String password = scanner.next();

        User user = userDao.loginUser(username, password);
        if (user != null) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid username or password.");
        }

        return user;
    }
}