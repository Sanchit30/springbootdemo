package com.anaadih.aclassdeal.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CommonResponseSender {

	public CommonResponseSender() {
		
	}
	
	/**
	 * @param object
	 * @param response
	 * @return response format map
	 */
	public static Map<String, Object> createdSuccessResponse(Object object, HttpServletResponse response) {
		response.setStatus(HttpStatus.CREATED.value());
		return setResponse(HttpStatus.CREATED.value(), true, "Record successfully created", object);
	}
	

	/**
	 * @param object
	 * @param response
	 * @return response format map
	 */
	public static Map<String, Object> updatedSuccessResponse(Object object, HttpServletResponse response) {
		response.setStatus(HttpStatus.OK.value());
		return setResponse(HttpStatus.OK.value(), true, "Record successfully updated", object);
	}
	
	
	/**
	 * @param object
	 * @param response
	 * @return response format map
	 */
	public static Map<String, Object> getRecordSuccessResponse(Object object, HttpServletResponse response) {
		response.setStatus(HttpStatus.OK.value());
		return setResponse(HttpStatus.OK.value(), true, "Record successfully fetched", object);
	}

	
	/**
	 * @param object
	 * @param response
	 * @return response format map
	 */
	public static Map<String, Object> errorResponse(Object object, HttpServletResponse response) {
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		return setResponse(HttpStatus.BAD_REQUEST.value(), false, HttpStatus.BAD_REQUEST.getReasonPhrase(), object);
	}
	
	
	/**
	 * @param object
	 * @param response
	 * @return response format map
	 */
	public static Map<String, Object> recordDeleteSuccessResponse(Object object, HttpServletResponse response) {
		response.setStatus(HttpStatus.OK.value());
		return setResponse(HttpStatus.OK.value(), true, "Record successfully Deleted", object);
	}
	
	private static Map<String, Object> setResponse(int status, boolean isSuccess, String message, Object object) {
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("status", status);
		responseMap.put("isSuccess", isSuccess);
		responseMap.put("message", message);
		if (isSuccess) {
			responseMap.put("data", object);
		} else {
			responseMap.put("error", object);
		}
		return responseMap;
	}
}
