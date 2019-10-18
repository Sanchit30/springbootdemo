package com.anaadih.aclassdeal.Controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.anaadih.aclassdeal.FileUploader;
import com.anaadih.aclassdeal.Model.CategoryModel;
import com.anaadih.aclassdeal.Model.ProductModel;
import com.anaadih.aclassdeal.Model.SearchModel;
import com.anaadih.aclassdeal.Model.User;
import com.anaadih.aclassdeal.Repository.UserRepository;
import com.anaadih.aclassdeal.Service.ClosedDealService;
import com.anaadih.aclassdeal.Service.FileUploadService;
import com.anaadih.aclassdeal.Service.ProductAttributeService;
import com.anaadih.aclassdeal.Service.ReportedAidService;
import com.anaadih.aclassdeal.Service.productService;
import com.anaadih.aclassdeal.util.CommonResponseSender;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Paras
 *
 */

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*")

public class ProductController {
	
	@Autowired
	private productService productService;
	
	@Autowired
	private ProductAttributeService productAttributeService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private FileUploader fileUploder;
	
	@Autowired
	private UserRepository userrepository;
	
	@Autowired 
	private ReportedAidService reportedService;
	
	@Autowired
	private com.anaadih.aclassdeal.Service.UserService userService;
	

	@Autowired
	private ClosedDealService closedDealService;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	
	private static final List<String> STATUS_LIST = Arrays.asList("APPROVED","BIDDING");
	private static final List<String> STATUS_LIST_NEW = Arrays.asList("NEW");
	
	/**
	 * method to save product with attributes and images
	 * @param product
	 * @param images
	 * @param mappings
	 * @param errors
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping(value="/saveProductWithImages",method=RequestMethod.POST)
	public Map<String,Object> saveProduct(@RequestParam(value="file",required=false) MultipartFile[] files,@RequestParam(value="productString",required=false) String productString,HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException
	{
		final HashMap<String, Object> map = new HashMap<>();
		int i=0;
		if(files!=null) {
		 i=files.length;
		}
		ObjectMapper objMapper= new ObjectMapper();
		TypeReference<ProductModel> mapType= new TypeReference<ProductModel>() {
		};
		ProductModel product= objMapper.readValue(productString, mapType);
		HashMap<String ,String> mappings=product.getAttributes();

		//for updateing in in use
		productService.updateCategorySubCategoryInUse(product);
		
		String userId=SecurityContextHolder.getContext().getAuthentication().getName();
		//Setting logged in user
		//IF USER IS ADMIN THEN APPROVE HIS ADS 
		//Optional<User> user = userrepository.findByUsername(userId);
		System.out.println("USER ID ISSS"+userId);
		Optional<User> user = userrepository.findByEmail(userId);
		if(user.isPresent()) {
			product.setUserId(user.get());
			product=productService.saveProduct(product);
			if("ADMIN".equals(user.get().getUserType())) {
				product.setStatus("APPROVED");
				product=productService.saveProduct(product);
			}
			map.put("product",product);
		}
		productAttributeService.saveMapping(product.getProdId(), mappings, userId);
		
		//product with no photo
//		if(i>0)
//		{
//			fileUploadService.saveImagesofProduct(files,product);
//		}
		
		///WEBSOCKET FOR SENDING NOTIFICATION TO ADMIN
		//simpMessaginTemplate.convertAndSend("/topic/response", "One Product Added !");
		System.out.println("AAbout to send notification");
		simpMessagingTemplate.convertAndSend("/topic/admin/notification", "One Product Added");
	     
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
	
	@RequestMapping(value="/getAllPostedAdsOfUser",method=RequestMethod.GET)
	public Map<String,Object> getAllPostedAdsOfUser(@RequestParam(value="limit")int limit,
			@RequestParam(value="offset")int offset,
			HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("productList", productService.getAllPostedAdsOfUser(limit,offset));
		//map.put("count", productService.getAllPostedAdsOfUser(limit,offset))
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	
	
	@RequestMapping(value="/getAllActivePostedAdsOfUser",method=RequestMethod.GET)
	public Map<String,Object> getAllActivePostedAdsOfUser(@RequestParam(value="limit")int limit,
			@RequestParam(value="offset")int offset,
			HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("productList", productService.getAllActivePostedAdsOfUser(limit,offset));
		
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	
	@RequestMapping(value="/getAllExpiredPostedAdsOfUser",method=RequestMethod.GET)
	public Map<String,Object> getAllExpiredPostedAdsOfUser(@RequestParam(value="limit")int limit,
			@RequestParam(value="offset")int offset,
			HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("productList", productService.getAllExpiredPostedAdsOfUser(limit,offset));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	@RequestMapping(value="/getAllRejectedPostedAdsOfUser",method=RequestMethod.GET)
	public Map<String,Object> getAllRejectedPostedAdsOfUser(@RequestParam(value="limit")int limit,
			@RequestParam(value="offset")int offset,
			HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("productList", productService.getAllRejectedPostedAdsOfUser(limit,offset));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	@RequestMapping(value="/getAllUnapprovedPo/topic/admin/notificationstedAdsOfUser",method=RequestMethod.GET)
	public Map<String,Object> getAllUnapprovedPostedAdsOfUser(@RequestParam(value="limit")int limit,
			@RequestParam(value="offset")int offset,
			HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("productList", productService.getAllUnapprovedPostedAdsOfUser(limit,offset));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	

	
	
	/**
	 * method to get product By Id
	 * @param prodId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getAllProducts",method=RequestMethod.GET)
	public Map<String,Object> getAllProducts(@RequestParam(value="limit")int limit,
			@RequestParam(value="offset")int offset,
			HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("productList", productService.getAllProducts(limit,offset-1));
		map.put("count", productService.getCountByStatus(STATUS_LIST));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	
	/**
	 * method to get product By Id
	 * @param prodId
	 * @param request
	 * @param response
	 * @return
	 */
	
	@RequestMapping(value="/getProductById",method=RequestMethod.GET)
	public Map<String,Object> getProductById(@RequestParam(value="prodId")String prodId,HttpServletRequest request,HttpServletResponse response){
		
		final HashMap<String, Object> map = new HashMap<>();
		map.put("product", productService.getProductById(Integer.parseInt(prodId)));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	

	
	/**
	 * all pending products are shown to admin
	 * @param limit
	 * @param offset
	 * @param request
	 * @param response
	 * @return
	 */
	
	@RequestMapping(value="/getAllPendingProducts",method=RequestMethod.GET)
	public Map<String,Object> getAllPendingProducts(@RequestParam(value="limit")int limit,
			@RequestParam(value="offset")int offset,
			HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("pendingProductList", productService.getAllPendingProducts(limit,offset));
		map.put("count",productService.getCountByStatus(STATUS_LIST_NEW));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	

	/**
	 * dashboard method to get products
	 * @param limit
	 * @param offset
	 * @param request
	 * @param response
	 * @return
	 */
	
	@RequestMapping(value="/getAllProductsDashboard",method=RequestMethod.GET)
	public Map<String,Object> getAllProductsDashboard(@RequestParam(value="limit")int limit,
			@RequestParam(value="offset")int offset,
			HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("productList", productService.getAllProducts(limit,offset));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	/**
	 * method to approve all products by admin
	 * @param limit
	 * @param offset
	 * @param request
	 * @param response
	 * @return
	 */
	
	@RequestMapping(value="/approveOrBlockProduct",method=RequestMethod.GET)
	public Map<String,Object> approveOrBlockProduct(@RequestParam (value = "Ids") List<Integer> Ids,
			@RequestParam (value = "status") String status,
			HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		productService.approveProduct(Ids,status);
		map.put("product", "Approved");
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	
	@RequestMapping(value="/getAllDetailsOfProduct",method=RequestMethod.GET)
	public Map<String,Object> getAllDetailsOfProduct(@RequestParam (value = "prodId") Integer prodId,
			HttpServletRequest request,HttpServletResponse response){
		 HashMap<String, Object> map = new HashMap<>();
		map=productService.getAllDetailsOfProduct(prodId);
		
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	
	
	/**
	 * Search by hibernate searching by passing search keyword
	 * 
	 * @param searchQuery
	 * @param request
	 * @param response
	 * @return
	 */
	
	@RequestMapping(value="/searchProduct",method=RequestMethod.POST)
	public Map<String,Object> searchProduct(
			@RequestBody SearchModel searchModel,
			@RequestParam(value="limit")int limit,
			@RequestParam(value="offset")int offset,
			HttpServletRequest request,HttpServletResponse response){
		 HashMap<String, Object> map = new HashMap<>();

			System.out.println("SEARCH MODEL US"+searchModel);
		 List<?> result = productService.getSearchResult(searchModel, limit, offset);
		 
		 map.put("count", result.size());
		 map.put("data", result);
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	
	
	
	/**
	 * method to get product By Id
	 * @param prodId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getAllProductsOfCategory",method=RequestMethod.GET)
	public Map<String,Object> getAllProductsOfCategory(@RequestParam(value="limit")int limit,
			@RequestParam(value="offset")int offset,
			@RequestParam(value="catCode")int catCode,
			HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("productList", productService.getAllProductsOfCategory(limit,offset-1,catCode));
		map.put("count", productService.getCountOfCategory(catCode));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	
	
	
	/**
	 * method to get product By Id
	 * @param prodId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getAllProductsOfSubCategory",method=RequestMethod.GET)
	public Map<String,Object> getAllProductsOfSubCategory(@RequestParam(value="limit")int limit,
			@RequestParam(value="offset")int offset,
			@RequestParam(value="catCode")int catCode,
			HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("productList", productService.getAllProductsOfSubCategory(limit,offset-1,catCode));
		map.put("count", productService.getCounOfSubCategory(catCode));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	
	
	
	/**
	 * method to get product By Id
	 * @param prodId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getAllCountOfNewProduct",method=RequestMethod.GET)
	public Map<String,Object> getAllCountOfNewProduct(
			
			HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("newProductCount", productService.getAllCountOfNewProduct());
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	
	
	@RequestMapping(value="/getAllDataForAdminDashboard",method=RequestMethod.GET)
	public Map<String,Object> getAllDataForAdminDashboard(
			
			HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("adCount", productService.getAllCountOfNewProduct());
		map.put("reportedCount", reportedService.getCountOfAllReportedAds());
		map.put("userCount", userService.getCountOfAllUsers());
		//It is constant for now
		map.put("totalClosedDeal", closedDealService.getCountOfClosedDeal());
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	/**
	 * 
	 * @param limit
	 * @param offset
	 * @param catCode
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getAllProductsIrrespectiveOfStatus",method=RequestMethod.GET)
	public Map<String,Object> getAllProductsIrrespectiveOfStatus(@RequestParam(value="limit")int limit,
			@RequestParam(value="offset")int offset,
			
			HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("productList", productService.getAllProductsIrrespectiveOfStatus(limit,offset));
		map.put("count",productService.getCountOfAllProduct());
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
}
