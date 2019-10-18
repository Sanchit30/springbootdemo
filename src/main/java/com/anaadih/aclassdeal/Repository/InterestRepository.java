package com.anaadih.aclassdeal.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadih.aclassdeal.Model.IntrestModel;
import com.anaadih.aclassdeal.Model.ProductModel;
import com.anaadih.aclassdeal.Model.User;

@Repository
public interface InterestRepository extends PagingAndSortingRepository<IntrestModel, Integer>{

	
	List<IntrestModel> findByProduct(ProductModel product);

	IntrestModel findByProductAndUser(ProductModel product, User user);

	boolean existsByProductAndUser(ProductModel product, User user);

	List<IntrestModel> findByUser(User user, Pageable pg);

	List<IntrestModel> findByProductUserId(User user, Pageable pg);

	long countByUser(User user);

	long countByProductUserId(User user);
}
