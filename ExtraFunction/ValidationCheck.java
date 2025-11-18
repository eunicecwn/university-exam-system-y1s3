package ExtraFunction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import UserController.Admin;
import UserController.FacultyMember;
import UserController.Student;

public class ValidationCheck {

    // Validate the name and return true if valid, false otherwise
    public boolean validationName(String value) {
        if (value.matches("[a-zA-Z ]+")) { // Only alphabets and spaces allowed
            if (value.length() <= 50) { // Name length cannot exceed 50 characters
                return true; // Name is valid
            } else {
                System.out.println("Invalid input. Name length cannot exceed 50 characters.");
            }
        } else {
            System.out.println("Invalid input. Please enter alphabetic characters only.");
        }
        return false; // Name is invalid
    }

    // Validate the intake year
    public boolean validationIntakeYear(int year) {
        int currentYear = Year.now().getValue(); // Dynamically get the current year

        // Check if the year is exactly 4 digits and not in the future
        if (year >= 1000 && year <= 9999) { // Ensure it's a 4-digit year
            if (year <= currentYear && year >= 2000) { // Ensure it's not in the future
                return true; // Year is valid
            } else {
                System.out.println("Invalid input. Intake year cannot be in the future and earlier than 2000.");
            }
        } else {
            System.out.println("Invalid input. Year must be a 4-digit number.");
        }
        return false; // Year is invalid
    }

    public boolean validationContactNumber(String contactNumber, List<Student> studentList, List<Admin> adminList,
            List<FacultyMember> facultyMemberList) {
        // Check format: 9–10 digit number only
        if (!contactNumber.matches("\\d{9,10}")) {
            System.out.println("Invalid input. Contact number must be 9-10 digits without symbols.");
            return false;
        }

        // Check if contact number already exists in student list
        for (Student s : studentList) {
            if (s.getPhoneNo().equals(contactNumber)) {
                System.out.println("This contact number is already registered to a student.");
                return false;
            }
        }

        // Check if contact number already exists in admin list
        for (Admin a : adminList) {
            if (a.getPhoneNo().equals(contactNumber)) {
                System.out.println("This contact number is already registered to an admin.");
                return false;
            }
        }

        // Check if contact number already exists in faculty member list
        for (FacultyMember f : facultyMemberList) {
            if (f.getPhoneNo().equals(contactNumber)) {
                System.out.println("This contact number is already registered to a faculty member.");
                return false;
            }
        }

        return true; // Contact number is valid and unique
    }

    // Validate the gender
    public boolean validationGender(String gender) {
        if (gender.matches("(?i)m|f")) { // Ensure it's case-insensitive
            return true; // Gender is valid
        } else {
            System.out.println("Invalid input. Please enter 'M' for Male or 'F' for Female.");
            return false; // Gender is invalid
        }
    }

    // Validate the IC No
    public boolean validationIcNo(String icNo,
            List<Student> studentList,
            List<Admin> adminList,
            List<FacultyMember> facultyList) {
        // Check format: exactly 12 digits
        if (!icNo.matches("\\d{12}")) {
            System.out.println("Invalid input. Please enter a valid IC Number with 12 digits and no symbols.");
            return false;
        }

        // Check uniqueness in student list
        for (Student s : studentList) {
            if (s.getIcNo().equals(icNo)) {
                System.out.println("This IC Number is already registered to a student.");
                return false;
            }
        }

        // Check uniqueness in admin list
        for (Admin a : adminList) {
            if (a.getIcNo().equals(icNo)) {
                System.out.println("This IC Number is already registered to an admin.");
                return false;
            }
        }

        // Check uniqueness in faculty list
        for (FacultyMember f : facultyList) {
            if (f.getIcNo().equals(icNo)) {
                System.out.println("This IC Number is already registered to a faculty member.");
                return false;
            }
        }

        return true; // Valid and unique
    }

    // Validate Student ID
    public boolean validationStudentid(String studentid, Scanner scanner) {
        if (studentid.matches("\\d{4}S\\d{5}")) { // Ensure it's in the correct format
            return true; // Student ID is valid
        } else {
            System.out.print("Please try again. Press any key to continue...");
            scanner.nextLine();
            return false; // Student ID is invalid
        }
    }

    // Validate Yes or No (Using char)
    public boolean validationYesNo(char choosen) {
        if (choosen == 'Y' || choosen == 'y' || choosen == 'N' || choosen == 'n') {
            return true; // Valid input
        } else {
            System.out.println("Invalid input. Please enter 'Y' for Yes or 'N' for No.");
            return false; // Invalid input
        }
    }

    // Validate the Email
    public boolean validationEmail(String email) {
        // Ensure the email follows the correct format and ends with "@lccn.edu.my"
        if (email.matches("^[A-Za-z0-9+_.-]+@lccn\\.edu\\.my$")) {
            return true;
        } else {
            System.out.println("Invalid input. Email must be in the format: username@lccn.edu.my");
            return false;
        }
    }

    // Validate the Password
    public boolean validationPassword(String password) {
        // Password must have at least 8 characters, include 1 uppercase, 1 lowercase, 1
        // number, and 1 special character
        if (password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$")) {
            return true;
        } else {
            System.out.println("Invalid input. Password must be at least 8 characters long and include:");
            System.out.println("- At least 1 uppercase letter (A-Z)");
            System.out.println("- At least 1 lowercase letter (a-z)");
            System.out.println("- At least 1 digit (0-9)");
            System.out.println("- At least 1 special character (@#$%^&+=!)");
            return false;
        }
    }

    // Validate faculty code
    public boolean validationFacultyCode(String facultyCode) {
        if (facultyCode.toUpperCase().matches("[A-Z]{4}")) {
            return true;
        } else {
            System.out.println("Invalid input. Faculty code must be 4 alphabet characters only.");
            return false;
        }
    }

    // Validate the credit hours
    public boolean validationCreditHours(int creditHours) {
        if (creditHours >= 1 && creditHours <= 10) {
            return true; // Credit hours are valid
        } else {
            System.out.println("Invalid input. Credit hours must be between 1 to 10.");
            return false; // Credit hours are invalid
        }
    }

    // Validate subject code
    public boolean validationSubjectCode(String subjectCode) {
        if (subjectCode.toUpperCase().matches("[A-Z]{4}\\d{4}")) {
            return true;
        } else {
            System.out.println("Invalid input. Subject code must be 4 alphabet characters followed by 4 digits.");
            return false;
        }
    }

    // Validate examDate
    public boolean validationExamDate(String examDate) {

        try {
            LocalDate.parse(examDate); // will throw if invalid
            return true;
        } catch (DateTimeParseException e) {
            System.out.println("Invalid input. Please enter the date in the format YYYY-MM-DD.");
            return false;
        }
    }

    // Validate examTime
    public boolean validationExamTime(String examTime) {
        try {
            LocalTime.parse(examTime); // throws exception if invalid
            return true;
        } catch (DateTimeParseException e) {
            System.out.println("Invalid input. Please enter the time in the format HH:MM.");
            return false;
        }
    }

    // Validate Venue Name
    public boolean validationVenueName(String venueName) {
        // Only alphanumeric characters and spaces allowed
        if (venueName.matches("[a-zA-Z0-9 ]+")) {
            return true; // Venue name is valid
        } else {
            System.out.println("Invalid input. Venue name can only contain alphanumeric characters and spaces.");
            return false; // Venue name is invalid
        }
    }

    // Validate Venue ID
    public boolean validationVenueID(String venueID) {
        // Check if the venue ID is alphanumeric and has a valid length
        if (venueID.matches("[A-Z]{1,2}")) { // Ensure it's upper characters and whihin 1-99 only
            return true; // Venue ID is valid
        } else {
            System.out.println("Invalid input. Venue ID must be A-Z 1-99 Example B11");
            return false; // Venue ID is invalid
        }
    }

    // Validate Venue Capacity
    public boolean validationVenueCapacity(int capacity) {
        // Check if the capacity is a positive integer
        if (capacity > 0) { // Ensure it's a positive number
            return true; // Capacity is valid
        } else {
            System.out.println("Invalid input. Venue capacity must be a positive integer.");
            return false; // Capacity is invalid
        }
    }

    public int validationinputForMenu(Scanner scanner, boolean loginMenu, boolean studentMenu, boolean facultyMenu,
            boolean adminMenu) {
        int UserChoice = 0;
        boolean validInput = false;

        ClearScreen clearScreen = new ClearScreen();
        MenuList menuList = new MenuList();
        if (loginMenu == true) {
            while (!validInput) {
                try {

                    clearScreen.clearConsole();
                    menuList.loginMenu();
                    UserChoice = Integer.parseInt(scanner.nextLine());

                    if (UserChoice >= 1 && UserChoice <= 3) {
                        return UserChoice;
                    } else {
                        System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                        System.out.print("Press any key to continue..... ");
                        scanner.nextLine();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a valid number.");
                    System.out.print("Press any key to continue..... ");
                    scanner.nextLine();
                }
            }
        } else if (studentMenu == true) {

            while (!validInput) {
                try {

                    clearScreen.clearConsole();
                    menuList.studentSideMenu();
                    UserChoice = Integer.parseInt(scanner.nextLine());

                    if (UserChoice >= 1 && UserChoice <= 3) {
                        return UserChoice;
                    } else {
                        System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                        System.out.print("Press any key to continue..... ");
                        scanner.nextLine();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a valid number.");
                    System.out.print("Press any key to continue..... ");
                    scanner.nextLine();
                }
            }

        }

        return UserChoice;
    }

    public boolean validationStudentCount(int count) {
        return count > 0 && count <= 500;
    }

    public boolean validationFacultyidorAdminid(String fmAamid, Scanner scanner) {
        if (fmAamid.matches("FM\\d{5}")) { // Ensure it's in the correct format
            return true; // Student ID is valid
        } else if (fmAamid.matches("A\\d{5}")) {
            return true;
        } else {
            System.out.print("Faculty ID or Admin ID not found. Please try again. Press any key to continue...");
            scanner.nextLine();
            return false; // Student ID is invalid
        }
    }

}