/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.services;

import com.ecofoodconnect.models.DonationRequest;
import com.ecofoodconnect.models.DonationRequestDirectory;
import com.ecofoodconnect.models.EnterprisePerson;
import com.ecofoodconnect.models.LogisticsRequest;
import com.ecofoodconnect.models.LogisticsRequestDirectory;
import com.ecofoodconnect.models.Person;
import com.ecofoodconnect.models.PersonDirectory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 *
 * @author tanmay
 */
public class AuthService {
    private static final PersonDirectory personDirectory = new PersonDirectory();
    private static Person currentUser;
    private static final DonationRequestDirectory donationRequestDirectory = new DonationRequestDirectory();
    
    private static LogisticsRequestDirectory logisticsRequestDirectory;
    
    private static final Map<String, ArrayList<String>> pendingTasks = new HashMap<>();
    private static final Map<String, ArrayList<String>> completedOperations = new HashMap<>();

    static {
        // Pre-populate with demo data
        // System Admins
        personDirectory.addPerson(new Person("1", "Admin One", "sysadmin1", "password123", "SystemAdmin"));
        personDirectory.addPerson(new Person("2", "Admin Two", "sysadmin2", "password123", "SystemAdmin"));
        personDirectory.addPerson(new Person("3", "Admin Three", "sysadmin3", "password123", "SystemAdmin"));
        
        // Restaurants
        personDirectory.addPerson(new EnterprisePerson("4", "Restaurant Admin", "restadmin1", "password123", "EnterpriseAdmin", "Restaurants"));
        personDirectory.addPerson(new EnterprisePerson("5", "Restaurant Manager One", "restmgr1", "password123", "RestaurantManager", "Restaurants"));
        personDirectory.addPerson(new EnterprisePerson("6", "Restaurant Manager Two", "restmgr2", "password123", "RestaurantManager", "Restaurants"));
        personDirectory.addPerson(new EnterprisePerson("7", "Restaurant Manager Three", "restmgr3", "password123", "RestaurantManager", "Restaurants"));

        // Food Banks
        personDirectory.addPerson(new EnterprisePerson("8", "Food Bank Admin", "fbadmin1", "password123", "EnterpriseAdmin", "FoodBanks"));
        personDirectory.addPerson(new EnterprisePerson("9", "Food Bank Manager One", "fbmgr1", "password123", "FoodBankManager", "FoodBanks"));
        personDirectory.addPerson(new EnterprisePerson("10", "Food Bank Manager Two", "fbmgr2", "password123", "FoodBankManager", "FoodBanks"));
        personDirectory.addPerson(new EnterprisePerson("11", "Food Bank Manager Three", "fbmgr3", "password123", "FoodBankManager", "FoodBanks"));
        
        // Logistics Providers
        personDirectory.addPerson(new EnterprisePerson("12", "Logistics Admin", "logadmin1", "password123", "EnterpriseAdmin", "LogisticsProviders"));
        personDirectory.addPerson(new EnterprisePerson("13", "Logistics Coordinator One", "logcoord1", "password123", "LogisticsCoordinator", "LogisticsProviders"));
        personDirectory.addPerson(new EnterprisePerson("14", "Logistics Coordinator Two", "logcoord2", "password123", "LogisticsCoordinator", "LogisticsProviders"));
        personDirectory.addPerson(new EnterprisePerson("15", "Logistics Coordinator Three", "logcoord3", "password123", "LogisticsCoordinator", "LogisticsProviders"));

        // Waste Management Firms
        personDirectory.addPerson(new EnterprisePerson("16", "Waste Management Admin", "wmadmin1", "password123", "EnterpriseAdmin", "WasteManagementFirms"));
        personDirectory.addPerson(new EnterprisePerson("17", "Waste Manager One", "wastemgr1", "password123", "WasteManagementOperator", "WasteManagementFirms"));
        personDirectory.addPerson(new EnterprisePerson("18", "Waste Manager Two", "wastemgr2", "password123", "WasteManagementOperator", "WasteManagementFirms"));
        personDirectory.addPerson(new EnterprisePerson("19", "Waste Manager Three", "wastemgr3", "password123", "WasteManagementOperator", "WasteManagementFirms"));
        
        // Quality Inspectors
        personDirectory.addPerson(new Person("20", "Quality Inspector One", "qualinsp1", "password123", "QualityInspector"));
        personDirectory.addPerson(new Person("21", "Quality Inspector Two", "qualinsp2", "password123", "QualityInspector"));
        personDirectory.addPerson(new Person("22", "Quality Inspector Three", "qualinsp3", "password123", "QualityInspector"));
        

        // End Users
        personDirectory.addPerson(new Person("23", "End User One", "enduser1", "password123", "EndUser"));
        personDirectory.addPerson(new Person("24", "End User Two", "enduser2", "password123", "EndUser"));
        personDirectory.addPerson(new Person("25", "End User Three", "enduser3", "password123", "EndUser"));

        // Add Pending Tasks
        pendingTasks.put("Restaurants", new ArrayList<>(List.of(
                "Donation request for 50 meals",
                "Urgent surplus redistribution request",
                "Request for pickup scheduling")));
        pendingTasks.put("FoodBanks", new ArrayList<>(List.of(
                "Request for redistribution approval",
                "Pending stock verification")));
        pendingTasks.put("LogisticsProviders", new ArrayList<>(List.of(
                "Assign driver for route A",
                "Schedule pickup from Restaurant X")));
        pendingTasks.put("WasteManagementFirms", new ArrayList<>(List.of(
                "Disposal request for 100 kg of waste",
                "Schedule non-edible waste processing")));

        // Add Completed Operations
        completedOperations.put("Restaurants", new ArrayList<>(List.of(
                "50 meals donated to Food Bank A",
                "Urgent surplus delivered to Shelter B")));
        completedOperations.put("FoodBanks", new ArrayList<>(List.of(
                "Redistribution of 200 meals completed",
                "Excess stock delivered to Shelter C")));
        completedOperations.put("LogisticsProviders", new ArrayList<>(List.of(
                "Route A delivery completed",
                "Pickup from Restaurant Y completed")));
        completedOperations.put("WasteManagementFirms", new ArrayList<>(List.of(
                "100 kg waste processed sustainably",
                "Non-edible waste disposed of")));
        
        populateDonationsDemoData();
        
        
        logisticsRequestDirectory = new LogisticsRequestDirectory();

        // Add demo data: 20 logistics requests
        logisticsRequestDirectory.addRequest(new LogisticsRequest("REQ001", "Driver A", "12/01/2024", "10:00 AM", "Scheduled"));
        logisticsRequestDirectory.addRequest(new LogisticsRequest("REQ002", "Driver B", "12/02/2024", "02:00 PM", "In Transit"));
        logisticsRequestDirectory.addRequest(new LogisticsRequest("REQ003", "Driver C", "12/03/2024", "09:30 AM", "Completed"));
        logisticsRequestDirectory.addRequest(new LogisticsRequest("REQ004", "Driver A", "12/04/2024", "01:00 PM", "Scheduled"));
        logisticsRequestDirectory.addRequest(new LogisticsRequest("REQ005", "Driver B", "12/05/2024", "03:45 PM", "Scheduled"));
        logisticsRequestDirectory.addRequest(new LogisticsRequest("REQ006", "Driver C", "12/06/2024", "11:00 AM", "Scheduled"));
        logisticsRequestDirectory.addRequest(new LogisticsRequest("REQ007", "Driver A", "12/07/2024", "08:00 AM", "In Transit"));
        logisticsRequestDirectory.addRequest(new LogisticsRequest("REQ008", "Driver B", "12/08/2024", "12:15 PM", "Scheduled"));
        logisticsRequestDirectory.addRequest(new LogisticsRequest("REQ009", "Driver C", "12/09/2024", "04:00 PM", "Scheduled"));
        logisticsRequestDirectory.addRequest(new LogisticsRequest("REQ010", "Driver A", "12/10/2024", "05:30 PM", "Completed"));
        logisticsRequestDirectory.addRequest(new LogisticsRequest("REQ011", "Driver B", "12/11/2024", "10:45 AM", "Scheduled"));
        logisticsRequestDirectory.addRequest(new LogisticsRequest("REQ012", "Driver C", "12/12/2024", "01:30 PM", "In Transit"));
        logisticsRequestDirectory.addRequest(new LogisticsRequest("REQ013", "Driver A", "12/13/2024", "03:00 PM", "Scheduled"));
        logisticsRequestDirectory.addRequest(new LogisticsRequest("REQ014", "Driver B", "12/14/2024", "07:00 AM", "Completed"));
        logisticsRequestDirectory.addRequest(new LogisticsRequest("REQ015", "Driver C", "12/15/2024", "02:15 PM", "Scheduled"));
        logisticsRequestDirectory.addRequest(new LogisticsRequest("REQ016", "Driver A", "12/16/2024", "09:30 AM", "Scheduled"));
        logisticsRequestDirectory.addRequest(new LogisticsRequest("REQ017", "Driver B", "12/17/2024", "11:45 AM", "Scheduled"));
        logisticsRequestDirectory.addRequest(new LogisticsRequest("REQ018", "Driver C", "12/18/2024", "10:00 AM", "In Transit"));
        logisticsRequestDirectory.addRequest(new LogisticsRequest("REQ019", "Driver A", "12/19/2024", "03:15 PM", "Scheduled"));
        logisticsRequestDirectory.addRequest(new LogisticsRequest("REQ020", "Driver B", "12/20/2024", "04:30 PM", "Scheduled"));

    }
    
    public static LogisticsRequestDirectory getLogisticsRequestDirectory() {
        return logisticsRequestDirectory;
    }
    
    private static void populateDonationsDemoData() {
        String[] foodTypes = {"Perishable", "Non-Perishable", "Canned", "Frozen", "Fresh Produce", "Beverages"};
        String[] statuses = {"Pending", "Approved", "Rejected"};
        String[] sampleNotes = {"Urgent donation", "Expires soon", "High quality", "Excess stock", "Local produce"};
        String[] demoUsers = {"restmgr1", "restmgr2", "restmgr3"}; // Demo usernames

        Random random = new Random();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        for (int i = 0; i < 50; i++) {
            String id = UUID.randomUUID().toString();
            String foodType = foodTypes[random.nextInt(foodTypes.length)];
            double quantity = 10 + (50 * random.nextDouble());
            Date expiryDate = new Date(System.currentTimeMillis() + (random.nextInt(10) + 1) * 86400000L);
            String expiryDateString = dateFormat.format(expiryDate);
            String notes = sampleNotes[random.nextInt(sampleNotes.length)];
            String status = statuses[random.nextInt(statuses.length)];
            String createdBy = demoUsers[random.nextInt(demoUsers.length)]; // Randomly assign to demo users

            DonationRequest request = new DonationRequest(id, foodType, quantity, expiryDateString, notes, status, createdBy);
            donationRequestDirectory.addDonationRequest(request);

            // Debug print
            System.out.println("Added Request: ID=" + id + ", CreatedBy=" + createdBy);
        }
        }
    
    public static Person getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Person user) {
        currentUser = user;
    }
    
    public static String getEnterpriseTypeOfCurrentUser() {
        if (currentUser instanceof EnterprisePerson) {
            return ((EnterprisePerson) currentUser).getEnterpriseType();
        }
        return null; // Non-enterprise users won't have an enterprise type
    }
    
    public static DonationRequestDirectory getDonationRequestDirectory() {
        return donationRequestDirectory;
    }

    // Authenticate user by username and password
    public static Person authenticate(String username, String password) {
        Person user = personDirectory.authenticate(username, password);
        if (user != null) {
            setCurrentUser(user); // Track logged-in user
        }
        return user;
    }

    // Register a new user
    public static boolean register(Person person) {
        person.setId(UUID.randomUUID().toString()); // Assign a unique ID to the person
        // Check if username is already taken
        for (Person existing : personDirectory.getAllPersons()) {
            if (existing.getUsername().equals(person.getUsername())) {
                return false; // Username already exists
            }
        }
        personDirectory.addPerson(person);
        return true;
    }
    
    // Get a user by ID
    public static Person getUserById(String id) {
        for (Person person : personDirectory.getAllPersons()) {
            if (person.getId().equals(id)) {
                return person;
            }
        }
        return null;
    }

    // Delete a user by ID
    public static void deleteUser(String id) {
        personDirectory.removePersonById(id);
    }

    // Get all users
    public static ArrayList<Person> getAllUsers() {
        return personDirectory.getAllPersons();
    }
    
    public static ArrayList<Person> getUsersByEnterpriseType(String enterpriseType) {
        ArrayList<Person> users = new ArrayList<>();
        for (Person person : personDirectory.getAllPersons()) {
            if (person instanceof EnterprisePerson) {
                EnterprisePerson enterpriseUser = (EnterprisePerson) person;
                if (enterpriseUser.getEnterpriseType().equalsIgnoreCase(enterpriseType)) {
                    users.add(enterpriseUser);
                }
            }
        }
        return users;
    }

    // Generate system report
    public static String generateSystemReport() {
        ArrayList<Person> allUsers = personDirectory.getAllPersons();
        Map<String, Integer> roleDistribution = new HashMap<>();

        for (Person user : allUsers) {
            roleDistribution.put(user.getRole(), roleDistribution.getOrDefault(user.getRole(), 0) + 1);
        }

        StringBuilder report = new StringBuilder("System Report\n");
        report.append("====================\n");
        report.append("Total Users: ").append(allUsers.size()).append("\n");
        report.append("Role Distribution:\n");

        for (Map.Entry<String, Integer> entry : roleDistribution.entrySet()) {
            report.append("- ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        return report.toString();
    }

    // Simulated active user count
    public static int getActiveUserCount() {
        return (int) (Math.random() * 100); // Simulate active user count
    }

    // Simulated role distribution
    public static String getRoleDistribution() {
        ArrayList<Person> allUsers = personDirectory.getAllPersons();
        Map<String, Integer> roleCounts = new HashMap<>();
        for (Person user : allUsers) {
            roleCounts.put(user.getRole(), roleCounts.getOrDefault(user.getRole(), 0) + 1);
        }

        StringBuilder distribution = new StringBuilder();
        for (Map.Entry<String, Integer> entry : roleCounts.entrySet()) {
            distribution.append(entry.getKey()).append(": ").append(entry.getValue()).append(", ");
        }
        return distribution.length() > 0 ? distribution.substring(0, distribution.length() - 2) : "No roles available";
    }
    
    public static Map<String, Integer> getRoleCounts() {
        Map<String, Integer> roleCounts = new HashMap<>();
        ArrayList<Person> users = personDirectory.getAllPersons();
        for (Person user : users) {
            String role = user.getRole();
            roleCounts.put(role, roleCounts.getOrDefault(role, 0) + 1);
        }
        return roleCounts;
    }

    // Get the most active role (simulated)
    public static String getMostActiveRole() {
        Map<String, Integer> roleCounts = getRoleCounts();
        String mostActiveRole = "";
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : roleCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostActiveRole = entry.getKey();
            }
        }
        return mostActiveRole;
    }

    // Get login trends (simulated)
    public static String getLoginTrends() {
        return "Mon: 45\nTue: 50\nWed: 60\nThu: 70\nFri: 65\nSat: 40\nSun: 30";
    }

    // Simulate CPU usage percentage
    public static int getCpuUsage() {
        return (int) (Math.random() * 100); // Replace with actual system metrics if available
    }

    // Simulate memory usage percentage
    public static int getMemoryUsage() {
        return (int) (Math.random() * 100); // Replace with actual system metrics if available
    }

    // Simulated system logs
    public static String getSystemLogs() {
        return "1. Admin logged in at 09:00 AM\n" +
               "2. Report generated at 09:15 AM\n" +
               "3. New user added at 10:00 AM\n" +
               "4. System health check passed at 10:30 AM";
    }
    
    public static ArrayList<String> getPendingTasks(String enterpriseType) {
        return pendingTasks.getOrDefault(enterpriseType, new ArrayList<>());
    }

    public static ArrayList<String> getCompletedOperations(String enterpriseType) {
        return completedOperations.getOrDefault(enterpriseType, new ArrayList<>());
    }
}
