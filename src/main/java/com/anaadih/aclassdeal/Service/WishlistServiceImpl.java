package com.anaadih.aclassdeal.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.anaadih.aclassdeal.Model.ProductModel;
import com.anaadih.aclassdeal.Model.User;
import com.anaadih.aclassdeal.Model.WishlistModel;
import com.anaadih.aclassdeal.Repository.ProductRepository;
import com.anaadih.aclassdeal.Repository.WishlistRepository;

@Service
public class WishlistServiceImpl implements WishlistService {

	@Autowired
	private WishlistRepository wishlistRepository;
	
	@Autowired
	private productService productService;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public HashMap<String, Object> addWishlist(String prodId, User userId) {
		HashMap<String, Object> map = new HashMap<>();
		WishlistModel wishlist = new WishlistModel();
		WishlistModel previousAdded=wishlistRepository.findByUserIdAndProdIdProdId(userId,Integer.parseInt(prodId));
		if(previousAdded!=null) {
			map.put("msg", "Already Added to wishlist");
			map.put("type", "Warning!");
			return map;
		}
		//For every product added to wishlist ,add the likes count by one
		ProductModel product =productService.getProductById(Integer.parseInt(prodId));
		if(product!=null)
		{
			product.setLikes(product.getLikes()+1);
			productRepository.save(product);
		}
		wishlist.setProdId(product);
		wishlist.setUserId(userId);
		
		
		wishlist.setStatus(true);
		 wishlistRepository.save(wishlist);
		 
		 map.put("msg", "Product Added to wishlist");
		 map.put("type", "Success");
		 return map;
		 
	}

	@Override
	public List<WishlistModel> getallWishlist(User userId,int limit,int offset) {
		Pageable pg=PageRequest.of(offset,limit, new Sort(Direction.ASC,"wishlistDate"));
		return wishlistRepository.findByUserId(userId,pg);
	}

}