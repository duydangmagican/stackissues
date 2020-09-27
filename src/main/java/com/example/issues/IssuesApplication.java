package com.example.issues;

import com.example.issues.Entities.Role;
import com.example.issues.Entities.User;
import com.example.issues.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class IssuesApplication {

	static private UserRepository userRepository;

	public IssuesApplication(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(IssuesApplication.class, args);

		List<User> list  = IssuesApplication.userRepository.findAll();
		if(list.size() ==0) {
			IssuesApplication.userRepository.save(new User("dang","123", Role.ADMIN));
			IssuesApplication.userRepository.save(new User("dat","123", Role.EXECUTOR));
			IssuesApplication.userRepository.save(new User("duc","123", Role.USER));
			IssuesApplication.userRepository.save(new User("kien","123", Role.USER));
			IssuesApplication.userRepository.save(new User("nhan","123", Role.USER));
		}

	}

}
