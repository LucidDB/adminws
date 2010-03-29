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
