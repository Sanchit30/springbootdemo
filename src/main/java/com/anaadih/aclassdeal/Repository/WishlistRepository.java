package com.anaadih.aclassdeal.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.anaadih.aclassdeal.Model.User;
import com.anaadih.aclassdeal.Model.WishlistModel;

public interface WishlistRepository extends PagingAndSortingRepository<WishlistModel,Integer> {

	WishlistModel save(WishlistModel wishlist);

	List<WishlistModel> findByUserId(User userId, Pageable pg);

	WishlistModel findByUserIdAndProdIdProdId(User userId, int prodId);

}