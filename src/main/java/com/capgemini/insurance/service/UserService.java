package com.capgemini.insurance.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.capgemini.insurance.model.User;

public interface UserService {
	 public User userCreation(User user, HttpServletRequest request);
	 public User update(int id, User user);
     public boolean deleteMethod(int id);
     public String login(User loginRequest);
	
}
