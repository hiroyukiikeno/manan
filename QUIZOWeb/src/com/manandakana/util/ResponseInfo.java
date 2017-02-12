package com.manandakana.util;

import java.io.Serializable;

public class ResponseInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String message ="";
	private String status ="";
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String toJSONString(){
		StringBuffer sb = new StringBuffer("");
		sb.append("{\"status\":\"").append(status);
		sb.append("\",\"message\":\"").append(message);
		sb.append("\"}");
		return sb.toString();
	}

}
