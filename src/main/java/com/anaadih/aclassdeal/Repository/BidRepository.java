package com.anaadih.aclassdeal.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadih.aclassdeal.Model.Bid;
import com.anaadih.aclassdeal.Model.ProductModel;
import com.anaadih.aclassdeal.Model.User;

@Repository
public interface BidRepository extends PagingAndSortingRepository<Bid, Integer>{

	List<Bid> findByProduct(ProductModel product);

	Bid findByProductAndUser(ProductModel product, User user);

	boolean existsByProductAndUser(ProductModel product, User user);

	List<Bid> findByUser(User user, Pageable pg);

	List<Bid> findByProductUserId(User user, Pageable pg);

	long countByUser(User user);

	long countByProductUserId(User user);

}
