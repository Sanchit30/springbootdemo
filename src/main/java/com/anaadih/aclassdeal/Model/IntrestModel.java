package com.anaadih.aclassdeal.Model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown=true)
public class IntrestModel {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	private ProductModel product;
	
	@ManyToOne
	private User user;
	
	private String status;
	
	private boolean profileNumber;
	
	private Date intrestDate;
		
	private String phoneNumber;
	
	private String comment;
	
	
	@PrePersist
	public void setData() {
		this.intrestDate= new Date();
		this.status="NEW";
	}

	public int getId() {
		return id;
	}

	
	public boolean isProfileNumber() {
		return profileNumber;
	}

	public void setProfileNumber(boolean profileNumber) {
		this.profileNumber = profileNumber;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ProductModel getProduct() {
		return product;
	}

	public void setProduct(ProductModel product) {
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getIntrestDate() {
		return intrestDate;
	}

	public void setIntrestDate(Date intrestDate) {
		this.intrestDate = intrestDate;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	
	
	
	
}
