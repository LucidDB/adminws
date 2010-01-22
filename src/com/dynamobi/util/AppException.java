package com.dynamobi.util;

import javax.xml.ws.WebFault;

/**
 * Create a customized exception
 * 
 * @author Ray Zhang
 * @since Jan-12-2010
 */
@WebFault
public class AppException extends Exception {

	private static final long serialVersionUID = 1L;

	public AppException() {

		super();

	}

	public AppException(String msg) {

		super(msg);

	}
}
