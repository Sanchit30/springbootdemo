package com.anaadih.aclassdeal.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class AttributeModel {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int attributeId;
	
	//Attributes are taken sub Category wise
	@ManyToOne(cascade=CascadeType.REMOVE)
	private SubCategoryModel parentCategory;
	
	@NotBlank
	private String attributeName;
	
	private String typeForInput;
	
	@Size(min=4,max=20)
	private String attributeType;
	
	private String attributeValue;
	
	private String status;



	public String getTypeForInput() {
		return typeForInput;
	}

	public void setTypeForInput(String typeForInput) {
		this.typeForInput = typeForInput;
	}
	

	public SubCategoryModel getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(SubCategoryModel parentCategory) {
		this.parentCategory = parentCategory;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(int attributeId) {
		this.attributeId = attributeId;
	}


	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	@Override
	public String toString() {
		return "AttributeModel [attributeId=" + attributeId + ", parentCategory=" + parentCategory + ", attributeName="
				+ attributeName + ", attributeType=" + attributeType + ", attributeValue=" + attributeValue
				+ ", status=" + status + "]";
	}

	
	
	
}
