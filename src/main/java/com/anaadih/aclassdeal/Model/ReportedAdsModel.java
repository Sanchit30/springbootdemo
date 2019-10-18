package com.anaadih.aclassdeal.Model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;
/**
 * 
 * @author Paras
 *
 */
@Entity
public class ReportedAdsModel {
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int reportedId;
	
	@ManyToOne
	private ProductModel productId;
	
	private String description;
	
	private String  reportedBy;
	
	private String uploadedBy;
	

	private Date reportDate;
	
	
	private int reportCount;
	
	private boolean status;
	
	private String comment;


	@PrePersist
	public void setData() {
//		this.setReportedBy("ADMIN");
//		this.setUploadedBy("USER1");
		this.setReportDate(new Date());
		this.setStatus(true);
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getReportedId() {
		return reportedId;
	}

	public void setReportedId(int reportedId) {
		this.reportedId = reportedId;
	}
	



	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public ProductModel getProductId() {
		return productId;
	}

	public void setProductId(ProductModel productId) {
		this.productId = productId;
	}



	public String getReportedBy() {
		return reportedBy;
	}

	public void setReportedBy(String reportedBy) {
		this.reportedBy = reportedBy;
	}

	public String getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}


	public int getReportCount() {
		return reportCount;
	}

	public void setReportCount(int reportCount) {
		this.reportCount = reportCount;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}


	
	
	public ReportedAdsModel() {
		super();
	}


	@Override
	public String toString() {
		return "ReportedAdsModel [reportedId=" + reportedId + ", productId=" + productId + ", description="
				+ description + ", reportedBy=" + reportedBy + ", uploadedBy=" + uploadedBy + ", reportDate="
				+ reportDate + ", reportCount=" + reportCount + ", status=" + status + ", comment=" + comment + "]";
	}

	
}
