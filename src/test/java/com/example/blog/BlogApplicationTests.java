package com.example.blog;

import com.example.blog.model.User;
import com.example.blog.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class BlogApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testSaveUser() {
		User user = new User("testuser", "password", "bio");
		userRepository.save(user);
		User fetchedUser = userRepository.findByUsername("testuser");
		assertNotNull(fetchedUser);
	}
}
