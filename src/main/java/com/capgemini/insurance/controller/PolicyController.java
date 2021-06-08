package com.capgemini.insurance.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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
import com.capgemini.insurance.model.Policy;
import com.capgemini.insurance.repository.PolicyRepo;
import com.capgemini.insurance.repository.UserRepo;
import com.capgemini.insurance.service.PolicyService;
import com.capgemini.insurance.util.JwtUtil;
import com.capgemini.insurance.util.ResourceNotFoundException;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PolicyController {
	
	@Autowired
	private PolicyRepo userRepo;
	@Autowired
	private PolicyService service;
	@Autowired
	private UserRepo user;
	private Set<Policy> policies;
	
	@GetMapping("fetch")
	public List<Policy> getAllEmployees() {
		return userRepo.findAll();
	}

	@GetMapping("/policy/{id}")
	public ResponseEntity<Policy> getEmployeeById(@PathVariable(value = "id") int  id)
			throws ResourceNotFoundException {
		Policy employee = userRepo.findByPolicyId(id);
				return ResponseEntity.ok().body(employee);
	}


	
	@PutMapping("/update/{id}")
	public ResponseEntity<Policy> updateEmployee(@PathVariable(value = "id") int policyId,
			@Validated @RequestBody Policy policyDetails) throws ResourceNotFoundException {
		Policy policy = userRepo.findById(policyId)
				.orElseThrow(() -> new ResourceNotFoundException("policy not found for this id :: " + policyId));

		policy.setPolicyName(policyDetails.getPolicyName());
		policy.setDescription(policyDetails.getDescription());
		policy.setDuration(policyDetails.getDuration());
		policy.setPrice(policyDetails.getPrice());
		final Policy updatedPolicy = userRepo.save(policy);
		return ResponseEntity.ok(updatedPolicy);
	}

	@DeleteMapping("/delete/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") int policyId)
			throws ResourceNotFoundException {
		Policy policy = userRepo.findById(policyId)
				.orElseThrow(() -> new ResourceNotFoundException("policy not found for this id :: " + policyId));

		userRepo.delete(policy);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
	
	@PostMapping(value = "addpolicy")
    public ResponseEntity<Policy> createPolicy(@RequestBody Policy policy,HttpServletRequest request) {
		String token  = request.getHeader("token");
         return new ResponseEntity<Policy>(service.policyCreation(policy), HttpStatus.CREATED);
    }
	
	
//	 @PostMapping(value = "buyPolicy/{customerid}")
//	 public ResponseEntity<Policy> addPolicy(@PathVariable int customerid, @RequestBody Policy purchasePolicy){
//		 
//		  return user.findById(customerid).map(customers -> {
//	            Set<Policy> policies=customers.getEnrollPolicies();
//	          
//	          if(policies == null) {
//	              policies = new HashSet<Policy>();
//	          }
//	          policies.add(purchasePolicy);
//	  
//	          service.save(customerid,purchasePolicy);
//	          return ResponseEntity.ok(purchasePolicy);
//	      }).orElseThrow();
//	 } 

	@PostMapping(value = "buyPolicy")
	public ResponseEntity<Policy> addPolicy(@RequestBody Policy purchasePolicy, HttpServletRequest request){
		String token = request.getHeader("token");
		int id = JwtUtil.tokenVerification(token);
		return user.findById(id).map(customers -> {
			Set<Policy> policies = customers.getEnrollPolicies();
			if(policies == null) {
				policies = new HashSet<Policy>();
			}
			policies.add(purchasePolicy);
			service.save(id,purchasePolicy);
			return ResponseEntity.ok(purchasePolicy);
		}).orElseThrow();	
	}
	
	}
