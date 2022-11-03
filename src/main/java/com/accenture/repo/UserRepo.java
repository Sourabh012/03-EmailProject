package com.accenture.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accenture.entity.User;
@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
	
	public User findByEmailId(String emailId);

}
