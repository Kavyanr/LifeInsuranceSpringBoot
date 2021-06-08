package com.capgemini.insurance.service;



	import java.util.List;
	import java.util.Optional;

	import javax.servlet.http.HttpServletRequest;
	import javax.transaction.Transactional;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;

import com.capgemini.insurance.model.LoginRequest;
import com.capgemini.insurance.model.User;
import com.capgemini.insurance.repository.UserRepo;
import com.capgemini.insurance.util.Encryption;
import com.capgemini.insurance.util.JwtUtil;

	@Service
	@Transactional
	public class UserServiceImpl implements UserService{

		@Autowired
		private UserRepo userRepo;
		
		@Override
		public User userCreation(User user, HttpServletRequest request) {
			user.setPassword(Encryption.encryptedPassword(user.getPassword()));
			userRepo.save(user);
			Optional<User> usr = userRepo.findById(user.getId());
			if(user != null) {
				System.out.println("Successfully registered");
				//String token = JwtUtil.jwtTokenGenerator("secretKey", usr.get().getId());
				User u = usr.get();
				return user;
			}
			else
				System.out.println("Not registered successfully");
			return null;
		}
		
		@Override
		public User update(int id, User user) {
			Optional<User> maybeUser = userRepo.findById(id);
			 User presentUser = maybeUser.map(existingUser -> {
			            existingUser.setEmail(user.getEmail() != null ? user.getEmail() : maybeUser.get().getEmail());
//			            existingUser.setPhoneNumber( user.getPhoneNumber() != null ? user.getPhoneNumber() : maybeUser.get().getPhoneNumber());
			            existingUser.setName(user.getName() != null ? user.getName() : maybeUser.get().getName());
			            existingUser.setCity(user.getCity() != null ? user.getCity() : maybeUser.get().getCity());
			            existingUser.setDateOfBirth(user.getDateOfBirth() != null ? user.getDateOfBirth() : maybeUser.get().getDateOfBirth());
			            existingUser.setPassword(user.getPassword() != null ? Encryption.encryptedPassword(user.getPassword()) : maybeUser.get().getPassword());
			            System.out.println("Successfully updated");
			            return existingUser;
			        }).orElseThrow(() -> new RuntimeException("User Not Found"));

			        return userRepo.save(presentUser);
			    }

			@Override
		public boolean deleteMethod(int id) {
			
			Optional<User> maybeUser = userRepo.findById(id);
			 return maybeUser.map(existingUser -> {
		            userRepo.delete(existingUser);
		            return true;
		        }).orElseGet(() -> false);
		}
			
			
			 @Override
			    public String login(User loginReq) {
			    	Optional<User> maybeUser = userRepo.findByEmailAndPassword(loginReq.getEmail(),
			    			Encryption.encryptedPassword(loginReq.getPassword()));
			    	System.out.println(maybeUser);

			    	if (maybeUser.isPresent()) {
			    	System.out.println("Sucessful login");
			  
			    	return JwtUtil.jwtTokenGenerator(Encryption.encryptedPassword(loginReq.getPassword()),maybeUser.get().getId());
			    	} 
			    	else
			    	return "Invalid User";
			    }

			
	

	}


