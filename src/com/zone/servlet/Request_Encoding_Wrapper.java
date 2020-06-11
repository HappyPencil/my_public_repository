package com.zone.servlet;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class Request_Encoding_Wrapper extends HttpServletRequestWrapper {

	HttpServletRequest request;
	
	public Request_Encoding_Wrapper(HttpServletRequest request) {
		super(request);
		this.request = request;
	}
	public String getParameter(String name){
		String value = request.getParameter(name);
		
		try {
			value = new String(value.getBytes(request.getCharacterEncoding()), "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage()+"字符编码不支持");
		}
		
		return value;
	}

}
