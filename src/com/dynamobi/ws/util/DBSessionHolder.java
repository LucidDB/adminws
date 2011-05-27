package com.dynamobi.ws.util;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.UserCredentialsDataSourceAdapter;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class DBSessionHolder {
	
	public DBSessionHolder() {
		
	}
	
	private Connection sessionConnection;

	public synchronized Connection getSessionConnection() {
		if ( sessionConnection == null ) {
			try {
				init();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sessionConnection;
	}

	public void setSessionConnection(Connection sessionConnection) {
		this.sessionConnection = sessionConnection;
	}

	public void init() throws Exception {
		  System.out.println("Session Init");
		  
		  final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		  WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		  
		  UserCredentialsDataSourceAdapter ds = (UserCredentialsDataSourceAdapter) wac.getBean("myDataSource");
		  ds.setUsername(auth.getName());
		  
		  sessionConnection = ds.getConnection();
		  
		}
	 
		public void cleanup() throws Exception {
		  System.out.println("Session Cleanup");
		  
		  if ( sessionConnection != null ) {
			  sessionConnection.close();
		  }
		  
		}
		


}
