package com.anaadih.aclassdeal.Model;

import java.util.Date;
import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author Paras
 *
 */
//STATUS FOR PRODUCT

//BY DEFAULT "NEW"
//AFTER APPROVAL FROM ADMIN "APPROVED"
//AFTER REJECTED FROM ADMIN "DELETED"
//AFTER REPORTED FROM USER "REPORTED"
//AFTER BID IS PLACED "BIDDING"
//AFTER PRODUCT IS SOLD "SOLD"
//IF PRODUCT NOT SOLD IN ONE MONTH "NOTSOLD"
//PRODUCT TO BE SHOWN ON DASHBOARD

//JSon Ignore unknwon property is  used to ignore the propery from converting json to objec
@Entity
@Indexed
@JsonIgnoreProperties(ignoreUnknown=true)
@AnalyzerDef(name = "customAnalyzer", tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class))
public class ProductModel {
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private int prodId;
		
		//Ad Title
		@NotBlank
	    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)     /* @Field to implement hibernate searching */
		@Analyzer(definition = "customAnalyzer")
		private String  prodName;
		
		@IndexedEmbedded
		@ManyToOne
		private CategoryModel category;
		
		@ManyToOne SubCategoryModel subCategory;
		
		@NotBlank
		 @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)     /* @Field to implement hibernate searching */
		@Analyzer(definition = "customAnalyzer")
		private String prodDesc;
		
		
		@NotBlank
		 @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)     /* @Field to implement hibernate searching */
		@Analyzer(definition = "customAnalyzer")
		private String city;

		private String phoneNumber;		
		
		
		 @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)     /* @Field to implement hibernate searching */
			@Analyzer(definition = "customAnalyzer")
		private String status;
		
		private boolean isInUse;
		
		private boolean isReported;
		
		private boolean isApproved;

		private Date createdDate;
		
		private Date modifiedDate;
		
		
		 @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)     /* @Field to implement hibernate searching */
		@Analyzer(definition = "customAnalyzer")
		private Integer price;
		
		@ManyToOne
		private User userId;

		private String imgNames;
		
		private String mainImage;
		
//		private long bidId;
		
		private String bidTime;
		
		private String bidamount;
		
		private boolean bid;
		
		private String model;
		
		//Check for allow user to visible/hide number to other user on  positng adds
		private boolean allowNumber;
		
		//For getting the likes and view of a product
		private int likes;
		
		private int views;
		
		
		@Transient
		private HashMap<String, String> attributes;
		

		
		

		public int getLikes() {
			return likes;
		}


		public void setLikes(int likes) {
			this.likes = likes;
		}


		public int getViews() {
			return views;
		}


		public void setViews(int views) {
			this.views = views;
		}


		public boolean isBid() {
			return bid;
		}


		public boolean isAllowNumber() {
			return allowNumber;
		}


		public void setAllowNumber(boolean allowNumber) {
			this.allowNumber = allowNumber;
		}


		public String getMainImage() {
			return mainImage;
		}


		public void setMainImage(String mainImage) {
			this.mainImage = mainImage;
		}


		public boolean isApproved() {
			return isApproved;
		}


		public User getUserId() {
			return userId;
		}


		public void setUserId(User userId) {
			this.userId = userId;
		}


		public void setApproved(boolean isApproved) {
			this.isApproved = isApproved;
		}


		public int getProdId() {
			return prodId;
		}


		public void setProdId(int prodId) {
			this.prodId = prodId;
		}


		public String getModel() {
			return model;
		}


		public void setModel(String model) {
			this.model = model;
		}

		 



		public Integer getPrice() {
			return price;
		}


		public void setPrice(Integer price) {
			this.price = price;
		}


		@PrePersist
		public void setValues() {
			this.setStatus("NEW");
			this.setCreatedDate(new Date());
			this.setModifiedDate(new Date());
		}
		
		
		public CategoryModel getCategory() {
			return category;
		}

		public void setCategory(CategoryModel category) {
			this.category = category;
		}

		public SubCategoryModel getSubCategory() {
			return subCategory;
		}

		public void setSubCategory(SubCategoryModel subCategory) {
			this.subCategory = subCategory;
		}



		public String getBidTime() {
			return bidTime;
		}

		public void setBidTime(String bidTime) {
			this.bidTime = bidTime;
		}

		public String getBidamount() {
			return bidamount;
		}

		public void setBidamount(String bidamount) {
			this.bidamount = bidamount;
		}

		public boolean bid() {
			return bid;
		}

		public void setBid(boolean bid) {
			this.bid = bid;
		}







		public String getProdName() {
			return prodName;
		}

		public void setProdName(String prodName) {
			this.prodName = prodName;
		}

		public String getProdDesc() {
			return prodDesc;
		}

		public void setProdDesc(String prodDesc) {
			this.prodDesc = prodDesc;
		}

	

		public String getStatus() {
			return status;
		}


		public void setStatus(String status) {
			this.status = status;
		}


		public boolean isInUse() {
			return isInUse;
		}

		public void setInUse(boolean isInUse) {
			this.isInUse = isInUse;
		}

		public Date getCreatedDate() {
			return createdDate;
		}

		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}

		public Date getModifiedDate() {
			return modifiedDate;
		}

		public void setModifiedDate(Date modifiedDate) {
			this.modifiedDate = modifiedDate;
		}

		public String getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public boolean isReported() {
			return isReported;
		}

		public void setReported(boolean isReported) {
			this.isReported = isReported;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}
		
		public String getImgNames() {
			return imgNames;
		}

		public void setImgNames(String imgNames) {
			this.imgNames = imgNames;
		}

	
		@PreUpdate
		public void update() {
			this.setModifiedDate(new Date());	
		}


		public HashMap<String, String> getAttributes() {
			return attributes;
		}


		public void setAttributes(HashMap<String, String> attributes) {
			this.attributes = attributes;
		}


		@Override
		public String toString() {
			return "ProductModel [prodId=" + prodId + ", prodName=" + prodName + ", category=" + category
					+ ", subCategory=" + subCategory + ", prodDesc=" + prodDesc + ", phoneNumber=" + phoneNumber
					+ ", city=" + city + ", status=" + status + ", isInUse=" + isInUse + ", isReported=" + isReported
					+ ", isApproved=" + isApproved + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate
					+ ", price=" + price + ", userId=" + userId + ", imgNames=" + imgNames 
					+ ", bidTime=" + bidTime + ", bidamount=" + bidamount + ", isBid=" + bid + ", model=" + model
					+ ", attributes=" + attributes +  "]";
		}



		
		

}
