package com.capgemini.insurance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.insurance.model.User;

public interface UserRepo extends JpaRepository <User, Integer>{
	Optional<User> findById(int id);
	Optional<User> findAllById(int id);
	Optional<User> findByEmailAndPassword(String email, String password);
	User findByid(int id);
}
