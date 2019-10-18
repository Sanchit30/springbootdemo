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
public class Bid {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	private ProductModel product;
	
	@ManyToOne
	private User user;
	
	private String status;
	
	private Date bidDate;
	
	private long bidAmount;
	
	private String comment;

	@PrePersist
	public void setData() {
		this.bidDate= new Date();
		this.status="NEW";
	}
	
	public int getId() {
		return id;
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

	public Date getBidDate() {
		return bidDate;
	}

	public void setBidDate(Date bidDate) {
		this.bidDate = bidDate;
	}

	public long getBidAmount() {
		return bidAmount;
	}

	public void setBidAmount(long bidAmount) {
		this.bidAmount = bidAmount;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "Bid [id=" + id + ", product=" + product + ", user=" + user + ", status=" + status + ", bidDate="
				+ bidDate + ", bidAmount=" + bidAmount + ", comment=" + comment + "]";
	}
	
	
	
	
	
	
	
}