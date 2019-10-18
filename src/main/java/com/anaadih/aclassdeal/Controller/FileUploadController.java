package com.anaadih.aclassdeal.Controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.anaadih.aclassdeal.Model.ProductImageMapping;
import com.anaadih.aclassdeal.Service.FileUploadService;
import com.anaadih.aclassdeal.util.CommonResponseSender;





@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class FileUploadController {

	
	@Autowired
	private FileUploadService fileUploadService;
	
	   @PostMapping("/uploadFile")
	    public Map<String, Object> uploadFile(@RequestParam("file") MultipartFile file,HttpServletResponse response) {
	    	System.out.println("inside upload file"+file);
		   ProductImageMapping uploadFileResponse = fileUploadService.storeFile(file);
	        final Map<String, Object> map = new HashMap<>();
	        System.out.println("uploadFileResponsee"+uploadFileResponse);
	        final Date startDate = new Date();
			map.put("uploadFile", uploadFileResponse);
			
			return CommonResponseSender.createdSuccessResponse(map, response);
	    }
	   
	   
	    @PostMapping("/uploadMultipleFiles")
	    public Map<String, Object> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,HttpServletResponse response) {
	    	
	    	List<ProductImageMapping> uploadMultipleFiles = fileUploadService.storeFiles(files);  	
	    	final Map<String, Object> map = new HashMap<>();
	        final Date startDate = new Date();
			map.put("uploadMultipleFiles", uploadMultipleFiles);
			
			return CommonResponseSender.createdSuccessResponse(map, response);
	    }
	    
	    @GetMapping("/downloadFile/{fileName:.+}")
	    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
	        // Load file as Resource
	        Resource resource = fileUploadService.loadFileAsResource(fileName);

	        // Try to determine file's content type
	        String contentType = null;
	        try {
	            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
	        } catch (IOException ex) {
	            
	        }

	        // Fallback to the default content type if type could not be determined
	        if(contentType == null) {
	            contentType = "application/octet-stream";
	        }

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	                .body(resource);
	    }
	   
}
