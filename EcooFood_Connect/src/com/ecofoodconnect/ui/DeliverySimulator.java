/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui;

import com.ecofoodconnect.models.DonationRequest;
import com.ecofoodconnect.models.DonationRequestDirectory;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author tanmay
 */

public class DeliverySimulator {
    private DonationRequestDirectory directory;

    public DeliverySimulator(DonationRequestDirectory directory) {
        this.directory = directory;
    }

    public void startSimulation() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ArrayList<DonationRequest> requests = directory.getDonationRequests();
                for (DonationRequest request : requests) {
                    if ("Requested".equalsIgnoreCase(request.getStatus())) {
                        request.setStatus("Delivered");
                        System.out.println("Request ID: " + request.getId() + " marked as Delivered.");
                    }
                }
            }
        }, 5000, 10000); // Simulate delivery every 10 seconds
    }
}