# Hotel Management System (Core Java)

## Project Overview
This is a **console-based Hotel Management System** developed using **Core Java, OOP, Collections, and Serialization**.  
The project simulates **real-world hotel operations** like room booking, customer management, food ordering, and billing.  

The main focus is to demonstrate **object-oriented programming, modular design, exception handling, and data persistence**, bridging the gap between theoretical Java concepts and real-world application development.

---

## Key Features
- **Room Booking:** Book rooms dynamically with real-time availability checks.  
- **Customer Management:** Store and manage customer details like name and contact number.  
- **Food Ordering:** Customers can order multiple food items with dynamic bill calculation.  
- **Billing:** Automatically calculates total bill combining room and food charges.  
- **Data Persistence:** Bookings and orders are serialized, ensuring data is saved and restored on restart.  
- **Exception Handling:** Custom exceptions prevent invalid input and maintain robustness.  
- **Modular Design:** Follows OOP principles with separate classes for Room, Booking, Customer, Food, and Data Storage.

---

## Technologies Used
- **Programming Language:** Java (Core Java)  
- **Concepts Applied:** Object-Oriented Programming (OOP), Inheritance, Encapsulation, Abstraction  
- **Collections:** `ArrayList`, `HashMap`  
- **File Handling:** Java Serialization (`ObjectOutputStream`, `ObjectInputStream`)  
- **Exception Handling:** Custom Exceptions

---

## Project Structure
- **Room:** Abstract class with `LuxuryRoom` and `DeluxeRoom` subclasses.  
- **Customer:** Stores customer information.  
- **Booking:** Links room and customer, tracks food orders.  
- **Food:** Stores ordered food details with price and quantity.  
- **DataStore:** Handles serialization and deserialization of bookings.  
- **HotelManagementSystem:** Main controller with menu-driven system.

---
