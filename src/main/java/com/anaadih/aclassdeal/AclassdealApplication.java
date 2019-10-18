package com.anaadih.aclassdeal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.anaadih.aclassdeal.config.AppProperties;
import com.anaadih.aclassdeal.property.FileUploadProperties;



@SpringBootApplication
@EnableConfigurationProperties({
	FileUploadProperties.class,AppProperties.class
})
public class AclassdealApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AclassdealApplication.class, args);
	}

}
