package com.cognixia.dao;


import java.util.List;
import com.cognixia.models.ComedyGroup;

public interface ComedyGroupDao {
    public List<ComedyGroup> getAllComedyGroups();
    public ComedyGroup getComedyGroupById(int groupId);
}
