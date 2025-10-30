# ✈️ Airline Reservation System – AWT & MySQL Based Admin Dashboard  

## 📝 Project Overview  
The **Airline Reservation System** is a desktop-based Java application built using **AWT (Abstract Window Toolkit)** and **MySQL**.  
It provides a simple and efficient platform for managing airline bookings and reservations through an intuitive **Admin Dashboard**.  
All operations are connected to a MySQL database using **JDBC** for real-time data management.

---

## ⚙️ Features
- 🆕 **Add New Reservation** – Register new passenger flight bookings.  
- 📋 **View All Reservations** – Display all existing flight records.  
- ✏️ **Update Reservation** – Modify existing booking details.  
- ❌ **Delete Reservation** – Remove canceled or invalid reservations.  
- 🔒 **Logout / Exit** – Securely close the admin session.  

---

## 🧩 Technologies Used
- **Frontend:** Java AWT  
- **Backend:** MySQL Database  
- **Connector:** JDBC (MySQL Connector JAR)  
- **Output:** Executable `.jar` file  
- **IDE Used:** NetBeans / Eclipse / IntelliJ IDEA  

---

## 🗄️ Database Configuration
1. Install and run **MySQL Server**.  
2. Create a database named `airline_db`.  
3. Import the required tables (flights, reservations, passengers, etc.).  
4. Update the database connection in your Java code:  
   ```java
   Connection con = DriverManager.getConnection(
       "jdbc:mysql://localhost:3306/airline_db", "root", "YourPassword"
   );

## Compile and run the main file

To compile :
javac -cp ".;mysql-connector-j-9.4.0.jar" *.java

To run :
java -cp ".;mysql-connector-j-9.4.0.jar" AirlineReservationSystem
