# University Examination System

## 📑 Table of Contents

* [Project Overview](#-project-overview)
* [Technology Stack](#-technology-stack)
* [Environment Setup](#-environment-setup)
* [Features](#-features)
* [User Roles](#-user-roles)
* [Running the Application](#-running-the-application)
* [System Components](#-system-components)
* [OOP Implementation](#-oop-implementation)
* [User Guide](#-user-guide)
* [Future Improvements](#-future-improvements)
* [License](#-license)
* [Authors](#-authors)

---

## 🎯 Project Overview

The **University Examination System** is a comprehensive Java-based system designed to manage university examination workflows. This system provides end-to-end management of academic operations from student enrollment to result processing, developed as a group project using Object-Oriented Programming principles.

---

## 🛠️ Technology Stack

* **Java** - Core programming language
* **Object-Oriented Programming** - Encapsulation, Inheritance, Polymorphism, Abstraction
* **Standard Java Libraries** - Collections, I/O, Utilities

---

## ⚙️ Environment Setup

### Prerequisites

Make sure the following are installed:

* Java JDK 8 or higher
* Java-compatible IDE (IntelliJ, Eclipse, NetBeans) or command line

### Installation Steps

1. Clone or download the project
2. Open project in your preferred Java IDE
3. Ensure JDK is properly configured

```bash
git clone https://github.com/eunicecwn/university-exam-system-y1s3.git
cd university-exam-system-y1s3
```

---

## ✨ Features

### 👥 User Management
* **Multi-role System** - Separate modules for Students, Faculty Members, and Administrators
* **Automated Credentials** - System-generated IDs and passwords
* **Profile Management** - Personal and academic information tracking

### 🏫 Academic Structure
* **Faculty Management** - Create and organize academic departments
* **Course Administration** - Operations for courses and programs
* **Subject Management** - Subject creation with credit hour system
* **Curriculum Design** - Subject allocation to courses

### 📅 Examination Management
* **Exam Scheduling** - Exam timetabling with conflict detection
* **Venue Allocation** - Capacity-based room assignment
* **Timetable Generation** - Schedule creation for students and faculty
* **Exam Registration** - Student enrollment process

### 📊 Academic Results
* **Result Processing** - Faculty-based mark entry system
* **Grade Calculation** - Automated grading with GPA computation
* **Performance Tracking** - Course-specific and cumulative CGPA
* **Result Management** - Activation/deactivation of results

---

## 👤 User Roles

### Administrators
* Manage institutional structure (faculties, departments)
* Oversee user accounts and system configuration
* Generate institutional reports
* Monitor system-wide academic operations

### Faculty Members
* Enter and manage student results
* View assigned courses and teaching schedules
* Access examination timetables and venues
* Track student academic performance

### Students
* View personal academic profiles
* Check enrolled courses and subjects
* Access examination schedules and venues
* View academic results and GPA information

---

## 🚀 Running the Application

### Execution Steps

1. **Using IDE**:
   * Open the project in your Java IDE
   * Compile and run the Main class
   * Navigate through the menu-driven interface

2. **Using Command Line**:
   ```bash
   javac *.java
   java Main
   ```

3. **Access different modules** based on user role through the interactive menu system

---

## 🔧 System Components

* **Automated ID Generation** - Systematic ID creation for all entities
* **Credit Management** - Credit hour calculation and tracking
* **Conflict Prevention** - Duplicate entry detection and validation
* **Grade Automation** - Grade calculation based on marks
* **CGPA Computation** - GPA and CGPA calculation
* **Data Validation** - Error handling and input validation

---

## 🏗️ OOP Implementation

The system implements core Object-Oriented Programming principles:

* **Encapsulation** - Data hiding with proper access modifiers
* **Inheritance** - Class hierarchies for users and academic entities
* **Polymorphism** - Method overriding and dynamic method dispatch
* **Abstraction** - Abstract classes and interfaces for system architecture

---

## 📖 User Guide

### Navigation
* Use the **menu-driven interface** to access different modules
* Follow **role-based access** controls
* Utilize **input validation** for data entry

### Key Operations
* **Student Registration** - Automated ID generation and profile setup
* **Course Management** - Faculty and subject allocation
* **Exam Scheduling** - Conflict-free timetable generation
* **Result Processing** - Mark entry and grade calculation

---

## 🚀 Future Improvements

* **Database Integration** - Persistent data storage
* **Web Interface** - Browser-based access
* **Mobile Application** - Student and faculty mobile access
* **Advanced Analytics** - Performance trends and predictive analysis
* **Automated Notifications** - Email and SMS alerts

---

## 📄 License

This project is created for **academic purposes**.  
Not intended for commercial distribution.

---

## 👥 Authors

* Developed as a group project for university coursework
* Using Object-Oriented Programming principles and Java best practices

---

*Group project developed for Object-Oriented Programming coursework*
