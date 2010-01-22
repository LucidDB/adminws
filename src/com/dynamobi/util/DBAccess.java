package com.dynamobi.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.dynamobi.domain.Column;
import com.dynamobi.domain.Table;

/**
 * Get Tables' info from database
 * 
 * @author Ray Zhang
 * @since Jan-12-2010
 */
public class DBAccess
{

    private DBAccess()
    {

    }

    public static Connection getConnenction()
        throws ClassNotFoundException, SQLException, FileNotFoundException,
        IOException
    {

        Properties pro = new Properties();
        pro.load(DBAccess.class.getResourceAsStream("/jdbc.properties"));
        Class.forName(pro.getProperty("jdbc.driver"));
        Connection conn = DriverManager.getConnection(
            pro.getProperty("jdbc.url"),
            pro.getProperty("jdbc.username"),
            pro.getProperty("jdbc.password"));

        return conn;

    }

    public static List<Table> getTableInfo()
        throws AppException
    {

        List<Table> retVal = new ArrayList<Table>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            conn = getConnenction();

            ps = conn.prepareStatement("select table_name, schema_name,catalog_name from sys_root.dba_tables");
            rs = ps.executeQuery();

            while (rs.next()) {

                Table en = new Table();
                int c = 1;
                en.setTableName(rs.getString(c++));
                en.setSchemaName(rs.getString(c++));
                en.setCatalogName(rs.getString(c++));
                retVal.add(en);

            }

        } catch (ClassNotFoundException e) {

            e.printStackTrace();
            throw new AppException("Error Info: Not found JDBC Driver Class!");

        } catch (SQLException e) {

            e.printStackTrace();
            throw new AppException(
                "Error Info: The connection was bad or Execute sql statment failed!");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
            throw new AppException("Error Info: Not found jdbc.properties!");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new AppException(
                "Error Info: failed to parse jdbc.properties!");
        } finally {

            try {

                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException ex) {

                throw new AppException("Error Info: Release db resouce failed");

            }

        }

        return retVal;

    }

    public static List<Table> getTableInfo(String schemaName)
        throws AppException
    {

        List<Table> retVal = new ArrayList<Table>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            conn = getConnenction();

            ps = conn.prepareStatement("select table_name, schema_name,catalog_name from sys_root.dba_tables where schema_name=?");
            ps.setString(1, schemaName);
            rs = ps.executeQuery();

            while (rs.next()) {

                Table en = new Table();
                int c = 1;
                en.setTableName(rs.getString(c++));
                en.setSchemaName(rs.getString(c++));
                en.setCatalogName(rs.getString(c++));
                retVal.add(en);

            }

        } catch (ClassNotFoundException e) {

            e.printStackTrace();
            throw new AppException("Error Info: Not found JDBC Driver Class!");

        } catch (SQLException e) {

            e.printStackTrace();
            throw new AppException(
                "Error Info: The connection was bad or Execute sql statment failed!");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
            throw new AppException("Error Info: Not found jdbc.properties!");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new AppException(
                "Error Info: failed to parse jdbc.properties!");
        } finally {

            try {

                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException ex) {

                throw new AppException("Error Info: Release db resouce failed");

            }

        }

        return retVal;

    }

    public static List<Column> getColumnNamesFromTable(
        String tableName,
        String schemaName)
        throws AppException
    {

        List<Column> retVal = new ArrayList<Column>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            conn = getConnenction();

            ps = conn.prepareStatement("select column_name from sys_root.dba_columns where table_name =? and schema_name=?");
            ps.setString(1, tableName);
            ps.setString(2, schemaName);
            rs = ps.executeQuery();

            while (rs.next()) {

                Column en = new Column();
                en.setColumnName(rs.getString(1));
                retVal.add(en);

            }

        } catch (ClassNotFoundException e) {

            e.printStackTrace();
            throw new AppException("Error Info: Not found JDBC driver!");
        } catch (SQLException e) {

            e.printStackTrace();
            throw new AppException(
                "Error Info: The connection was bad or Execute sql statment failed!");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
            throw new AppException("Error Info: Not found jdbc.properties!");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new AppException(
                "Error Info: failed to parse jdbc.properties!");
        } finally {

            try {

                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException ex) {

                throw new AppException("Error Info: Release db resouce failed");

            }

        }

        return retVal;

    }

    public static void main(String[] args)
    {

        try {
            List<Table> list = DBAccess.getTableInfo("RAY");
            for (Table te : list) {

                System.out.println(te.getCatalogName() + "."
                    + te.getSchemaName() + "." + te.getTableName());
            }
        } catch (AppException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
