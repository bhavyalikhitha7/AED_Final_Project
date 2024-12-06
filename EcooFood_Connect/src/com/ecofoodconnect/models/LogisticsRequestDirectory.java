/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.models;

import java.util.ArrayList;

/**
 *
 * @author tanmay
 */

public class LogisticsRequestDirectory {
    private ArrayList<LogisticsRequest> logisticsRequests;

    public LogisticsRequestDirectory() {
        this.logisticsRequests = new ArrayList<>();
    }

    public void addRequest(LogisticsRequest request) {
        logisticsRequests.add(request);
    }

    public ArrayList<LogisticsRequest> getRequests() {
        return logisticsRequests;
    }

    public LogisticsRequest getRequestById(String requestId) {
        for (LogisticsRequest request : logisticsRequests) {
            if (request.getRequestId().equals(requestId)) {
                return request;
            }
        }
        return null;
    }

    public void updateRequestStatus(String requestId, String newStatus) {
        LogisticsRequest request = getRequestById(requestId);
        if (request != null) {
            request.setStatus(newStatus);
        }
    }
}
