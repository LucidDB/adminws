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
    busy_session = false;
	}
	
	private Connection sessionConnection;
  private boolean busy_session;

	public synchronized Connection getSessionConnection() {
    while (busy_session) {
      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        // request could have been terminated, ignore.
        e.printStackTrace();
      }
    }
    busy_session = true;
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

  public void releaseSessionConnection() {
    busy_session = false;
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
