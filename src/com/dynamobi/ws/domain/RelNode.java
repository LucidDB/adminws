package com.dynamobi.ws.domain;

import java.util.List;

/**
 * vo for explain plan.
 * @author Ray Zhang
 * @since Mar 23, 2010
 */

public class RelNode
{
    private int currentId;
    
    private int parentId;
    
    private int level;
    
    private int numOfSpaces;
    
    private List<Integer> childrenIds;
    
    private ShowPlanEntity showPlanEntity;

    public List<Integer> getChildrenIds()
    {
        return childrenIds;
    }

    public void setChildrenIds(List<Integer> childrenIds)
    {
        this.childrenIds = childrenIds;
    }

    public int getCurrentId()
    {
        return currentId;
    }

    public void setCurrentId(int currentId)
    {
        this.currentId = currentId;
    }

    public int getParentId()
    {
        return parentId;
    }

    public void setParentId(int parentId)
    {
        this.parentId = parentId;
    }

    public int getNumOfSpaces()
    {
        return numOfSpaces;
    }

    public void setNumOfSpaces(int numOfSpaces)
    {
        this.numOfSpaces = numOfSpaces;
    }

    public ShowPlanEntity getShowPlanEntity()
    {
        return showPlanEntity;
    }

    public void setShowPlanEntity(ShowPlanEntity showPlanEntity)
    {
        this.showPlanEntity = showPlanEntity;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }
}
