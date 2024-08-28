package com.cognixia.dao;

import com.cognixia.models.User;

public interface UserDao {
    public boolean registerUser(User user);
    public User loginUser(String username, String password);
}
