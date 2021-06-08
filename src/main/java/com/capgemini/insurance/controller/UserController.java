package com.capgemini.insurance.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.insurance.model.LoginRequest;
import com.capgemini.insurance.model.Policy;
import com.capgemini.insurance.model.User;
import com.capgemini.insurance.repository.UserRepo;
import com.capgemini.insurance.service.UserService;
import com.capgemini.insurance.util.Encryption;
import com.capgemini.insurance.util.ResourceNotFoundException;
@RestController
@RequestMapping("insurance")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

	@Autowired
	private UserService service;
	@Autowired
	private UserRepo userRepo;

	
	@PostMapping(value = "/register")
    public User createUser(@RequestBody User user,HttpServletRequest request) {
        service.userCreation(user,request);
         return user;
    }
	
	@GetMapping(value="/fetch")
	public List<User> fetch(HttpServletRequest request)
	{
		return userRepo.findAll();	
	}
	
	@GetMapping(value="/getById/{id}")
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") int  id)
			throws ResourceNotFoundException {
		User user = userRepo.findByid(id);
				return ResponseEntity.ok().body(user);
	}
	
	  @PutMapping(value = "/updateuser/{id}")
	    public void updateUser(@RequestBody User user, @PathVariable int id) {
	        service.update(id, user);
	    }
	  
	  @DeleteMapping(value= "/delete/{id}")
	  public ResponseEntity<String> deleteUser(@PathVariable int id,HttpServletRequest request){
		  service.deleteMethod(id);
		  return new ResponseEntity<String>("Deleted",HttpStatus.OK);
	  }
	 
	  @PostMapping(value= "/login")
	  public ResponseEntity<?> loginMethod(@RequestBody User loginRequest, HttpServletRequest request, HttpServletResponse response){
		  String token = service.login(loginRequest);
		  //response.setHeader("token",token);
		  if(token != null)
	 	  {
			  response.addHeader("token", token);
			  response.addHeader("Access-control-Allow-Headers", "*");
			  response.addHeader("Access-control-Expose-Headers", "*");
			  return new ResponseEntity<>(HttpStatus.OK);
		  }
		  else
		  {
		  return new ResponseEntity<>("{Invalid user}", HttpStatus.BAD_REQUEST);
		  }
}
	
	  
	  
}