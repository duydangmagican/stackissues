package com.example.issues.Repositories;

import com.example.issues.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

    @Query("select u from User u where u.username= :username and u.password= :password")
    User findUser(@Param("username") String username,@Param("password") String password);

    @Query("select u.username from User u")
    List<String> listUsername();

}
