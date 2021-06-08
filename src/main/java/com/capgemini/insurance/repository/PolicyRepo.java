package com.capgemini.insurance.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.insurance.model.Policy;

public interface PolicyRepo extends JpaRepository<Policy, Integer>{
	
	public Policy findByPolicyId(int policyId);
	 void deleteByPolicyId(int policyId);
	 //public Optional findById(int policyId);
	
}
