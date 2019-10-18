package com.anaadih.aclassdeal.util;


import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

	@Component
	  public class CustomException extends Exception
	  {
	  
	  private static final long serialVersionUID = 6798031143363755641L; private
	  static final String MESSAGE = "message";
	  
	  public CustomException(String message) { super(message); }
	  
	  public CustomException() {
	  
	  }
	  
	  public CustomException(HttpStatusCodeException e) throws CustomException,
	  JSONException { 
		  getErrorMessage(e.getResponseBodyAsString(), e.getMessage());
	  }
	  
	  private void getErrorMessage(String responseBody, String errorMessage) throws
	  CustomException, JSONException {
	  JSONObject responseError = new JSONObject(responseBody);
	  if (responseError.has(MESSAGE) &&
	  responseError.getString(MESSAGE) != null) {
		  errorMessage = responseError.getString(MESSAGE);
		  }
	  throw new CustomException(errorMessage);
	  }
	  }

