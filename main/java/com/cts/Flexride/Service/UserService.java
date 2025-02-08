
package com.cts.Flexride.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cts.Flexride.Entity.UserEntity;
import com.cts.Flexride.Repo.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    // Save User (Signup)
    public UserEntity saveUser(UserEntity user) {
        return userRepo.save(user);
    }

    // Validate User (Login)
    public boolean validateUser(String email, String password) {
        UserEntity user = userRepo.findByEmail(email);
        return user != null && user.getPassword().equals(password);
    }
    
    public Integer getUserId(String email, String password) {
        UserEntity user = userRepo.findByEmail(email);
        return user.getId();
    }
    
    public UserEntity getUserById(int id) {
        Optional<UserEntity> userOptional = userRepo.findById(id);
        return userOptional.orElse(null);
    }
    
    
}

