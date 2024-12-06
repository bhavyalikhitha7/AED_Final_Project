/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.models;

/**
 *
 * @author tanmay
 */
public class LogisticsRequest {
    private String requestId;
    private String driver;
    private String pickupDate;
    private String pickupTime;
    private String status;

    public LogisticsRequest(String requestId, String driver, String pickupDate, String pickupTime, String status) {
        this.requestId = requestId;
        this.driver = driver;
        this.pickupDate = pickupDate;
        this.pickupTime = pickupTime;
        this.status = status;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
