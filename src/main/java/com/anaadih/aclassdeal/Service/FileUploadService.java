package com.anaadih.aclassdeal.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.anaadih.aclassdeal.Model.CategoryModel;
import com.anaadih.aclassdeal.Model.ProductImageMapping;
import com.anaadih.aclassdeal.Model.ProductModel;
import com.anaadih.aclassdeal.Model.User;
import com.anaadih.aclassdeal.Repository.CategoryRepository;
import com.anaadih.aclassdeal.Repository.UserRepository;
import com.anaadih.aclassdeal.property.FileUploadProperties;
import com.anaadih.aclassdeal.util.FileStorageException;
import com.anaadih.aclassdeal.util.MyFileNotFoundException;



@Service
public class FileUploadService {


	private final Path fileStoragePath;
    private final Path fileStorageLocation;
    
	@Autowired
	private productService productService;
	
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private UserRepository userRepository;
     
    @Value("${application.public.domain}")
	private String applicationPublicDomain;
    
    @Autowired
    public FileUploadService(FileUploadProperties fileStorageProperties) {
    	this.fileStoragePath = Paths.get(fileStorageProperties.getUploadDir());
        this.fileStorageLocation = this.fileStoragePath.toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
    
    public String generateFileUri(String fileName) {
    	return applicationPublicDomain+"/downloadFile/"+fileName;
//    	return ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/downloadFile/")
//                .path(fileName)
//                .toUriString();
    }
    
    private String generateFileName() {
        return "PIC_"+String.valueOf(new Date().getTime());
    }

    private String generateFileNameForProduct(int prodId) {
        return prodId+"_"+String.valueOf(new Date().getTime());
    }
    
	private String generateFileNameForCategory(Integer catCode) {
		return catCode+"_"+String.valueOf(new Date().getTime());
	}
	private String generateFileNameForUser(Long long1) {
		return "USER_"+long1+"_"+String.valueOf(new Date().getTime());
	}
	
	

	public ProductImageMapping storeFile(MultipartFile file) {
	     // Normalize file name
        String fileName = StringUtils.cleanPath(generateFileName());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return new ProductImageMapping(fileName, generateFileUri(fileName),file.getContentType(), file.getSize());
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
	}

	public List<ProductImageMapping> storeFiles(MultipartFile[] files) {
		List<ProductImageMapping> uploadMultipleFiles = new ArrayList<>();
    	for(MultipartFile file : files){
    		 uploadMultipleFiles.add(storeFile(file));
    	}
    	return uploadMultipleFiles;
	}

	public Resource loadFileAsResource(String fileName) {
		try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
	}

	public void saveImagesofProduct(MultipartFile[] files, ProductModel product) {
		List<String> imgName=new ArrayList<>();
		for(MultipartFile file : files){
   		 		storeImageForProduct(file,imgName,product.getProdId());
			}
		if(imgName!=null && imgName.size()>0) {
			product.setMainImage(imgName.get(0));
		}
		String csv=String.join(",", imgName);
		csv=csv.substring(csv.indexOf(","), csv.length());
		product.setImgNames(csv);
		productService.saveProduct(product);
	}

	private void storeImageForProduct(MultipartFile file, List<String> imgName, int prodId) {
		 String fileName = StringUtils.cleanPath(generateFileNameForProduct(prodId));
		 
	        try {
	            // Check if the file's name contains invalid characters
	            if(fileName.contains("..")) {
	                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
	            }

	            // Copy file to the target location (Replacing existing file with the same name)
	            Path targetLocation = this.fileStorageLocation.resolve(fileName);
	            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
	            imgName.add(fileName);
	            
	        } catch (IOException ex) {
	            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
	        }
	}

	public void saveIconofCategory(MultipartFile[] files, CategoryModel category) {
		String fileName = StringUtils.cleanPath(generateFileNameForCategory(category.getCatCode()));
		 try {
	            // Check if the file's name contains invalid characters
	            if(fileName.contains("..")) {
	                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
	            }
	            for(MultipartFile file : files){
	            	Path targetLocation = this.fileStorageLocation.resolve(fileName);
		            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		            category.setCatIcon(fileName);
				}
	            // Copy file to the target location (Replacing existing file with the same name)
	          categoryRepository.save(category);
	        } catch (IOException ex) {
	            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
	        }
	}

	public void saveImageofUser(MultipartFile[] files, User user) {
		String fileName = StringUtils.cleanPath(generateFileNameForUser(user.getId()));
		 try {
	            // Check if the file's name contains invalid characters
	            if(fileName.contains("..")) {
	                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
	            }
	            for(MultipartFile file : files){
	            	Path targetLocation = this.fileStorageLocation.resolve(fileName);
		            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		            user.setImageUrl(fileName);
				}
	            // Copy file to the target location (Replacing existing file with the same name)
	            userRepository.save(user);
	        } catch (IOException ex) {
	            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
	        }
		
	}


}
