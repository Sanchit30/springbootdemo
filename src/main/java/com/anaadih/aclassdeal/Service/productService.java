package com.anaadih.aclassdeal.Service;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.anaadih.aclassdeal.Model.ProductModel;
import com.anaadih.aclassdeal.Model.SearchModel;


@Service
public interface productService {

	ProductModel saveProduct(ProductModel category);

	List<ProductModel> getAllProducts(int limit, int offset);

	ProductModel getProductById(int prodId);
	
	List<ProductModel> getAllPendingProducts(int limit, int offset);

	void approveProduct(List<Integer> ids, String status);

	HashMap<String, Object> getAllDetailsOfProduct(Integer prodId);
	
	List<ProductModel> getAllPostedAdsOfUser(int limit, int offset);

	List<?> getSearchResult(SearchModel searchModel, int limit, int offset);

	List<ProductModel> getAllProductsOfCategory(int limit, int i, int catCode);

	long getAllCountOfNewProduct();

	List<ProductModel> getAllProductsOfSubCategory(int limit, int offset, int catCode);

	void updateCategorySubCategoryInUse(ProductModel product);

	long getCountByStatus(List<String> statusListNew);

	long getCountOfCategory(int catCode);

	long getCounOfSubCategory(int catCode);

	List<ProductModel> getAllProductsIrrespectiveOfStatus(int limit, int i);

	long getCountOfAllProduct();

	List<ProductModel> getAllActivePostedAdsOfUser(int limit, int offset);

	List<ProductModel> getAllExpiredPostedAdsOfUser(int limit, int offset);

	List<ProductModel>  getAllRejectedPostedAdsOfUser(int limit, int offset);

	List<ProductModel> getAllUnapprovedPostedAdsOfUser(int limit, int offset);
}
