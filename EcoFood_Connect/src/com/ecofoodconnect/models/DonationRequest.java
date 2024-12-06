/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.models;
/**
 *
 * @author tanmay
 */

public class DonationRequest {
    private String id;
    private String foodType;
    private double quantity;
    private String expiryDate;
    private String notes;
    private String status;
    private String createdBy; // Creator of the request
    private String driver; // Assigned driver
    private String pickupDate; // Scheduled pickup date
    private String pickupTime; // Scheduled pickup time
    private String pickupLocation; // Pickup location
    private String rejectionReason; // Reason for rejection
    private String requestedBy; // End user who requested the donation

    // Quality Metrics
    private int freshness;
    private double temperature;
    private int packagingQuality;
    private int odor;
    private int labelingAccuracy;

    public DonationRequest(String id, String foodType, double quantity, String expiryDate, String notes, String status, String createdBy) {
        this.id = id;
        this.foodType = foodType;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.notes = notes;
        this.status = status;
        this.createdBy = createdBy;
        this.rejectionReason = "";
    }

    // Getters and Setters for core fields
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFoodType() { return foodType; }
    public void setFoodType(String foodType) { this.foodType = foodType; }

    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }

    public String getExpiryDate() { return expiryDate; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public String getRequestedBy() { return requestedBy; }
    public void setRequestedBy(String requestedBy) { this.requestedBy = requestedBy; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }

    // Getters and Setters for Quality Metrics
    public int getFreshness() { return freshness; }
    public void setFreshness(int freshness) { this.freshness = freshness; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public int getPackagingQuality() { return packagingQuality; }
    public void setPackagingQuality(int packagingQuality) { this.packagingQuality = packagingQuality; }

    public int getOdor() { return odor; }
    public void setOdor(int odor) { this.odor = odor; }

    public int getLabelingAccuracy() { return labelingAccuracy; }
    public void setLabelingAccuracy(int labelingAccuracy) { this.labelingAccuracy = labelingAccuracy; }

    // Getters and Setters for Pickup Details
    public String getDriver() { return driver; }
    public void setDriver(String driver) { this.driver = driver; }

    public String getPickupDate() { return pickupDate; }
    public void setPickupDate(String pickupDate) { this.pickupDate = pickupDate; }

    public String getPickupTime() { return pickupTime; }
    public void setPickupTime(String pickupTime) { this.pickupTime = pickupTime; }

    public String getPickupLocation() { return pickupLocation; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }
}