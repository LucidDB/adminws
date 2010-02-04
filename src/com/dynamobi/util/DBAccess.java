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
import com.dynamobi.domain.ColumnStats;
import com.dynamobi.domain.Counter;
import com.dynamobi.domain.SystemParameter;
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
                en.name = rs.getString(c++);
                en.schema = rs.getString(c++);
                en.catalog = rs.getString(c++);
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

            StringBuffer sb = new StringBuffer();
            sb.append("select table_name, schema_name,catalog_name, lineage_id from sys_root.dba_tables where ");
            sb.append(" TABLE_TYPE = 'LOCAL TABLE'");
            sb.append(" AND schema_name = ?");
            
            ps = conn.prepareStatement(sb.toString());
            ps.setString(1, schemaName);
            rs = ps.executeQuery();

            while (rs.next()) {

                Table en = new Table();
                int c = 1;
                en.name = rs.getString(c++);
                en.schema = rs.getString(c++);
                en.catalog = rs.getString(c++);
                en.uuid = rs.getString(c++);
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
    
    public static SystemParameter findSystemParameterByName(String paramName) throws AppException{
        
        
        SystemParameter retVal = new SystemParameter();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            conn = getConnenction();

            ps = conn.prepareStatement("select param_name, param_value from sys_root.dba_system_parameters where param_name = ?");
            ps.setString(1, paramName);
            rs = ps.executeQuery();

            while (rs.next()) {

                retVal.setParamName(rs.getString(1));
                retVal.setParamValue(rs.getString(2));
                break;

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
    
    
    public static List<SystemParameter> getAllSystemParameters() throws AppException{
        
        
        List<SystemParameter> retVal = new ArrayList<SystemParameter>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            conn = getConnenction();

            ps = conn.prepareStatement("select param_name, param_value from sys_root.dba_system_parameters");
            rs = ps.executeQuery();

            while (rs.next()) {
                
                SystemParameter en = new SystemParameter();
                en.setParamName(rs.getString(1));
                en.setParamValue(rs.getString(2));
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

    
    
    public static boolean updateSystemParameter(String paramName, String paramValue) throws AppException{
        
        
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;

        try {

            conn = getConnenction();
            String sql = "alter system set \""+ paramName +"\" = '"+paramValue+"'";
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            ps.execute();
            
            retVal = true;

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
    
   public static List<Counter> getAllPerformanceCounters() throws AppException{
        
        
        List<Counter> retVal = new ArrayList<Counter>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            conn = getConnenction();

            ps = conn.prepareStatement("select source_name, counter_name, counter_units, counter_value  from SYS_ROOT.DBA_PERFORMANCE_COUNTERS");
            rs = ps.executeQuery();

            while (rs.next()) {
                
                Counter en = new Counter();
                en.setSourceName(rs.getString(1));
                en.setCounterName(rs.getString(2));
                en.setCounterUnits(rs.getString(3));
                en.setCounterValue(rs.getString(4));
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
   
   public static Counter findPerformanceCounterByName(String counterName) throws AppException{
       
       
       Counter retVal = new Counter();

       Connection conn = null;
       PreparedStatement ps = null;
       ResultSet rs = null;

       try {

           conn = getConnenction();

           ps = conn.prepareStatement("select source_name, counter_name, counter_units, counter_value  from SYS_ROOT.DBA_PERFORMANCE_COUNTERS where counter_name = ?");
           ps.setString(1, counterName);
           rs = ps.executeQuery();

           while (rs.next()) {
               
               retVal.setSourceName(rs.getString(1));
               retVal.setCounterName(rs.getString(2));
               retVal.setCounterUnits(rs.getString(3));
               retVal.setCounterValue(rs.getString(4));
               break;
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
   
   public static List<ColumnStats> getAllColumnStats() throws AppException{
       
       
       List<ColumnStats> retVal = new ArrayList<ColumnStats>();

       Connection conn = null;
       PreparedStatement ps = null;
       ResultSet rs = null;

       try {

           conn = getConnenction();

           ps = conn.prepareStatement("select catalog_name, schema_name, table_name, column_name, distinct_value_count, is_distinct_value_count_estimated, percent_sampled, sample_size from sys_root.dba_column_stats");
           rs = ps.executeQuery();

           while (rs.next()) {
               
               ColumnStats en = new ColumnStats();
               en.setCatalogName(rs.getString(1));
               en.setSchemaName(rs.getString(2));
               en.setTableName(rs.getString(3));
               en.setColumnName(rs.getString(4));
               en.setDistinctValueCount(rs.getLong(5));
               en.setDistinctValueCountEstimated(rs.getBoolean(6));
               en.setPercentSampled(rs.getDouble(7));
               en.setSampleSize(rs.getLong(8));
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
   

   
   public static List<ColumnStats> findColumnStats(String catalogName, String schemaName, String tableName, String columnName) throws AppException{
       
       
       List<ColumnStats> retVal = new ArrayList<ColumnStats>();

       Connection conn = null;
       PreparedStatement ps = null;
       ResultSet rs = null;

       try {

           conn = getConnenction();
           
           StringBuffer sql = new StringBuffer();
           
           List<String> myConditions =  new ArrayList<String>();
           sql.append("select catalog_name, schema_name, table_name, column_name, distinct_value_count, is_distinct_value_count_estimated, percent_sampled, sample_size from sys_root.dba_column_stats where ");
           if(catalogName!=null && !catalogName.isEmpty()){
               myConditions.add("catalog_name = '" + catalogName + "'");
           }
           
           if(schemaName!=null && !schemaName.isEmpty()){
               myConditions.add("schema_name = '" + schemaName + "'");
           }
           
           if(tableName!=null && !tableName.isEmpty()){
               myConditions.add("table_name = '" + tableName + "'");
           }
           
           if(columnName!=null && !columnName.isEmpty()){
               myConditions.add("column_name = '" + columnName + "'");
           }
           
           if(myConditions.size() > 1){
               
               int size = myConditions.size();
               
               for(int i = 0; i< size;i++){
                   
                   if((size - i)!= 1 )
                       sql.append(myConditions.get(i)).append(" and ");
                   else
                       sql.append(myConditions.get(i));
               }
           }
//           System.out.println(sql.toString());
           ps = conn.prepareStatement(sql.toString());
           rs = ps.executeQuery();

           while (rs.next()) {
               
               ColumnStats en = new ColumnStats();
               en.setCatalogName(rs.getString(1));
               en.setSchemaName(rs.getString(2));
               en.setTableName(rs.getString(3));
               en.setColumnName(rs.getString(4));
               en.setDistinctValueCount(rs.getLong(5));
               en.setDistinctValueCountEstimated(rs.getBoolean(6));
               en.setPercentSampled(rs.getDouble(7));
               en.setSampleSize(rs.getLong(8));
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

                System.out.println(te.catalog + "."
                    + te.schema + "." + te.name);
            }
            
            SystemParameter en = DBAccess.findSystemParameterByName("javaCompilerClassName");
            DBAccess.updateSystemParameter("fennelDisabled", "false");
            System.out.println(en.getParamName()+":"+en.getParamValue());
            
            Counter co = DBAccess.findPerformanceCounterByName("JvmMemoryUnused");
            
            System.out.println(co.getSourceName()+":"+co.getCounterName()+":"+co.getCounterUnits()+":"+co.getCounterValue());
            
//            List<ColumnStats> cols = DBAccess.getAllColumnStats();
//            
//            for (ColumnStats te : cols) {
//
//                System.out.println(te.getCatalogName() + "."
//                    + te.getSchemaName() + "." 
//                    + te.getTableName() + "."
//                    + te.getColumnName() + "."
//                    + te.getDistinctValueCount() + "."
//                    + te.isDistinctValueCountEstimated() + "."
//                    + te.getPercentSampled() + "."
//                    + te.getSampleSize()+ "."
//                    );
//            }
//            
            List<ColumnStats> cols = DBAccess.findColumnStats("LOCALDB", "RAY", "", "NAME");
            
            for (ColumnStats te : cols) {

                System.out.println(te.getCatalogName() + "."
                    + te.getSchemaName() + "." 
                    + te.getTableName() + "."
                    + te.getColumnName() + "."
                    + te.getDistinctValueCount() + "."
                    + te.isDistinctValueCountEstimated() + "."
                    + te.getPercentSampled() + "."
                    + te.getSampleSize()+ "."
                    );
            }
            
        } catch (AppException e) {
           
            e.printStackTrace();
        }

    }

}
