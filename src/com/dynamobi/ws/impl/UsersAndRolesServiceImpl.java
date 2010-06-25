package com.dynamobi.ws.impl;

import java.util.List;
import java.util.ArrayList;

import javax.jws.WebService;
import javax.ws.rs.Path;

import com.dynamobi.ws.api.UsersAndRolesService;
import com.dynamobi.ws.domain.UserDetails;
import com.dynamobi.ws.domain.SessionInfo;
import com.dynamobi.ws.util.AppException;
import com.dynamobi.ws.util.DBAccess;

/**
 * UsersAndRolesService implementation
 * 
 * @author Kevin Secretan
 * @since June 24, 2010
 */
@WebService(endpointInterface = "com.dynamobi.ws.api.UsersAndRolesService")
@Path("/users")
public class UsersAndRolesServiceImpl implements UsersAndRolesService {

  public List<UserDetails> getUsersDetails() throws AppException {
    return new ArrayList<UserDetails>();
  }

  public SessionInfo getCurrentSessions() throws AppException {
    return new SessionInfo(); // necessary?
  }

  public boolean addNewUser(String user, String password) throws AppException {
    return false;
  }

  public boolean deleteUser(String user) throws AppException {
    return false;
  }

  public boolean modifyUsers(List<UserDetails> details) throws AppException {
    return false;
  }

}
