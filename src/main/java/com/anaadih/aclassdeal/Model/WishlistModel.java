package com.anaadih.aclassdeal.Model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
@Entity
public class WishlistModel {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int wishlistId;
	
	@ManyToOne
	//@NotBlank
	private ProductModel prodId;
	
//	@NotBlank
	@ManyToOne
	private User  userId;
	
	public Date getWishlistDate() {
		return wishlistDate;
	}

	public void setWishlistDate(Date wishlistDate) {
		this.wishlistDate = wishlistDate;
	}

	//@NotBlank
	private Date wishlistDate;
	

	
	private boolean status;

	@PrePersist
	public void setData() {
		this.setStatus(true);
		this.setWishlistDate(new Date());
		//this.setUserId("ADMIN1");
	}
	
	public int getWishlistId() {
		return wishlistId;
	}

	public void setWishlistId(int wishlistId) {
		this.wishlistId = wishlistId;
	}

	public ProductModel getProdId() {
		return prodId;
	}

	public void setProdId(ProductModel prodId) {
		this.prodId = prodId;
	}



	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}


	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}