/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.models;

import java.util.ArrayList;
import java.util.Optional;
/**
 *
 * @author tanmay
 */

public class DonationRequestDirectory {
    private ArrayList<DonationRequest> donationRequests;

    public DonationRequestDirectory() {
        this.donationRequests = new ArrayList<>();
    }

    // Get all donation requests
    public ArrayList<DonationRequest> getDonationRequests() {
        return donationRequests;
    }

    // Add a new donation request
    public void addDonationRequest(DonationRequest request) {
        donationRequests.add(request);
    }

    // Remove a donation request by ID
    public void removeDonationRequest(String id) {
        donationRequests.removeIf(request -> request.getId().equals(id));
    }

    // Update an existing donation request
    public void updateDonationRequest(DonationRequest updatedRequest) {
        Optional<DonationRequest> existingRequest = donationRequests.stream()
                .filter(request -> request.getId().equals(updatedRequest.getId()))
                .findFirst();

        existingRequest.ifPresent(request -> {
            request.setFoodType(updatedRequest.getFoodType());
            request.setQuantity(updatedRequest.getQuantity());
            request.setExpiryDate(updatedRequest.getExpiryDate());
            request.setNotes(updatedRequest.getNotes());
            request.setStatus(updatedRequest.getStatus());
            request.setRejectionReason(updatedRequest.getRejectionReason());
            request.setDriver(updatedRequest.getDriver());
            request.setPickupDate(updatedRequest.getPickupDate());
            request.setPickupTime(updatedRequest.getPickupTime());
            request.setPickupLocation(updatedRequest.getPickupLocation());
            request.setRequestedBy(updatedRequest.getRequestedBy());
            request.setFreshness(updatedRequest.getFreshness());
            request.setTemperature(updatedRequest.getTemperature());
            request.setPackagingQuality(updatedRequest.getPackagingQuality());
            request.setOdor(updatedRequest.getOdor());
            request.setLabelingAccuracy(updatedRequest.getLabelingAccuracy());
        });
    }

    // Fetch requests by status
    public ArrayList<DonationRequest> getRequestsByStatus(String status) {
        ArrayList<DonationRequest> filteredRequests = new ArrayList<>();
        for (DonationRequest request : donationRequests) {
            if (request.getStatus().equalsIgnoreCase(status)) {
                filteredRequests.add(request);
            }
        }
        return filteredRequests;
    }

    // Fetch requests by creator
    public ArrayList<DonationRequest> getRequestsByCreator(String createdBy) {
        ArrayList<DonationRequest> filteredRequests = new ArrayList<>();
        for (DonationRequest request : donationRequests) {
            if (request.getCreatedBy().equalsIgnoreCase(createdBy)) {
                filteredRequests.add(request);
            }
        }
        return filteredRequests;
    }
}