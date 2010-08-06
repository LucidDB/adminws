/*
Dynamo Web Services is a web service project for administering LucidDB
Copyright (C) 2010 Dynamo Business Intelligence Corporation

This program is free software; you can redistribute it and/or modify it
under the terms of the GNU General Public License as published by the Free
Software Foundation; either version 2 of the License, or (at your option)
any later version approved by Dynamo Business Intelligence Corporation.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/
package com.dynamobi.ws.domain;

/**
 * vo for explain plan.
 * @author Ray Zhang
 * @since Mar 23, 2010
 */

public class ShowPlanEntity
{
    private String stmtText;
    
    private int stmtId;

    private String physicalOp;

    private String logicalOp;

    private String estimateRows;

    private String totalSubtreeCost;

    public String getEstimateRows()
    {
        return estimateRows;
    }

    public void setEstimateRows(String estimateRows)
    {
        this.estimateRows = estimateRows;
    }

    public String getLogicalOp()
    {
        return logicalOp;
    }

    public void setLogicalOp(String logicalOp)
    {
        this.logicalOp = logicalOp;
    }

    public String getPhysicalOp()
    {
        return physicalOp;
    }

    public void setPhysicalOp(String physicalOp)
    {
        this.physicalOp = physicalOp;
    }

    public int getStmtId()
    {
        return stmtId;
    }

    public void setStmtId(int stmtId)
    {
        this.stmtId = stmtId;
    }

    public String getStmtText()
    {
        return stmtText;
    }

    public void setStmtText(String stmtText)
    {
        this.stmtText = stmtText;
    }

    public String getTotalSubtreeCost()
    {
        return totalSubtreeCost;
    }

    public void setTotalSubtreeCost(String totalSubtreeCost)
    {
        this.totalSubtreeCost = totalSubtreeCost;
    }

    public String toString(){
        
        StringBuffer ret = new StringBuffer();
        ret.append("StmtText: " + stmtText)
        .append("\n")
        .append("stmtId: " + stmtId)
        .append("\n")
        .append("physicalOp/logicalOp: " + logicalOp)
        .append("\n")
        .append("estimateRows: " +estimateRows)
        .append("\n")
        .append("totalSubtreeCost: " +totalSubtreeCost)
        .append("\n");
        
        return ret.toString();

    }
}
