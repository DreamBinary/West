package com.example.westfour01.dao;

import com.example.westfour01.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author CXQ
 * @date 2022/1/26
 */

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    User findUserByUsername(String username);

}
