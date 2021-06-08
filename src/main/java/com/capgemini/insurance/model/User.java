package com.capgemini.insurance.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table
public class User {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int id;
	private String name;
	private String email;
	private String password;
	private Date dateOfBirth;
	private String city;
	private String gender;
	
	 @ManyToMany(cascade = {
             CascadeType.REMOVE
           })
     @JoinTable(
       name = "policypurchase", 
       joinColumns = @JoinColumn(name = "customer_id"),
       inverseJoinColumns = @JoinColumn(name = "policy_Id"))
     @JsonIgnore
     Set<Policy> enrollPolicies;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Set<Policy> getEnrollPolicies() {
		return enrollPolicies;
	}
	public void setEnrollPolicies(Set<Policy> enrollPolicies) {
		this.enrollPolicies = enrollPolicies;
	}
	
	
}
