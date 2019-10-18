package com.anaadih.aclassdeal.Model;

public class SearchModel {

	private String searchString;
	
	private String location;

	private String dateRange;
	
	private String price;
	
	private String category;

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDateRange() {
		return dateRange;
	}

	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "SearchModel [searchString=" + searchString + ", location=" + location + ", dateRange=" + dateRange
				+ ", price=" + price + ", category=" + category + "]";
	}
	
	
	
	
	
	
}
