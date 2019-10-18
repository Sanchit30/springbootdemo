package com.anaadih.aclassdeal.Model;

public class DeactivateUserDTO {

	private String description;
	
	private long userId;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "DeactivateUserDTO [description=" + description + ", userId=" + userId + "]";
	}
	
	
}

