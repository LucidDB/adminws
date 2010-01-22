package com.dynamobi.util;

/**
 * Create a customized exception
 * 
 * @author Ray Zhang
 * @since Jan-12-2010
 */

public class AppException extends Exception {

	private static final long serialVersionUID = 1L;

	public AppException() {

		super();

	}

	public AppException(String msg) {

		super(msg);

	}
}
