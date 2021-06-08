package com.capgemini.insurance.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.insurance.model.Policy;
import com.capgemini.insurance.repository.PolicyRepo;
import com.capgemini.insurance.repository.UserRepo;

@Service
@Transactional
public class PolicyServiceImpl implements PolicyService{

	@Autowired
	private PolicyRepo repo;
	@Autowired
	private UserRepo userRepo;
	@Override
	public Policy policyCreation(Policy user) {
		repo.save(user);
		return user;
	}

//
//	@Override
//	public Policy update(Policy policy,int policyId) {
//		 
//           Optional<Policy> policyInfo = repo.findByPolicyId(policyId);
//           Policy presentUser = policyInfo.map(existingUser -> {
//	        	existingUser.setPolicyName(policy.getPolicyName() != null ? policy.getPolicyName() : policyInfo.get().getPolicyName());
//	        	existingUser.setDescription(policy.getDescription() != null ? policy.getDescription() : policyInfo.get().getDescription());
//	        	existingUser.setDuration(policy.getDuration() != 0 ? policy.getDuration() : policyInfo.get().getDuration());
//	        	existingUser.setPrice(policy.getPrice() != 0 ? policy.getPrice() : policyInfo.get().getPrice());
//	        	System.out.println("Successfully updated");
//	            return existingUser;
//	        }).orElseThrow(() -> new RuntimeException("User Not Found"));
//
//       return repo.save(policyInfo.get());
//	}
//	@Override
//	public String deleteMethod(int policyId){
//		repo.deleteByPolicyId(policyId);
//		return "Deleted";
//	}
	@Override
	public Object save(int customerid, Policy purchasePolicy) {
		int policy_Id = purchasePolicy.getPolicyId();
		Optional<Policy> user= repo.findById(policy_Id);
		if(user !=null) {
			purchasePolicy.getPolicyName();
			purchasePolicy.getDescription();
			purchasePolicy.getDuration();
		    purchasePolicy.getPrice();
		   repo.save(purchasePolicy);
		}
		return purchasePolicy;
	}

	
	  
	     
}
