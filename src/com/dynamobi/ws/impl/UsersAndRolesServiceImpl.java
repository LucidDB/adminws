package com.dynamobi.ws.impl;

import java.util.List;
import java.util.ArrayList;

import javax.jws.WebService;
import javax.ws.rs.Path;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    List<UserDetails> details = new ArrayList<UserDetails>();
    try {
      ResultSet rs = DBAccess.rawResultExec("SELECT * FROM sys_root.dba_users");
      while (rs.next()) {
        UserDetails ud = new UserDetails();
        ud.name = rs.getString(1);
        ud.password = rs.getString(2);
        ud.creation_timestamp = rs.getString(3);
        ud.modification_timestamp = rs.getString(4);
        details.add(ud);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return details;
  }

  public List<SessionInfo> getCurrentSessions() throws AppException {
    return DBAccess.getCurrentSessions();
  }

  private enum Action { NEW, MOD, DEL };
  private String userAction(String user, String password, Action act)
                 throws AppException {
    // TODO: rewrite this to use prepared statements
    String query = "";
    if (act == Action.NEW) {
      query = "CREATE USER " + user + " IDENTIFIED BY '" + password + "'";
    } else if (act == Action.MOD) {
      query = "CREATE OR REPLACE USER " + user + " IDENTIFIED BY '" + password + "'";
    } else if (act == Action.DEL) {
      query = "DROP USER " + user;
    }

    String retval = "";
    try {
      ResultSet rs = DBAccess.rawResultExec(query);
    } catch (SQLException e) {
      e.printStackTrace();
      retval = e.getMessage();
    }
    return retval;
  }

  public String addNewUser(String user, String password) throws AppException {
    return userAction(user, password, Action.NEW);
  }

  // TODO: also adjust the roles when modifying and deleting.
  public String modifyUser(String user, String password) throws AppException {
    return userAction(user, password, Action.MOD);
  }

  public String deleteUser(String user) throws AppException {
    return userAction(user, "", Action.DEL);
  }

  public boolean modifyUsers(List<UserDetails> details) throws AppException {
    return false;
  }

}
