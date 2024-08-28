package com.cognixia.daoClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cognixia.dao.ComedyGroupDao;
import com.cognixia.models.ComedyGroup;
import com.cognixia.utils.DatabaseConnection;

public class ComedyGroupDaoClass implements ComedyGroupDao {

    @Override
    public List<ComedyGroup> getAllComedyGroups() {
        List<ComedyGroup> comedyGroups = new ArrayList<>();
        String query = "SELECT group_id, name, description, image_url FROM ComedyGroups";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                ComedyGroup comedyGroup = new ComedyGroup(
                    rs.getInt("group_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("image_url")
                );
                comedyGroups.add(comedyGroup);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comedyGroups;
    }
    

    @Override
    public ComedyGroup getComedyGroupById(int groupId) {
        String query = "SELECT group_id, name, description, image_url FROM ComedyGroups WHERE group_id = ?";
        ComedyGroup comedyGroup = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, groupId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                comedyGroup = new ComedyGroup(
                    rs.getInt("group_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("image_url")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comedyGroup;
    }
}
