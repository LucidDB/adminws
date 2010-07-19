package com.dynamobi.ws.util;

import java.sql.SQLException;
import javax.sql.DataSource;
import java.lang.ClassNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

// Base class
import org.springframework.security.userdetails.jdbc.JdbcDaoImpl;

// Factory class to construct our pooled datasource
import com.mchange.v2.c3p0.DataSources;

import com.dynamobi.ws.util.DBAccess;

public class DBDao extends JdbcDaoImpl {

  private DataSource ds_pooled = null;

  public DBDao() throws ClassNotFoundException, SQLException, IOException {
    super();

    Properties pro = new Properties();

    InputStream user_props = this.getClass().getResourceAsStream("/luciddb-jdbc.properties");
    if (user_props != null) {
      pro.load(user_props);
    } else {
      pro.load(this.getClass().getResourceAsStream("/luciddb-jdbc-default.properties"));
    }

    Class.forName(pro.getProperty("jdbc.driver"));

    String username = pro.getProperty("jdbc.username");
    String password = pro.getProperty("jdbc.password");
    String url      = pro.getProperty("jdbc.url");

    DataSource ds_unpooled = DataSources.unpooledDataSource(
        url,
        username,
        password);

    Map<String,String> overrides = new HashMap<String,String>();
    overrides.put("minPoolSize", "3");
    ds_pooled = DataSources.pooledDataSource(ds_unpooled, overrides);

    setDataSource(ds_pooled);
    DBAccess.connDataSource = ds_pooled;
  }

  public void cleanup() throws SQLException {
    DataSources.destroy(ds_pooled);
  }

}
