package com.cognixia.models;

public class ComedyGroup {

    private int groupId;
    private String name;
    private String description;
    private String imageUrl;

    // Constructor
    public ComedyGroup(int groupId, String name, String description, String imageUrl) {
        this.groupId = groupId;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    // Getter methods
    public int getGroupId() {
        return groupId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    // Setter methods
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "ComedyGroup [groupId=" + groupId + ", name=" + name + ", description=" + description + ", imageUrl=" + imageUrl + "]";
    }
}