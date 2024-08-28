package com.cognixia.dao;

import com.cognixia.exception.UserAlreadyExistsException;
import com.cognixia.models.User;

public interface UserDao {
    public boolean registerUser(User user) throws UserAlreadyExistsException;
    public User loginUser(String username, String password);
}
