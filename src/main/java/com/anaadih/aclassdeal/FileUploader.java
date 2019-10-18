package com.anaadih.aclassdeal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Date;

//import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author paras
 *
 */

@Component
public class FileUploader {
String directoryPath="C:\\ACLASSDEAL\\";
    
	public static boolean uploadFile(MultipartFile file, String path, String fileName, String userId){
		 //String fileName = StringUtils.cleanPath(generateFileName());
		File dest = new File(path);
	        try {
	            // Check if the file's name contains invalid characters
	            if(fileName.contains("..")) {
	               // throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
	            }
	            
	            // Copy file to the target location (Replacing existing file with the same name)
        
	            Files.copy(file.getInputStream(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
	            System.out.println("FILE COPIED");
	          //  return new UploadFileResponse(fileName, generateFileUri(fileName),file.getContentType(), file.getSize());
	        } catch (IOException ex) {
	          //  throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
	        }
			return false;
	}
	
	public String getFilePath(String imgName,String userName) {
		String mdoHomeDir = System.getProperty("");
		userName = (userName!=null && !userName.equalsIgnoreCase("null") ?File.separator+userName:"");
		imgName = (imgName!=null && !imgName.equalsIgnoreCase("null") ?File.separator+imgName:"");

		String path = mdoHomeDir+File.separator+"Attachements"+userName+imgName;
		 
		return path; 
	}
	
	 private String generateFileName() {
	        return String.valueOf(new Date().getTime());
	    }

}
