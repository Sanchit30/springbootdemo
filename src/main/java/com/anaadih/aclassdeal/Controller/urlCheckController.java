package com.anaadih.aclassdeal.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@CrossOrigin(origins="*")
public class urlCheckController {
	
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String insertProduct() {
		return "Hello world";
	
	}

}
