package com.example.x_change.utility;

import java.util.ArrayList;

public class Profile {
    public String profileId, profileName, location, contact;
    public int successfulSwaps = 0;
    public ArrayList<String> reviewIds = new ArrayList<>(),
            sellingItemIds = new ArrayList<>(),
            bookmarkIds = new ArrayList<>(),
            chatsIds = new ArrayList<>();

    public Profile(String profileId, String location, String contact, String name) {
        this.profileId = profileId;
        this.location = location;
        this.contact = contact;
        this.profileName = name;
    }

    public Profile() {}
}
