package com.cognixia.service;

import com.cognixia.dao.*;
import com.cognixia.daoClass.*;
import java.util.List;
import java.util.Scanner;

public class ComedyGroupService {
    private ComedyGroupDaoClass comedyGroupDao;
    private UserTrackedGroupDaoClass userTrackedGroupDao;
    private UserTrackedGroupService userTrackedGroupService;

    public ComedyGroupService() {
        this.comedyGroupDao = new ComedyGroupDaoClass();
        this.userTrackedGroupDao = new UserTrackedGroupDao(); // Initialize
    }

    public void viewAllComedyGroups(Scanner scanner, int loggedInUserId) {
        List<ComedyGroup> comedyGroups = comedyGroupDao.getAllComedyGroups();
        System.out.println("Available Comedy Groups:");
        for (ComedyGroup comedyGroup : comedyGroups) {
            System.out.println(comedyGroup.getGroupId() + ". " + comedyGroup.getName());
        }

        System.out.print("Enter the number of the group you'd like to view details or '0' to go back: ");
        int groupId = scanner.nextInt();

        if (groupId == 0) {
            return;
        }

        viewComedyGroupDetails(groupId, scanner, loggedInUserId);
    }

    public void viewComedyGroupDetails(int groupId, Scanner scanner, int loggedInUserId) {
        ComedyGroupDaoClass comedyGroup = comedyGroupDao.getComedyGroupById(groupId);
        if (comedyGroup != null) {
            System.out.println("Group Name: " + comedyGroup.getName());
            System.out.println("Description: " + comedyGroup.getDescription());
            System.out.println("Image URL: " + comedyGroup.getImageUrl());

            UserTrackedGroup trackedGroup = userTrackedGroupDao.getUserTrackedGroup(loggedInUserId, groupId);
            if (trackedGroup == null) {
                System.out.print("Would you like to follow this group? (yes/no): ");
                String followChoice = scanner.next();
                if (followChoice.equalsIgnoreCase("yes")) {
                    userTrackedGroupService.trackGroup(loggedInUserId, groupId, "Want to Watch");
                }
            } else {
                // Display available sketches logic can go here
                System.out.println("You are already following this group.");
            }
        } else {
            System.out.println("Comedy Group not found.");
        }
    }
}