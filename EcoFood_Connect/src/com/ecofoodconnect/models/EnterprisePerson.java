/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.models;

/**
 *
 * @author tanmay
 */
public class EnterprisePerson extends Person {
    private String enterpriseType;

    public EnterprisePerson(String id, String name, String username, String password, String role, String enterpriseType) {
        super(id, name, username, password, role);
        this.enterpriseType = enterpriseType;
    }

    public String getEnterpriseType() {
        return enterpriseType;
    }

    public void setEnterpriseType(String enterpriseType) {
        this.enterpriseType = enterpriseType;
    }
}
