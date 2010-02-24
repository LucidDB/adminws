package com.dynamobi.ws.api;

import javax.jws.WebService;

/**
 * WebService interface for FlexSQLAdmin
 * @author Ray Zhang
 * @since Feb 04,2010
 * 
 */
@WebService
public interface FlexSQLAdmin
{
    /**
     * 
     * @param connection
     * @return
     */
    public String getDBMetaData(String connection);
    /**
     * exec SQL
     * @param connection
     * @param sqlquerytype
     * @param sql
     * @param toomany
     * @return 
     */
    public String execSQL(String connection, String sqlquerytype, String sql, int toomany);
    
    /**
     * update data
     * @param connection
     * @param testsql
     * @param sql
     * @param toomany
     * @return
     */
    public String handleUpdate(String connection, String testsql, String sql, int toomany);
}
