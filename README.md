# 🚀EcoFood Connect

## 📘 **Project Overview**
EcoFood Connect is a **Java Swing-based software platform** designed to tackle the issue of food wastage and hunger in the United States. With over **133 billion pounds of food wasted annually**, the platform aims to streamline the process of donating surplus food from restaurants to food banks, managing the logistics of food donations, and ensuring the sustainable disposal of non-edible food waste.

This ecosystem facilitates the collaboration between four key entities — **Restaurants**, **Food Banks**, **Logistics Providers**, and **Waste Management Firms** — to create an efficient process for redistributing surplus food to those in need while minimizing environmental impact.

---

## 🔍 **Problem Statement**
- The U.S. generates significant food waste, contributing to greenhouse gas emissions and hunger.
- Restaurants, being one of the largest food producers, lack a unified platform to donate surplus food efficiently.
- There is a disconnect between stakeholders (restaurants, food banks, logistics, waste management) in managing food surplus and waste.

---

## 💡 **Solution**
EcoFood Connect provides an end-to-end platform connecting restaurants, food banks, logistics companies, and waste management firms. The system allows:

- **Easy coordination** between donors (restaurants) and recipients (food banks).
- **Efficient management** of logistics for food delivery.
- **Sustainable disposal** of non-edible food waste.

### 🔑 **Key Features**
- **Coordination**: Seamless communication between all stakeholders.
- **Efficiency**: Simplifies the donation process and waste management.
- **Impact Tracking**: Reports metrics such as food donated, waste reduced, and carbon emissions prevented.

---

## 🌐 **Ecosystem Components**
1. **Network**: Centralized communication system for all stakeholders.
2. **Enterprises**: 
   - **Restaurants**: Initiate donation requests for surplus food.
   - **Food Banks**: Receive and redistribute donated food.
   - **Logistics Providers**: Transport food donations.
   - **Waste Management Firms**: Process non-edible food waste.
3. **Organizations**:
   - **Donation Management**
   - **Logistics and Delivery**
   - **Quality Assurance**
   - **Waste Processing**
   - **Reporting and Analytics**
4. **Roles**:
   - **System Admin**
   - **Enterprise Admin**
   - **Restaurant Manager**
   - **Logistics Coordinator**
   - **Food Bank Manager**
   - **Quality Inspector**
   - **Waste Management Operator**
   - **End Users** (Shelter Recipients)

---

## 📋 **Use Cases**

### **1️⃣ Food Donation Request**
1. A restaurant creates a donation request with food type, quantity, and pickup time.
2. The Food Bank Manager approves or rejects the request.
3. Logistics assigns a driver for pickup, and the food is delivered to a shelter.
4. The request is tagged as "Completed" in the system.

---

### **2️⃣ Cross-Enterprise Collaboration**
1. Multiple food chains contribute surplus to a mega food bank.
2. The admin monitors cross-enterprise collaboration, and logistics handles multi-point pickups.

---

### **3️⃣ Waste Treatment**
1. Restaurants report non-edible waste.
2. Waste management operators process and update the system with environmental impact data.

---

### **4️⃣ Reporting**
1. The System Administrator generates reports detailing:
   - Total food donations
   - Carbon emissions reduced
   - Cross-enterprise collaboration

---

## 🎯 **Deliverables**
1. **A working Java Swing application** that implements the EcoFood Connect ecosystem.
2. **A presentation** outlining the design, implementation, and problem-solving approach.
3. **Role-based authentication** with username and password capabilities.
4. **Reporting module** providing a summarized view of system performance.
5. **Configuration module** with test cases to validate the system's correctness.

---

## ✨ **Features Implemented**

- **Network Management**: Facilitates communication among all stakeholders.
- **Enterprise CRUD Operations**: Allows creation, reading, updating, and deletion of enterprise records.
- **Role-based Access Control**: Each role (e.g., System Admin, Restaurant Manager) has distinct permissions.
- **Donation Request Management**: Streamlines the process of food donation.
- **Multi-Enterprise Collaboration**: Facilitates cross-enterprise food donations.
- **Waste Treatment Management**: Manages the sustainable processing of non-edible waste.
- **Reporting & Analytics**: Generates reports on donations and environmental impact.

---

## 🛠️ **Technical Details**
- **Technology Stack**: Java Swing, Faker module (for random data generation).
- **Libraries**: 
  - **Swing** for GUI development
  - **Faker** for test data generation.
  
---

## 🚀 **Installation Instructions**

1. **Clone the repository**:
    ```bash
    git clone https://github.com/bhavyalikhitha7/AED_Final_Project.git
    ```

2. **Compile and run the Java Swing application**:
    ```bash
    javac EcoFoodConnect.java
    java EcoFoodConnect
    ```

---

## 🔮 **Future Enhancements**
- **External API Integrations**: Live data (e.g., weather data for logistics planning).
- **Notifications**: SMS and email notifications for donation requests and status updates.
- **Geographic Mapping**: Route optimization for logistics.
- **Data Visualization**: Interactive dashboards for reporting metrics.
  
---
