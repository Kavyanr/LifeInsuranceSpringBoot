package com.capgemini.insurance.service;

import com.capgemini.insurance.model.Policy;

public interface PolicyService {

	public Policy policyCreation(Policy user);

	public Object save(int customerid, Policy purchasePolicy);


	
	

}
