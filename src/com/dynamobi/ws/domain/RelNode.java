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
