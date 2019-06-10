package com.claudiuorosanu.Wumie.service;

import com.claudiuorosanu.Wumie.exception.AppException;
import com.claudiuorosanu.Wumie.exception.ResourceNotFoundException;
import com.claudiuorosanu.Wumie.model.User;
import com.claudiuorosanu.Wumie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", id.toString()));
    }
}
