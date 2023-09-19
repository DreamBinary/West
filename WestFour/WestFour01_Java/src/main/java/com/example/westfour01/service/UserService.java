package com.example.westfour01.service;

import com.example.westfour01.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.westfour01.dao.UserRepository;

import javax.transaction.Transactional;

/**
 * @author CXQ
 * @date 2022/1/26
 */

@Transactional(rollbackOn = Exception.class)
@Service
// @RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class UserService {
    @Autowired
    private final UserRepository repository = null;

    public boolean exist(User user) {
        return repository.existsById(user.getUsername());
    }

    public boolean insert(User user) {
        repository.save(user);
        return true;
    }

    public boolean update(User user) {
        if (repository.existsById(user.getUsername())) {
            repository.save(user);
            return true;
        }
        return false;
    }

    public boolean delete(String username) {
        repository.deleteById(username);
        return true;
    }

    public boolean findMatchUser(User user) {
        User user0 = repository.findUserByUsername(user.getUsername());
        return user.getPassword().equals(user0.getPassword());
    }
}
