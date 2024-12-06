package com.ecofoodconnect.models;

import java.util.ArrayList;

/**
 *
 * @author tanmay
 */
public class FoodBankManager extends EnterprisePerson {
    private ArrayList<String> linkedRestaurants; // List of restaurant IDs linked to the manager

    public FoodBankManager(String id, String name, String username, String password, ArrayList<String> linkedRestaurants) {
        super(id, name, username, password, "FoodBankManager", "FoodBanks"); // Role and enterprise type are fixed
        this.linkedRestaurants = linkedRestaurants;
    }

    // Getters and Setters
    public ArrayList<String> getLinkedRestaurants() {
        return linkedRestaurants;
    }

    public void setLinkedRestaurants(ArrayList<String> linkedRestaurants) {
        this.linkedRestaurants = linkedRestaurants;
    }

    // Add a restaurant to the linked list
    public void addLinkedRestaurant(String restaurantId) {
        if (!linkedRestaurants.contains(restaurantId)) {
            linkedRestaurants.add(restaurantId);
        }
    }

    // Remove a restaurant from the linked list
    public void removeLinkedRestaurant(String restaurantId) {
        linkedRestaurants.remove(restaurantId);
    }

    // Check if a restaurant is linked
    public boolean isRestaurantLinked(String restaurantId) {
        return linkedRestaurants.contains(restaurantId);
    }
}