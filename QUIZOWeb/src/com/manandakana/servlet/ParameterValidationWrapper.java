package com.manandakana.servlet;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.validation.ValidationException;

import com.manandakana.util.EncodeHelper;
import com.manandakana.util.ParameterProperties;

public class ParameterValidationWrapper extends HttpServletRequestWrapper {

	public ParameterValidationWrapper(HttpServletRequest request) {
		super(request);
	}
	
	@Override
	public String getParameter(String name){
		HttpServletRequest request = (HttpServletRequest)super.getRequest();
		if(request.getParameter(name) != null){
			return validate(name, request.getParameter(name));
		}
		return null;
	}
	
	private String validate(String name, String input){
		switch (getFieldType(name)){
		case "int":
			try {
				Integer.parseInt(input);
			} catch (NumberFormatException e){
				throw new ValidationException("improper format for field " + name);
			}
			return input;
		case "Timestamp":
			try {
				Long.parseLong(input);
			} catch (NumberFormatException e){
				throw new ValidationException("improper format for field " + name);
			}
		case "String":
			String encodedInput = htmlEncode(input);
			if(input != null){
				if(getFieldLength(name) < encodedInput.length()){
					if(isTruncatable(name)){
						return encodedInput.substring(0, getFieldLength(name));
					} else {
						throw new ValidationException("improper length for field " + name);
					}
				} else {
					return encodedInput;
				}
			}
		default:
			return input;
		}
		
			
		
	}
	
	private String getFieldType(String name){
		return ParameterProperties.typeOf(name);
	}
	
	private int getFieldLength(String name){
		return ParameterProperties.lengthOf(name);
	}
	
	private boolean isTruncatable(String name){
		return ParameterProperties.isTruncatable(name);
	}
	
	private String htmlEncode(String input){
		EncodeHelper helper = new EncodeHelper();
		return helper.htmlEncode(input);
	}

}
