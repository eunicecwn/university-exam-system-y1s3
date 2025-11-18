package UserController;

import java.util.Scanner;

import CourseController.*;
import ExaminationController.Examination;
import ExaminationController.ExaminationController;
import ExaminationController.Venue;
import ExtraFunction.*;

import java.util.ArrayList;
import java.util.List;

public class UserController {

    String[] departmentsList = {
            "Computer Science",
            "Business Administration",
            "Mechanical Engineering",
            "Electrical Engineering",
            "Psychology",
            "Biology",
            "Mathematics",
            "Economics",
            "Law",
            "Education"
    };

    String[] positionList = {
            "Lecturer",
            "Senior Lecturer",
            "Assistant Professor",
            "Associate Professor",
            "Professor",
            "Head of Department",
            "Dean",
            "Research Fellow",
            "Tutor",
            "Visiting Lecturer"
    };

    String[] adminPositionList = {
            "Registrar",
            "Administrative Officer",
            "Admissions Officer",
            "Finance Officer",
            "Human Resources Officer",
            "IT Administrator",
            "Library Manager",
            "Facilities Manager",
            "Student Affairs Coordinator",
            "Examination Officer"
    };

    // ========================================================================
    // STAFF
    // ========================================================================

    public void displayStaffUI(Scanner scanner, ClearScreen clearScreen, ValidationCheck validationCheck,
            MenuList menuInterface, CourseController courseController, ArrayList<Student> studentList,
            ArrayList<FacultyMember> facultyMemberList,
            ArrayList<Admin> adminList, ArrayList<Faculty> facultyList, ArrayList<Subject> subjectList,
            ExaminationController examinationController, ArrayList<Examination> examinationList,
            ArrayList<Venue> venueList) {
        // Variable
        boolean continueAdmin = true;

        do {
            clearScreen.clearConsole(); // Call the clear screen function
            menuInterface.LccnUniAdminMenu(); // Call the course menu

            try {

                int optionAdmin = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (optionAdmin) {
                    case 1: // Course Maintenance
                        courseController.displayCourseInterface(scanner, clearScreen, validationCheck, menuInterface,
                                facultyList);
                        break;
                    case 2: // Subject Maintenance
                        courseController.displaySubjectInterface(scanner, subjectList, validationCheck, clearScreen,
                                menuInterface, facultyList);
                        break;
                    case 3: // Staff Maintenance
                        displayStaffMaintenanceUI(scanner, clearScreen, validationCheck, menuInterface,
                                facultyMemberList, facultyList, adminList, studentList);
                        break;
                    case 4: // Faculty Maintenance
                        courseController.displayFacultyInterface(scanner, facultyList, validationCheck, clearScreen,
                                menuInterface);
                        break;
                    case 5: // Student Maintenance
                        displayStudentMaintenanceUI(scanner, clearScreen, validationCheck, menuInterface, studentList,
                                facultyList, adminList, facultyMemberList);
                        break;
                    case 6: // Examination Maintenance
                        examinationController.displayExaminationMaintenanceUI(scanner, clearScreen, validationCheck,
                                menuInterface, courseController, studentList, facultyList, examinationList, venueList,
                                menuInterface);
                        break;
                    case 7: // End the entire program
                        continueAdmin = false;
                        System.out.println("Exiting the program...");
                        return;
                    default:
                        System.out.println("Error! Please enter a number between 1 - 7 to proceed.");
                        System.out.println("Press Enter to continue...");
                        scanner.nextLine(); // Wait for user input
                }

            } catch (Exception ex) {
                System.out.println("Error! Please enter a numeric number to proceed");
                System.out.println("Press Enter to continue...");
                scanner.nextLine(); // Wait for user input
                scanner.nextLine(); // Consume the newline character
            }

        } while (continueAdmin);

        scanner.close();
    }

    public void displayStaffMaintenanceUI(Scanner scanner, ClearScreen clearScreen, ValidationCheck validationCheck,
            MenuList menuInterface, ArrayList<FacultyMember> facultyMemberList, ArrayList<Faculty> facultyList,
            ArrayList<Admin> adminList, ArrayList<Student> studentList) {

        boolean continueStaff = true;
        int optionRole;

        do {
            clearScreen.clearConsole();
            menuInterface.staffMenu();

            int optionStaff;
            while (true) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Error! Please enter a numeric number to proceed.");
                    scanner.nextLine(); // Consume invalid input
                    System.out.println("Press Enter to continue...");
                    scanner.nextLine();
                    clearScreen.clearConsole();
                    menuInterface.staffMenu();
                } else {
                    optionStaff = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    if (optionStaff >= 1 && optionStaff <= 6) {
                        break; // Exit loop if valid input between 1-6
                    } else {
                        System.out.println("Invalid option! Please enter a number between 1 and 6.");
                        System.out.println("Press Enter to continue...");
                        scanner.nextLine(); // Consume invalid input
                        clearScreen.clearConsole();
                        menuInterface.staffMenu(); // Re-display the menu
                    }
                }
            }

            switch (optionStaff) {
                case 1: // Add Staff
                    clearScreen.clearConsole();
                    menuInterface.selectionRoleStaffMenu();

                    while (true) {
                        if (!scanner.hasNextInt()) {
                            System.out.println("Invalid input! Please enter 1 - 2.");
                            scanner.nextLine(); // Consume invalid input
                            System.out.println("Press Enter to continue...");
                            scanner.nextLine(); // Wait for user input
                            clearScreen.clearConsole();
                            menuInterface.selectionRoleStaffMenu();
                        } else {
                            optionRole = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            if (optionRole == 1 || optionRole == 2 || optionRole == 3) {
                                break; // Exit loop if valid input
                            } else {
                                System.out.println("Invalid input! Please enter 1 - 3.");
                                scanner.nextLine(); // Consume invalid input
                                clearScreen.clearConsole();
                                menuInterface.selectionRoleStaffMenu(); // Re-display the menu
                            }
                        }
                    }

                    if (optionRole == 1) {
                        insertAdminRecord(scanner, clearScreen, validationCheck, menuInterface, studentList, adminList,
                                facultyMemberList);
                    } else if (optionRole == 2) {
                        insertFacultyMemberRecord(scanner, clearScreen, validationCheck, menuInterface, facultyList,
                                studentList, adminList, facultyMemberList);
                    } else if (optionRole == 3) {
                        break;
                    }
                    break;

                case 2: // Staff Overview
                    clearScreen.clearConsole();
                    menuInterface.selectionRoleStaffMenu();

                    while (true) {
                        if (!scanner.hasNextInt()) {
                            System.out.println("Invalid input! Please enter 1 - 3.");
                            scanner.nextLine(); // Consume invalid input
                            System.out.println("Press Enter to continue...");
                            scanner.nextLine(); // Wait for user input
                            clearScreen.clearConsole();
                            menuInterface.selectionRoleStaffMenu();
                        } else {
                            optionRole = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            if (optionRole == 1 || optionRole == 2 || optionRole == 3) {
                                break; // Exit loop if valid input
                            } else {
                                System.out.println("Invalid input! Please enter 1 - 3.");
                                scanner.nextLine(); // Consume invalid input
                                clearScreen.clearConsole();
                                menuInterface.selectionRoleStaffMenu(); // Re-display the menu
                            }
                        }
                    }

                    if (optionRole == 1) {
                        clearScreen.clearConsole();
                        displayAdminRecord(adminList);
                        System.out.println("Press Enter to continue...");
                        scanner.nextLine(); // Wait for user input
                        break;
                    } else if (optionRole == 2) {
                        clearScreen.clearConsole();
                        displayFacultyMemberRecord(facultyMemberList);
                        System.out.println("Press Enter to continue...");
                        scanner.nextLine(); // Wait for user input
                        break;
                    } else if (optionRole == 3) {
                        break;
                    }
                    break;

                case 3: // Update Staff Overview
                    clearScreen.clearConsole();
                    menuInterface.selectionRoleStaffMenu();

                    while (true) {
                        if (!scanner.hasNextInt()) {
                            System.out.println("Invalid input! Please enter 1 - 3.");
                            scanner.nextLine(); // Consume invalid input
                            System.out.println("Press Enter to continue...");
                            scanner.nextLine(); // Wait for user input
                            clearScreen.clearConsole();
                            menuInterface.selectionRoleStaffMenu();
                        } else {
                            optionRole = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            if (optionRole == 1 || optionRole == 2 || optionRole == 3) {
                                break; // Exit loop if valid input
                            } else {
                                System.out.println("Invalid input! Please enter 1 - 3.");
                                scanner.nextLine(); // Consume invalid input
                                clearScreen.clearConsole();
                                menuInterface.selectionRoleStaffMenu(); // Re-display the menu
                            }
                        }
                    }

                    if (optionRole == 1) {
                        updateAdminRecord(scanner, clearScreen, validationCheck, menuInterface, studentList, adminList,
                                facultyMemberList);
                        break;
                    } else if (optionRole == 2) {
                        updateFacultyMemberRecord(scanner, clearScreen, validationCheck, menuInterface, facultyList,
                                studentList, adminList, facultyMemberList);
                        break;
                    } else if (optionRole == 3) {
                        break;
                    }
                    break;

                case 4: // Deactivate Faculty Member
                    clearScreen.clearConsole();
                    menuInterface.selectionRoleStaffMenu();

                    while (true) {
                        if (!scanner.hasNextInt()) {
                            System.out.println("Invalid input! Please enter 1 - 3.");
                            scanner.nextLine(); // Consume invalid input
                            System.out.println("Press Enter to continue...");
                            scanner.nextLine(); // Wait for user input
                            clearScreen.clearConsole();
                            menuInterface.selectionRoleStaffMenu();
                        } else {
                            optionRole = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            if (optionRole == 1 || optionRole == 2 || optionRole == 3) {
                                break; // Exit loop if valid input
                            } else {
                                System.out.println("Invalid input! Please enter 1 - 3.");
                                scanner.nextLine(); // Consume invalid input
                                clearScreen.clearConsole();
                                menuInterface.selectionRoleStaffMenu(); // Re-display the menu
                            }
                        }
                    }

                    if (optionRole == 1) {
                        deactivateAdmin(scanner, clearScreen, validationCheck, menuInterface, adminList);
                        break;
                    } else if (optionRole == 2) {
                        deactivateFacultyMember(scanner, clearScreen, validationCheck, menuInterface,
                                facultyMemberList);
                        break;
                    } else if (optionRole == 3) {
                        break;
                    }
                    break;

                case 5: // Activate Faculty Member
                    clearScreen.clearConsole();
                    menuInterface.selectionRoleStaffMenu();

                    while (true) {
                        if (!scanner.hasNextInt()) {
                            System.out.println("Invalid input! Please enter 1 - 3.");
                            scanner.nextLine(); // Consume invalid input
                            System.out.println("Press Enter to continue...");
                            scanner.nextLine(); // Wait for user input
                            clearScreen.clearConsole();
                            menuInterface.selectionRoleStaffMenu();
                        } else {
                            optionRole = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            if (optionRole == 1 || optionRole == 2 || optionRole == 3) {
                                break; // Exit loop if valid input
                            } else {
                                System.out.println("Invalid input! Please enter 1 - 3.");
                                scanner.nextLine(); // Consume invalid input
                                clearScreen.clearConsole();
                                menuInterface.selectionRoleStaffMenu(); // Re-display the menu
                            }
                        }
                    }

                    if (optionRole == 1) {
                        activateAdmin(scanner, clearScreen, validationCheck, menuInterface, adminList);
                        break;
                    } else if (optionRole == 2) {
                        activateFacultyMember(scanner, clearScreen, validationCheck, menuInterface,
                                facultyMemberList);
                        break;
                    } else if (optionRole == 3) {
                        break;
                    }
                    break;

                case 6: // Return to main menu
                    continueStaff = false;
                    break;

                default:
                    System.out.println("Error! Please enter a number between 1 - 6 to proceed.");
                    System.out.println("Press Enter to continue...");
                    scanner.nextLine(); // Wait for user input
                    clearScreen.clearConsole();
                    menuInterface.staffMenu(); // Re-display the menu
                    break;
            }

        } while (continueStaff);
    }

    // ========================================================================
    // STUDENT
    // ========================================================================

    public void displayStudentMaintenanceUI(Scanner scanner, ClearScreen clearScreen, ValidationCheck validationCheck,
            MenuList menuInterface, ArrayList<Student> studentList, ArrayList<Faculty> facultyList,
            ArrayList<Admin> adminList, ArrayList<FacultyMember> facultyMemberList) {

        // Variable
        boolean continueStudent = true;

        do {
            clearScreen.clearConsole(); // Call the clear screen function
            menuInterface.studentMenu();
            try {
                int optionStudent = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (optionStudent) {
                    case 1: // Add Student
                        insertStudentRecord(scanner, clearScreen, validationCheck, menuInterface, studentList,
                                facultyList, adminList, facultyMemberList);
                        break;
                    case 2: // Student Record
                        clearScreen.clearConsole();
                        displayAllStudentsRecord(studentList);
                        System.out.println("Press Enter to continue...");
                        scanner.nextLine(); // Wait for user input
                        break;
                    case 3: // Update Student
                        updateStudentRecord(scanner, clearScreen, validationCheck, menuInterface, studentList,
                                facultyList, adminList, facultyMemberList);
                        break;
                    case 4: // Delete Student
                        deactivateStudent(scanner, clearScreen, validationCheck, menuInterface, studentList);
                        break;
                    case 5: // Activate Student
                        activateStudent(scanner, clearScreen, validationCheck, menuInterface, studentList);
                        break;
                    case 6: // Return to LCCN main menu
                        continueStudent = false;
                        break;
                    default:
                        System.out.println("Error! Please enter a number between 1 - 5 to proceed.");
                        System.out.println("Press Enter to continue...");
                        scanner.nextLine(); // Wait for user input
                }
            } catch (Exception ex) {
                System.out.println("Error! Please enter a numeric number to proceed");
                System.out.println("Press Enter to continue...");
                scanner.nextLine(); // Wait for user input
                scanner.nextLine(); // Consume the newline character
            }
        } while (continueStudent);
    }

    // Insert student record
    private void insertStudentRecord(Scanner scanner, ClearScreen clearScreen, ValidationCheck validationCheck,
            MenuList menuInterface, ArrayList<Student> studentList, ArrayList<Faculty> facultyList,
            ArrayList<Admin> adminList, ArrayList<FacultyMember> facultyMemberList) {

        char continueOption = 'Y';

        do {
            boolean isValid = false;
            String studentName = "";
            int intakeYear = 0;
            String contactNumber = "";
            String gender = "";
            String icNo = "";

            // Validate Student Name
            do {
                clearScreen.clearConsole();
                menuInterface.addStudentIcon();
                System.out.print("Student Name\t:");
                studentName = scanner.nextLine().toUpperCase(); // Convert input to uppercase;

                // Check for cancellation
                if (studentName.equals("X")) {
                    if (isExit(scanner, validationCheck)) {
                        return; // Exit entire method if user confirms
                    } else {
                        continue; // Continue looping if user chooses not to exit
                    }
                }

                // Validate the name
                isValid = validationCheck.validationName(studentName);

                // If the name is invalid, prompt the user to re-enter
                if (isValid == false) {
                    System.out.print("Please try again. Press any key to continue...");
                    scanner.nextLine(); // Wait for user input
                }
            } while (isValid == false);

            // Validate Intake Year
            do {
                clearScreen.clearConsole();
                menuInterface.addStudentIcon();
                System.out.println("Student Name\t: " + studentName);
                System.out.print("Intake Year\t:");

                // Reset validation state and temporary storage
                isValid = false;
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("X")) {
                    if (isExit(scanner, validationCheck)) {
                        return; // Exit completely
                    } else {
                        intakeYear = 0; // Explicitly reset
                        continue; // Restart loop
                    }
                }

                try {
                    intakeYear = Integer.parseInt(input);
                    isValid = validationCheck.validationIntakeYear(intakeYear);

                    if (!isValid) {
                        System.out.print("Invalid year. Press Enter to retry...");
                        scanner.nextLine();
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid input. Please enter a numeric year.");
                    System.out.print("Please try again. Press enter key to continue...");
                    scanner.nextLine();
                    intakeYear = 0; // Explicitly reset on error
                    isValid = false;
                }
            } while (!isValid);

            // Validate Contact Number
            do {
                clearScreen.clearConsole();
                menuInterface.addStudentIcon();
                System.out.println("Student Name\t: " + studentName); // Retain student name
                System.out.println("Intake Year\t: " + intakeYear); // Retain intake year
                System.out.print("Contact Number\t: + 60");
                contactNumber = scanner.nextLine();

                // Check for cancellation
                if (contactNumber.equals("X")) {
                    if (isExit(scanner, validationCheck)) {
                        return; // Exit entire method if user confirms
                    } else {
                        continue; // Continue looping if user chooses not to exit
                    }
                }

                // Validate the contact number
                isValid = validationCheck.validationContactNumber(contactNumber, studentList, adminList,
                        facultyMemberList);

                // If the contact number is invalid, prompt the user to re-enter
                if (isValid == false) {
                    System.out.print("Please try again. Press any key to continue...");
                    scanner.nextLine(); // Wait for user input
                }
            } while (isValid == false);

            do {
                clearScreen.clearConsole();
                menuInterface.addStudentIcon();
                System.out.println("Student Name\t: " + studentName); // Retain student name
                System.out.println("Intake Year\t: " + intakeYear); // Retain intake year
                System.out.println("Contact Number\t: + 60" + contactNumber); // Retain Contact Number
                System.out.print("Gender (M/F)\t:");
                gender = scanner.nextLine();

                // Check for cancellation
                if (gender.equals("X")) {
                    if (isExit(scanner, validationCheck)) {
                        return; // Exit entire method if user confirms
                    } else {
                        continue; // Continue looping if user chooses not to exit
                    }
                }

                isValid = validationCheck.validationGender(gender);

                if (isValid == false) {
                    System.out.print("Please try again. Press any key to continue...");
                    scanner.nextLine(); // Wait for user input
                }
            } while (isValid == false);

            // Assign the value M --> Male / F --> Female ignore case
            if (gender.equalsIgnoreCase("M")) {
                gender = "Male";
            } else if (gender.equalsIgnoreCase("F")) {
                gender = "Female";
            }

            // Validation IC Number
            do {
                clearScreen.clearConsole();
                menuInterface.addStudentIcon();
                System.out.println("Student Name\t: " + studentName); // Retain student name
                System.out.println("Intake Year\t: " + intakeYear); // Retain intake year
                System.out.println("Contact Number\t: + 60" + contactNumber); // Retain Contact Number
                System.out.println("Gender (M/F)\t: " + gender); // Retain gender
                System.out.print("IC No\t\t:");
                icNo = scanner.nextLine();

                // Check for cancellation
                if (icNo.equals("X")) {
                    if (isExit(scanner, validationCheck)) {
                        return; // Exit entire method if user confirms
                    } else {
                        continue; // Continue looping if user chooses not to exit
                    }
                }

                isValid = validationCheck.validationIcNo(icNo, studentList, adminList, facultyMemberList);

                if (isValid == false) {
                    System.out.print("Please try again. Press any key to continue...");
                    scanner.nextLine(); // Wait for user input
                }
            } while (isValid == false);

            int choice = -1;

            do {
                clearScreen.clearConsole();
                menuInterface.addStudentIcon();
                System.out.println("Student Name\t: " + studentName); // Retain student name
                System.out.println("Intake Year\t: " + intakeYear); // Retain intake year
                System.out.println("Contact Number\t: + 60" + contactNumber); // Retain Contact Number
                System.out.println("Gender (M/F)\t: " + gender); // Retain gender
                System.out.println("IC No\t\t: " + icNo);

                System.out.println("Available Faculties:");
                System.out.println("==============================================================================");
                System.out.printf("%-4s%-15s%-15s\n", "No", "Faculty Code", "Faculty Name");
                System.out.println("==============================================================================");

                for (int i = 0; i < facultyList.size(); i++) {
                    if (facultyList.get(i).getisActive()) {
                        System.out.printf("%-4d%-4s%-11s%-15s\n",
                                i + 1,
                                " ",
                                facultyList.get(i).getFacultyCode(),
                                facultyList.get(i).getFacultyName());
                        System.out.println(
                                "------------------------------------------------------------------------------");
                    }
                }

                isValid = false;
                System.out.print("Select a faculty (1-" + facultyList.size() + "): ");

                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    if (choice >= 1 && choice <= facultyList.size()) {
                        Faculty selected = facultyList.get(choice - 1);
                        if (selected.getisActive()) {
                            isValid = true; // success
                        } else {
                            System.out.println("This faculty is deactivated and cannot be selected.");
                            System.out.print("Press Enter to try again...");
                            scanner.nextLine();
                        }
                    } else {
                        System.out.println("Invalid input. Please enter a number between 1 and " + facultyList.size());
                        System.out.print("Press Enter to try again...");
                        scanner.nextLine();
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine(); // consume invalid input
                    System.out.print("Press Enter to try again...");
                    scanner.nextLine();
                }
            } while (!isValid);

            Faculty selectedFaculty = facultyList.get(choice - 1);
            ArrayList<Course> courseList = selectedFaculty.getCourseList();

            // COURSE SELECTION - MULTIPLE COURSES
            List<Course> selectedCourses = new ArrayList<>();
            boolean finishedSelecting = false;

            do {
                clearScreen.clearConsole();
                menuInterface.addStudentIcon();

                // Display student info
                System.out.println("Student Name\t: " + studentName);
                System.out.println("Intake Year\t: " + intakeYear);
                System.out.println("Contact Number\t: + 60" + contactNumber);
                System.out.println("Gender\t\t: " + gender);
                System.out.println("IC No\t\t: " + icNo);
                System.out.println("Faculty\t\t: " + selectedFaculty.getFacultyName());

                // Display available courses
                System.out.println("\nAvailable Courses:");
                System.out.println("==========================================");

                int displayNumber = 1;
                List<Course> availableCourses = new ArrayList<>();

                for (Course course : courseList) {
                    if (course.getisActive() && !selectedCourses.contains(course)) {
                        System.out.println(displayNumber + ". " + course.getCourseName());
                        availableCourses.add(course);
                        displayNumber++;
                    }
                }

                // Display already selected courses
                if (!selectedCourses.isEmpty()) {
                    System.out.println("\nAlready Selected Courses:");
                    for (Course course : selectedCourses) {
                        System.out.println("- " + course.getCourseName());
                    }
                }

                System.out.println("\n0. Finish Selecting Courses");

                if (availableCourses.isEmpty()) {
                    System.out.println("\nNo more courses available for selection!");
                    finishedSelecting = true;
                } else {
                    System.out.print("\nChoose Course (1-" + (displayNumber - 1) + ") or 0 to finish: ");

                    try {
                        int selection = Integer.parseInt(scanner.nextLine());

                        if (selection == 0) {
                            finishedSelecting = true;
                        } else if (selection >= 1 && selection <= availableCourses.size()) {
                            Course chosenCourse = availableCourses.get(selection - 1);
                            selectedCourses.add(chosenCourse);
                            System.out.println("Added: " + chosenCourse.getCourseName());
                        } else {
                            System.out.println("Invalid selection! Please choose between 0-" + availableCourses.size());
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid number!");
                    }
                }

                if (!finishedSelecting) {
                    System.out.println("Press Enter to continue...");
                    scanner.nextLine();
                }

            } while (!finishedSelecting);

            // STUDENT ENROLLMENT
            if (!selectedCourses.isEmpty()) {
                Student newStudent = new Student(studentName, gender, icNo, contactNumber, intakeYear);

                try {
                    studentList.add(newStudent);

                    // Enroll in all selected courses
                    for (Course course : selectedCourses) {
                        newStudent.enrollCourse(course);
                    }

                    do {
                        // Display success message
                        clearScreen.clearConsole();
                        menuInterface.addStudentIcon();
                        System.out.println(
                                "\n============================= Student Added ================================");
                        newStudent.displayInfo();

                        // Ask if the user wants to continue
                        System.out.print("\nDo you want to continue adding student? [Y/N]: ");

                        String input = scanner.nextLine().trim();
                        if (input.length() == 1) {
                            continueOption = input.charAt(0);
                            isValid = validationCheck.validationYesNo(continueOption);
                        } else {
                            isValid = false;
                            System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                        }

                        if (isValid == false) {
                            System.out.print("Please try again. Press any key to continue...");
                            scanner.nextLine(); // Wait for user input
                        }

                    } while (isValid == false);

                } catch (Exception e) {
                    // Rollback if enrollment fails
                    studentList.remove(newStudent);
                    System.out.println("\nENROLLMENT FAILED: " + e.getMessage());
                }
            } else {
                System.out.println("No courses selected. Student not created.");
                System.out.println("Press Enter to continue...");
                scanner.nextLine();
            }
        } while (continueOption == 'Y' || continueOption == 'y');
    }

    private void displayAllStudentsRecord(ArrayList<Student> studentList) {
        if (studentList.isEmpty()) {
            System.out.println("No student records found.");
        } else {
            System.out.println(
                    "\n============================================================================== All Student Records ================================================================================================");
            System.out.printf("%-13s%-15s%-9s%-13s%-15s%-30s%-15s%-15s%-30s", "Student ID", "Name", "Gender",
                    "Intake Year", "IC No", "Email", "Contact", "Password", "Enrolled Course");
            System.out.println(
                    "\n===================================================================================================================================================================================================");
            for (Student student : studentList) {
                if (student.getisActive() == true) {
                    System.out.print(student.Overview());
                    System.out.println(
                            "---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                }
            }
        }
    }

    // Update student record
    private void updateStudentRecord(Scanner scanner, ClearScreen clearScreen, ValidationCheck validationCheck,
            MenuList menuInterface, ArrayList<Student> studentList, ArrayList<Faculty> facultyList,
            ArrayList<Admin> adminList, ArrayList<FacultyMember> facultyMemberList) {
        boolean continueMenu;

        do {
            clearScreen.clearConsole();
            // Call the Faculty Member Icon
            menuInterface.updateStudentIcon();
            displayAllStudentsRecord(studentList);

            System.out.print("Enter Student ID to update:");
            String studentID = scanner.nextLine().trim(); // Trim to remove extra spaces

            continueMenu = checkStudentID(studentID, studentList); // Check if student ID exists

            if (continueMenu == true) {
                Student selectedStudent = null;

                // Find the student in the list
                for (Student student : studentList) {
                    if (student.getId().equals(studentID)) {
                        selectedStudent = student;
                        break;
                    }
                }

                // Display Student details
                System.out.println("\n============================= Student Found ================================");
                selectedStudent.displayInfo();
                System.out.println("------------------------------------------------------------------------------");

                boolean updateMore; // Control loop for multiple updates

                do {
                    try {
                        displayUpdateStudentScreen(clearScreen, menuInterface, selectedStudent);

                        menuInterface.updateStudentSelectedMenu();
                        System.out.print("Enter your choice: ");
                        int choice = scanner.nextInt();
                        scanner.nextLine();
                        boolean isValid = false;

                        switch (choice) {
                            case 1:
                                while (isValid == false) {

                                    displayUpdateStudentScreen(clearScreen, menuInterface, selectedStudent);

                                    System.out.print("Enter new Name: ");
                                    String studentName = scanner.nextLine().toUpperCase(); // Convert input to uppercase

                                    // Validate Student Name
                                    isValid = validationCheck.validationName(studentName);

                                    // If the name is invalid, prompt the user to re-enter
                                    if (isValid == true) {
                                        selectedStudent.setName(studentName);
                                    } else {
                                        System.out.print("Please try again. Press enter key to continue...");
                                        scanner.nextLine(); // Wait for user input
                                    }
                                }
                                break;
                            case 2:
                                while (isValid == false) {
                                    displayUpdateStudentScreen(clearScreen, menuInterface, selectedStudent);

                                    System.out.print("Enter new Gender (M/F): ");
                                    String studentGender = scanner.nextLine();

                                    // Validate Student Gender
                                    isValid = validationCheck.validationGender(studentGender);

                                    // If the name is invalid, prompt the user to re-enter
                                    if (isValid == true) {
                                        // Assign the value M --> Male / F --> Female ignore case
                                        if (studentGender.equalsIgnoreCase("M")) {
                                            studentGender = "Male";
                                        } else if (studentGender.equalsIgnoreCase("F")) {
                                            studentGender = "Female";
                                        }
                                        // Assign the value to the data
                                        selectedStudent.setGender(studentGender);
                                    } else if (isValid == false) {
                                        System.out.print("Please try again. Press enter key to continue...");
                                        scanner.nextLine(); // Wait for user input
                                    }
                                }
                                break;
                            case 3:
                                while (isValid == false) {
                                    displayUpdateStudentScreen(clearScreen, menuInterface, selectedStudent);

                                    System.out.print("Enter new IC No: ");
                                    String studentIcNo = scanner.nextLine();

                                    // Validate Student Ic No
                                    isValid = validationCheck.validationIcNo(studentIcNo, studentList, adminList,
                                            facultyMemberList);

                                    // If the name is invalid, prompt the user to re-enter
                                    if (isValid == true) {
                                        selectedStudent.setIcNo(studentIcNo);
                                    } else {
                                        System.out.print("Please try again. Press enter key to continue...");
                                        scanner.nextLine(); // Wait for user input
                                    }
                                }
                                break;
                            case 4:
                                while (isValid == false) {
                                    displayUpdateStudentScreen(clearScreen, menuInterface, selectedStudent);

                                    System.out.print("Enter new Email: ");
                                    String studentEmail = scanner.nextLine();

                                    // Validate Student Email
                                    isValid = validationCheck.validationEmail(studentEmail);

                                    // If the name is invalid, prompt the user to re-enter
                                    if (isValid == true) {
                                        selectedStudent.setEmail(studentEmail);
                                    } else {
                                        System.out.print("Please try again. Press enter key to continue...");
                                        scanner.nextLine(); // Wait for user input

                                    }
                                }
                                break;
                            case 5:
                                while (isValid == false) {
                                    displayUpdateStudentScreen(clearScreen, menuInterface, selectedStudent);

                                    System.out.print("Enter new Contact: +60");
                                    String studentContact = scanner.nextLine();

                                    // Validate Student Contact Number
                                    isValid = validationCheck.validationContactNumber(studentContact, studentList,
                                            adminList, facultyMemberList);

                                    // If the name is invalid, prompt the user to re-enter
                                    if (isValid == true) {
                                        selectedStudent.setPhoneNo(studentContact);
                                    } else {
                                        System.out.print("Please try again. Press enter key to continue...");
                                        scanner.nextLine(); // Wait for user input

                                    }
                                }
                                break;
                            case 6:
                                while (isValid == false) {
                                    displayUpdateStudentScreen(clearScreen, menuInterface, selectedStudent);

                                    System.out.print("Enter new Password: ");
                                    String studentPassword = scanner.nextLine();

                                    // Validate Student Password
                                    isValid = validationCheck.validationPassword(studentPassword);

                                    // If the name is invalid, prompt the user to re-enter
                                    if (isValid == true) {
                                        selectedStudent.setPassword(studentPassword);
                                    } else {
                                        System.out.print("\nPlease try again. Press enter key to continue...");
                                        scanner.nextLine(); // Wait for user input
                                    }
                                }
                                break;
                            case 7:
                                System.out.println("Exiting update menu...");
                                updateMore = false;
                                continue; // Skip "Update successful" message when exiting

                            default:
                                System.out.println("Invalid choice! No updates made.");
                                System.out.println("Press Enter to continue...");
                                scanner.nextLine(); // Wait for user input
                                updateMore = true;
                                continue;
                        }

                        System.out.println("\nStudent details updated successfully!");
                        System.out.println("Press Enter to continue...");
                        scanner.nextLine(); // Wait for user input

                        do {
                            clearScreen.clearConsole();
                            menuInterface.updateStudentIcon();

                            // Display updated student details
                            System.out.println(
                                    "\n========================= Updated Student Details ==========================");
                            selectedStudent.displayInfo();
                            System.out.println(
                                    "------------------------------------------------------------------------------");

                            System.out.print("\nDo you want to update another field? (Y/N): ");
                            char choiceContinue = scanner.next().charAt(0);
                            scanner.nextLine(); // Consume the newline

                            updateMore = (choiceContinue == 'Y' || choiceContinue == 'y');

                            isValid = validationCheck.validationYesNo(choiceContinue);

                            if (isValid == false) {
                                System.out.print("Please try again. Press enter key to continue...");
                                scanner.nextLine(); // Wait for user input
                            }

                        } while (isValid == false);

                    } catch (Exception ex) {
                        System.out.println("Error! Please enter a valid number.Press enter key to continue...");
                        scanner.nextLine();
                        scanner.nextLine();
                        updateMore = true;
                    }

                } while (updateMore == true); // Repeat update menu until the user exits

            } else {
                System.out.println("The student record was not found. Please double-check the Student ID.");
                continueMenu = isExit(scanner, validationCheck);
            }

        } while (continueMenu == false); // Repeat until user decides to exit
    }

    private void deactivateStudent(Scanner scanner, ClearScreen clearScreen, ValidationCheck validationCheck,
            MenuList menuInterface, ArrayList<Student> studentList) {

        boolean continueMenu;

        do {
            clearScreen.clearConsole();
            menuInterface.deactivateStudentIcon();

            if (studentList.isEmpty()) {
                System.out.println("No student records found.");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                return; // Exit the function if no students
            }

            displayAllStudentsRecord(studentList); // Show all students

            System.out.print("Enter the Student ID that you want to deactivate: ");
            String studentIdToDeactivate = scanner.nextLine();

            continueMenu = checkStudentID(studentIdToDeactivate, studentList);

            if (continueMenu == true) {
                Student selectedStudent = null;

                for (Student student : studentList) {
                    if (student.getId().equals(studentIdToDeactivate)) {
                        selectedStudent = student;
                        break;
                    }
                }

                if (selectedStudent != null) {
                    clearScreen.clearConsole();
                    menuInterface.deactivateStudentIcon();
                    System.out
                            .println("\n============================= Student Found ================================");
                    selectedStudent.displayInfo();
                    System.out.println(
                            "------------------------------------------------------------------------------\n");

                    System.out.print("Are you sure you want to deactivate this student? [Y/N]: ");
                    char confirmDeactivate = scanner.next().charAt(0);
                    scanner.nextLine();

                    if (confirmDeactivate == 'Y' || confirmDeactivate == 'y') {
                        selectedStudent.setActive(false);
                        System.out.println("\nStudent with ID " + studentIdToDeactivate + " has been deactivated.");
                    } else {
                        System.out.println("Deactivate canceled.");
                    }

                    System.out.println("Press Enter to continue...");
                    scanner.nextLine();
                }

            } else {
                System.out.println("The student record was not found. Please double-check the Student ID.");
                continueMenu = isExit(scanner, validationCheck);
            }

        } while (continueMenu == false); // Keep looping if user wants to try again
    }

    private void activateStudent(Scanner scanner, ClearScreen clearScreen, ValidationCheck validationCheck,
            MenuList menuInterface, ArrayList<Student> studentList) {
        boolean continueMenu;
        do {
            clearScreen.clearConsole();
            menuInterface.activateStudentIcon();
            ArrayList<Student> deactivatedStudents = new ArrayList<>();
            for (Student student : studentList) {
                if (!student.getisActive()) {
                    deactivatedStudents.add(student);
                }
            }
            if (deactivatedStudents.isEmpty()) {
                System.out.println("No deactivated students found.");
                System.out.println("Press Enter to return...");
                scanner.nextLine();
                return;
            }
            System.out
                    .println("================================ Deactivated Students ================================");
            System.out.printf("%-4s%-15s%-25s\n", "No", "Student ID", "Student Name");
            System.out.println("-------------------------------------------------------------------------------------");
            for (int i = 0; i < deactivatedStudents.size(); i++) {
                Student student = deactivatedStudents.get(i);
                System.out.printf("%-4d%-15s%-25s\n", i + 1, student.getId(), student.getName());
            }
            System.out.println("-------------------------------------------------------------------------------------");
            System.out.print("Enter the Student ID you want to activate: ");
            String studentIdToActivate = scanner.nextLine();
            Student selectedStudent = null;
            for (Student student : deactivatedStudents) {
                if (student.getId().equalsIgnoreCase(studentIdToActivate)) {
                    selectedStudent = student;
                    break;
                }
            }
            if (selectedStudent != null) {
                clearScreen.clearConsole();
                menuInterface.activateStudentIcon();
                System.out.println("============================= Student Found ===============================");
                selectedStudent.displayInfo();
                System.out.println("---------------------------------------------------------------------------");
                System.out.print("Are you sure you want to activate this student? [Y/N]: ");
                char confirmActivate = scanner.next().charAt(0);
                scanner.nextLine();
                if (confirmActivate == 'Y' || confirmActivate == 'y') {
                    selectedStudent.setActive(true);
                    System.out.println("\nStudent with ID " + studentIdToActivate + " has been activated.");
                } else {
                    System.out.println("Activation canceled.");
                }
                System.out.println("Press Enter to continue...");
                scanner.nextLine();
                continueMenu = false;
            } else {
                System.out.println("The Student ID was not found in the deactivated list.");
                continueMenu = !isExit(scanner, validationCheck);
            }
        } while (continueMenu);
    }

    private void displayUpdateStudentScreen(ClearScreen clearScreen, MenuList menuInterface, Student student) {
        clearScreen.clearConsole();
        menuInterface.updateStudentIcon();

        System.out.println("\n============================= Student Details ==============================");
        student.displayInfo();
        System.out.println("------------------------------------------------------------------------------");
    }

    // ========================================================================
    // FACULTY MEMBER
    // ========================================================================

    public void insertFacultyMemberRecord(Scanner scanner, ClearScreen clearScreen, ValidationCheck validationCheck,
            MenuList menuInterface, ArrayList<Faculty> facultyList, ArrayList<Student> studentList,
            ArrayList<Admin> adminList, ArrayList<FacultyMember> facultyMemberList) {

        char continueOption = 'Y';

        // Get faculty member details from the admin
        do {
            boolean isValid = false;
            String fmName = "";
            String contactNumber = "";
            String gender = "";
            String icNo = "";
            String position = "";
            String department = "";
            String areaInterest = "";
            String specialiseSub = "";

            // Validate Student Name
            do {
                clearScreen.clearConsole();
                menuInterface.addFacultyMemberIcon();
                System.out.print("Faculty Member Name\t:");
                fmName = scanner.nextLine().toUpperCase(); // Convert input to uppercase;

                // Check for cancellation
                if (fmName.equals("X")) {
                    if (isExit(scanner, validationCheck)) {
                        return; // Exit entire method if user confirms
                    } else {
                        continue; // Continue looping if user chooses not to exit
                    }
                }

                // Validate the name
                isValid = validationCheck.validationName(fmName);

                // If the name is invalid, prompt the user to re-enter
                if (isValid == false) {
                    System.out.print("Please try again. Press any key to continue...");
                    scanner.nextLine(); // Wait for user input
                }
            } while (isValid == false);

            // Validate Contact Number
            do {
                clearScreen.clearConsole();
                menuInterface.addFacultyMemberIcon();
                System.out.println("Faculty Member Name\t: " + fmName); // Retain faculty member name
                System.out.print("Contact Number\t\t: + 60");
                contactNumber = scanner.nextLine();

                // Check for cancellation
                if (contactNumber.equals("X")) {
                    if (isExit(scanner, validationCheck)) {
                        return; // Exit entire method if user confirms
                    } else {
                        continue; // Continue looping if user chooses not to exit
                    }
                }

                // Validate the contact number
                isValid = validationCheck.validationContactNumber(contactNumber, studentList, adminList,
                        facultyMemberList);

                // If the contact number is invalid, prompt the user to re-enter
                if (isValid == false) {
                    System.out.print("Please try again. Press any key to continue...");
                    scanner.nextLine(); // Wait for user input
                }
            } while (isValid == false);

            do {
                clearScreen.clearConsole();
                menuInterface.addFacultyMemberIcon();
                System.out.println("Faculty Member Name\t: " + fmName); // Retain faculty member name
                System.out.println("Contact Number\t\t: + 60" + contactNumber); // Retain Contact Number
                System.out.print("Gender (M/F)\t\t:");
                gender = scanner.nextLine();

                // Check for cancellation
                if (gender.equals("X")) {
                    if (isExit(scanner, validationCheck)) {
                        return; // Exit entire method if user confirms
                    } else {
                        continue; // Continue looping if user chooses not to exit
                    }
                }

                isValid = validationCheck.validationGender(gender);

                if (isValid == false) {
                    System.out.print("Please try again. Press any key to continue...");
                    scanner.nextLine(); // Wait for user input
                }
            } while (isValid == false);

            // Assign the value M --> Male / F --> Female ignore case
            if (gender.equalsIgnoreCase("M")) {
                gender = "Male";
            } else if (gender.equalsIgnoreCase("F")) {
                gender = "Female";
            }

            // Validation IC Number
            do {
                clearScreen.clearConsole();
                menuInterface.addFacultyMemberIcon();
                System.out.println("Faculty Member Name\t: " + fmName); // Retain faculty member name
                System.out.println("Contact Number\t\t: + 60" + contactNumber); // Retain Contact Number
                System.out.println("Gender (M/F)\t\t: " + gender); // Retain gender
                System.out.print("IC No\t\t\t:");
                icNo = scanner.nextLine();

                // Check for cancellation
                if (icNo.equals("X")) {
                    if (isExit(scanner, validationCheck)) {
                        return; // Exit entire method if user confirms
                    } else {
                        continue; // Continue looping if user chooses not to exit
                    }
                }

                isValid = validationCheck.validationIcNo(icNo, studentList, adminList, facultyMemberList);

                if (isValid == false) {
                    System.out.print("Please try again. Press any key to continue...");
                    scanner.nextLine(); // Wait for user input
                }
            } while (isValid == false);

            // Validation Position
            do {
                clearScreen.clearConsole();
                menuInterface.addFacultyMemberIcon();
                System.out.println("Faculty Member Name\t: " + fmName);
                System.out.println("Contact Number\t\t: + 60" + contactNumber);
                System.out.println("Gender (M/F)\t\t: " + gender);
                System.out.println("IC No\t\t\t: " + icNo);

                showPositionList();

                System.out.print("Enter a number (1-10) to select the position: ");
                String input = scanner.nextLine();

                // Check for cancellation
                if (input.equalsIgnoreCase("X")) {
                    if (isExit(scanner, validationCheck)) {
                        return;
                    } else {
                        continue;
                    }
                }

                try {
                    int choice = Integer.parseInt(input);
                    if (choice >= 1 && choice <= positionList.length) {
                        position = positionList[choice - 1];
                        isValid = true;
                    } else {
                        isValid = false;
                        System.out.println("Invalid number. Please choose between 1 and 10.");
                        System.out.print("Press any key to continue...");
                        scanner.nextLine();
                    }
                } catch (NumberFormatException e) {
                    isValid = false;
                    System.out.println("Invalid input. Please enter a number.");
                    System.out.print("Press any key to continue...");
                    scanner.nextLine();
                }
            } while (!isValid);

            // Validation Department
            do {
                clearScreen.clearConsole();
                menuInterface.addFacultyMemberIcon();
                System.out.println("Faculty Member Name\t: " + fmName);
                System.out.println("Contact Number\t\t: + 60" + contactNumber);
                System.out.println("Gender (M/F)\t\t: " + gender);
                System.out.println("IC No\t\t\t: " + icNo);
                System.out.println("Position\t\t: " + position);

                showDepartmentList();

                System.out.print("Please enter a number (1-10) to select department: ");
                String input = scanner.nextLine();

                // Check for cancellation
                if (input.equalsIgnoreCase("X")) {
                    if (isExit(scanner, validationCheck)) {
                        return; // Exit method
                    } else {
                        continue; // Re-display list
                    }
                }

                try {
                    int choice = Integer.parseInt(input);
                    if (choice >= 1 && choice <= 10) {
                        department = departmentsList[choice - 1]; // Get department name
                        isValid = true;
                    } else {
                        isValid = false;
                        System.out.println("Invalid number. Please choose between 1 and 10.");
                        System.out.print("Press any key to continue...");
                        scanner.nextLine();
                    }
                } catch (NumberFormatException e) {
                    isValid = false;
                    System.out.println("Invalid input. Please enter a number.");
                    System.out.print("Press any key to continue...");
                    scanner.nextLine();
                }
            } while (!isValid);

            // Validation Area of Interest
            do {
                clearScreen.clearConsole();
                menuInterface.addFacultyMemberIcon();
                System.out.println("Faculty Member Name\t: " + fmName); // Retain faculty member name
                System.out.println("Contact Number\t\t: + 60" + contactNumber); // Retain Contact Number
                System.out.println("Gender (M/F)\t\t: " + gender); // Retain gender
                System.out.println("IC No\t\t\t: " + icNo);
                System.out.println("Position\t\t: " + position);
                System.out.println("Department\t\t: " + department);
                System.out.print("Area of Interest\t:");
                areaInterest = scanner.nextLine();

                // Check for cancellation
                if (areaInterest.equals("X")) {
                    if (isExit(scanner, validationCheck)) {
                        return; // Exit entire method if user confirms
                    } else {
                        continue; // Continue looping if user chooses not to exit
                    }
                }

                isValid = validationCheck.validationName(areaInterest);

                if (isValid == false) {
                    System.out.print("Please try again. Press any key to continue...");
                    scanner.nextLine(); // Wait for user input
                }
            } while (isValid == false);

            // Validation Specialise Subject
            do {
                clearScreen.clearConsole();
                menuInterface.addFacultyMemberIcon();
                System.out.println("Faculty Member Name\t: " + fmName); // Retain faculty member name
                System.out.println("Contact Number\t\t: + 60" + contactNumber); // Retain Contact Number
                System.out.println("Gender (M/F)\t\t: " + gender); // Retain gender
                System.out.println("IC No\t\t\t: " + icNo);
                System.out.println("Position\t\t: " + position);
                System.out.println("Department\t\t: " + department);
                System.out.println("Area of Interest\t: " + areaInterest);
                System.out.print("Specialise Subject\t:");
                specialiseSub = scanner.nextLine();

                // Check for cancellation
                if (specialiseSub.equals("X")) {
                    if (isExit(scanner, validationCheck)) {
                        return; // Exit entire method if user confirms
                    } else {
                        continue; // Continue looping if user chooses not to exit
                    }
                }

                isValid = validationCheck.validationName(specialiseSub);

                if (isValid == false) {
                    System.out.print("Please try again. Press any key to continue...");
                    scanner.nextLine(); // Wait for user input
                }
            } while (isValid == false);

            // Display available faculties
            int choice = -1;

            do {
                clearScreen.clearConsole();
                menuInterface.addFacultyMemberIcon();
                System.out.println("Faculty Member Name\t: " + fmName);
                System.out.println("Contact Number\t\t: +60" + contactNumber);
                System.out.println("Gender (M/F)\t\t: " + gender);
                System.out.println("IC No\t\t\t: " + icNo);
                System.out.println("Position\t\t: " + position);
                System.out.println("Department\t\t: " + department);
                System.out.println("Area of Interest\t: " + areaInterest);
                System.out.println("Specialise Subject\t: " + specialiseSub);

                System.out.println("Available Faculties:");
                System.out.println("==============================================================================");
                System.out.printf("%-4s%-15s%-15s\n", "No", "Faculty Code", "Faculty Name");
                System.out.println("==============================================================================");

                for (int i = 0; i < facultyList.size(); i++) {
                    if (facultyList.get(i).getisActive()) {
                        System.out.printf("%-4d%-4s%-11s%-15s\n",
                                i + 1,
                                " ",
                                facultyList.get(i).getFacultyCode(),
                                facultyList.get(i).getFacultyName());
                        System.out.println(
                                "------------------------------------------------------------------------------");
                    }
                }

                isValid = false;
                System.out.print("Select a faculty (1-" + facultyList.size() + "): ");

                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    if (choice >= 1 && choice <= facultyList.size()) {
                        Faculty selected = facultyList.get(choice - 1);
                        if (selected.getisActive()) {
                            isValid = true; // success
                        } else {
                            System.out.println("This faculty is deactivated and cannot be selected.");
                            System.out.print("Press Enter to try again...");
                            scanner.nextLine();
                        }
                    } else {
                        System.out.println("Invalid input. Please enter a number between 1 and " + facultyList.size());
                        System.out.print("Press Enter to try again...");
                        scanner.nextLine();
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine(); // consume invalid input
                    System.out.print("Press Enter to try again...");
                    scanner.nextLine();
                }

            } while (!isValid);

            // Now, use the valid choice
            Faculty selectedFaculty = facultyList.get(choice - 1);

            // Create FM object with the selected faculty
            FacultyMember newFmMember = new FacultyMember(fmName, gender, icNo, contactNumber, position, department,
                    areaInterest, specialiseSub,
                    selectedFaculty);
            facultyMemberList.add(newFmMember);

            // Display Faculty Member information
            do {
                // Display student details
                clearScreen.clearConsole();
                menuInterface.addFacultyMemberIcon();
                System.out.println("\n========================== New Faculty Member Added ==========================");
                newFmMember.displayInfo();
                System.out.println("------------------------------------------------------------------------------");

                // Ask if the user wants to continue
                System.out.print("\nDo you want to continue adding faculty member? [Y/N]: ");

                String input = scanner.nextLine().trim();
                if (input.length() == 1) {
                    continueOption = input.charAt(0);
                    isValid = validationCheck.validationYesNo(continueOption);
                } else {
                    isValid = false;
                    System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                }

                if (isValid == false) {
                    System.out.print("Please try again. Press any key to continue...");
                    scanner.nextLine(); // Wait for user input
                }

            } while (isValid == false);

        } while (continueOption == 'Y' || continueOption == 'y');
    }

    // Display Faculty Member Record
    private void displayFacultyMemberRecord(ArrayList<FacultyMember> facultyMemberList) {
        if (facultyMemberList.isEmpty()) {
            System.out.println("No faculty member records found.");
        } else {
            System.out.println(
                    "\n================================================================================== Faculty Member Records =====================================================================================");
            System.out.printf("%-8s%-15s%-8s%-16s%-30s%-12s%-10s%-20s%-25s%-20s%-20s%-5s\n",
                    "ID", "Name", "Gender", "IC No", "Email", "Contact",
                    "Password", "Position", "Department", "Area of Interest", "Specialise Subject", "Faculty");

            System.out.println(
                    "-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            for (FacultyMember facultyMember : facultyMemberList) {
                if (facultyMember.getIsActive()) {
                    System.out.printf("%-8s%-15s%-8s%-16s%-30s%-12s%-10s%-20s%-25s%-20s%-20s%-5s\n",
                            facultyMember.getId(), facultyMember.getName(), facultyMember.getGender(),
                            facultyMember.getIcNo(), facultyMember.getEmail(),
                            facultyMember.getPhoneNo(), facultyMember.getPassword(), facultyMember.getPosition(),
                            facultyMember.getDepartment(),
                            facultyMember.getAreaInterest(), facultyMember.getSpeciliseSubject(),
                            facultyMember.getFaculty().getFacultyCode());
                    System.out.println(
                            "-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                }
            }
        }
    }

    // Update Faculty Member Record
    private void updateFacultyMemberRecord(Scanner scanner, ClearScreen clearScreen, ValidationCheck validationCheck,
            MenuList menuInterface, ArrayList<Faculty> facultyList, ArrayList<Student> studentList,
            ArrayList<Admin> adminList, ArrayList<FacultyMember> facultyMemberList) {
        boolean continueMenu;

        do {
            clearScreen.clearConsole();
            // Call the Faculty Member Icon
            menuInterface.updateFacultyMemberIcon();
            displayFacultyMemberRecord(facultyMemberList);

            System.out.print("\nEnter Faculty Member ID to update:");
            String fmID = scanner.nextLine().trim(); // Trim to remove extra spaces

            continueMenu = checkFmID(fmID, facultyMemberList); // Check if student ID exists

            if (continueMenu == true) {
                FacultyMember selectedFMember = null;

                // Find the Faculty Member in the list
                for (FacultyMember fMember : facultyMemberList) {
                    if (fMember.getId().equals(fmID)) {
                        selectedFMember = fMember;
                        break;
                    }
                }

                // Display Faculty Member details
                System.out.println("========================= Faculty Member Found =============================");
                selectedFMember.displayInfo();
                System.out.println("------------------------------------------------------------------------------");

                boolean updateMore; // Control loop for multiple updates

                do {
                    try {
                        displayUpdateFmScreen(clearScreen, menuInterface, selectedFMember);

                        menuInterface.updateFmSelectedMenu();
                        ;
                        System.out.print("Enter your choice: ");
                        int choice = scanner.nextInt();
                        scanner.nextLine();
                        boolean isValid = false;

                        switch (choice) {
                            case 1: // Name
                                while (isValid == false) {

                                    displayUpdateFmScreen(clearScreen, menuInterface, selectedFMember);

                                    System.out.print("Enter new Name: ");
                                    String fmName = scanner.nextLine().toUpperCase(); // Convert input to uppercase

                                    // Validate Faculty Member Name
                                    isValid = validationCheck.validationName(fmName);

                                    // If the name is invalid, prompt the user to re-enter
                                    if (isValid == true) {
                                        selectedFMember.setName(fmName);
                                    } else {
                                        System.out.print("Please try again. Press enter key to continue...");
                                        scanner.nextLine(); // Wait for user input
                                    }
                                }
                                break;
                            case 2:// Gender
                                while (isValid == false) {
                                    displayUpdateFmScreen(clearScreen, menuInterface, selectedFMember);

                                    System.out.print("Enter new Gender (M/F): ");
                                    String fmGender = scanner.nextLine();

                                    // Validate Faculty Member Gender
                                    isValid = validationCheck.validationGender(fmGender);

                                    // If the name is invalid, prompt the user to re-enter
                                    if (isValid == true) {
                                        // Assign the value M --> Male / F --> Female ignore case
                                        if (fmGender.equalsIgnoreCase("M")) {
                                            fmGender = "Male";
                                        } else if (fmGender.equalsIgnoreCase("F")) {
                                            fmGender = "Female";
                                        }
                                        // Assign the value to the data
                                        selectedFMember.setGender(fmGender);
                                    } else if (isValid == false) {
                                        System.out.print("Please try again. Press enter key to continue...");
                                        scanner.nextLine(); // Wait for user input
                                    }
                                }
                                break;
                            case 3:// Ic No
                                while (isValid == false) {
                                    displayUpdateFmScreen(clearScreen, menuInterface, selectedFMember);

                                    System.out.print("Enter new IC No: ");
                                    String fmIcNo = scanner.nextLine();

                                    // Validate Faculty Member Ic No
                                    isValid = validationCheck.validationIcNo(fmIcNo, studentList, adminList,
                                            facultyMemberList);

                                    // If the name is invalid, prompt the user to re-enter
                                    if (isValid == true) {
                                        selectedFMember.setIcNo(fmIcNo);
                                    } else {
                                        System.out.print("Please try again. Press enter key to continue...");
                                        scanner.nextLine(); // Wait for user input
                                    }
                                }
                                break;
                            case 4:// Email
                                while (isValid == false) {
                                    displayUpdateFmScreen(clearScreen, menuInterface, selectedFMember);

                                    System.out.print("Enter new Email: ");
                                    String fmEmail = scanner.nextLine();

                                    // Validate Faculty Member Email
                                    isValid = validationCheck.validationEmail(fmEmail);

                                    // If the name is invalid, prompt the user to re-enter
                                    if (isValid == true) {
                                        selectedFMember.setEmail(fmEmail);
                                    } else {
                                        System.out.print("Please try again. Press enter key to continue...");
                                        scanner.nextLine(); // Wait for user input

                                    }
                                }
                                break;
                            case 5:// Contact Number
                                while (isValid == false) {
                                    displayUpdateFmScreen(clearScreen, menuInterface, selectedFMember);

                                    System.out.print("Enter new Contact: +60");
                                    String fmContact = scanner.nextLine();

                                    // Validate Faculty Member Name
                                    isValid = validationCheck.validationContactNumber(fmContact, studentList, adminList,
                                            facultyMemberList);

                                    // If the name is invalid, prompt the user to re-enter
                                    if (isValid == true) {
                                        selectedFMember.setPhoneNo(fmContact);
                                    } else {
                                        System.out.print("Please try again. Press enter key to continue...");
                                        scanner.nextLine(); // Wait for user input

                                    }
                                }
                                break;
                            case 6: // New Password
                                while (isValid == false) {
                                    displayUpdateFmScreen(clearScreen, menuInterface, selectedFMember);

                                    System.out.print("Enter new Password: ");
                                    String fmPassword = scanner.nextLine();

                                    // Validate Faculty Member Password
                                    isValid = validationCheck.validationPassword(fmPassword);

                                    // If the name is invalid, prompt the user to re-enter
                                    if (isValid == true) {
                                        selectedFMember.setPassword(fmPassword);
                                    } else {
                                        System.out.print("\nPlease try again. Press enter key to continue...");
                                        scanner.nextLine(); // Wait for user input
                                    }
                                }
                                break;

                            case 7: // Position
                                while (!isValid) {
                                    displayUpdateFmScreen(clearScreen, menuInterface, selectedFMember);

                                    // Display admin position options
                                    showPositionList();

                                    System.out.print("Please enter a number (1-10) to select new position: ");
                                    String input = scanner.nextLine();

                                    // Check for cancellation
                                    if (input.equalsIgnoreCase("X")) {
                                        if (isExit(scanner, validationCheck)) {
                                            return; // Exit entire method if user confirms
                                        } else {
                                            continue; // Continue looping if user chooses not to exit
                                        }
                                    }

                                    int selectedChoice = -1;
                                    try {
                                        selectedChoice = Integer.parseInt(input);
                                        if (selectedChoice >= 1 && selectedChoice <= positionList.length) {
                                            String selectedPosition = positionList[selectedChoice - 1];
                                            selectedFMember.setPosition(selectedPosition);
                                            isValid = true;
                                        } else {
                                            System.out.println("Invalid number. Please choose between 1 and 10.");
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid input. Please enter a number.");
                                    }

                                    if (!isValid) {
                                        System.out.print("Please try again. Press enter key to continue...");
                                        scanner.nextLine(); // Wait for user input
                                    }
                                }
                                break;

                            case 8: // Department
                                while (!isValid) {
                                    displayUpdateFmScreen(clearScreen, menuInterface, selectedFMember);

                                    showDepartmentList();

                                    System.out.print("Please enter a number (1-10) to select the department: ");
                                    String input = scanner.nextLine();

                                    // Check for cancellation
                                    if (input.equalsIgnoreCase("X")) {
                                        if (isExit(scanner, validationCheck)) {
                                            return; // Exit entire method if user confirms
                                        } else {
                                            continue; // Continue looping if user chooses not to exit
                                        }
                                    }

                                    int selectedChoice = -1; // New variable to store the parsed input choice
                                    try {
                                        selectedChoice = Integer.parseInt(input);
                                        if (selectedChoice >= 1 && selectedChoice <= departmentsList.length) {
                                            // Set the selected department
                                            String selectedDepartment = departmentsList[selectedChoice - 1];
                                            selectedFMember.setDepartment(selectedDepartment); // Assuming
                                                                                               // setDepartment() method
                                                                                               // exists in
                                                                                               // selectedFMember
                                            isValid = true; // Valid input
                                        } else {
                                            System.out.println("Invalid number. Please choose between 1 and 10.");
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid input. Please enter a number.");
                                    }

                                    if (!isValid) {
                                        System.out.print("Please try again. Press enter key to continue...");
                                        scanner.nextLine(); // Wait for user input
                                    }
                                }
                                break;

                            case 9:
                                while (isValid == false) {
                                    displayUpdateFmScreen(clearScreen, menuInterface, selectedFMember);

                                    System.out.print("Enter new Area of Interest: ");
                                    String areaInterest = scanner.nextLine();

                                    // Validate Faculty Member Area of Interest
                                    isValid = validationCheck.validationName(areaInterest);

                                    // If the name is invalid, prompt the user to re-enter
                                    if (isValid == true) {
                                        selectedFMember.setAreaInterest(areaInterest);
                                    } else {
                                        System.out.print("\nPlease try again. Press enter key to continue...");
                                        scanner.nextLine(); // Wait for user input
                                    }
                                }
                                break;

                            case 10:
                                while (isValid == false) {
                                    displayUpdateFmScreen(clearScreen, menuInterface, selectedFMember);

                                    System.out.print("Enter new Specialise Subject: ");
                                    String specialiseSub = scanner.nextLine();

                                    // Validate Faculty Member Area of Interest
                                    isValid = validationCheck.validationName(specialiseSub);

                                    // If the name is invalid, prompt the user to re-enter
                                    if (isValid == true) {
                                        selectedFMember.setSpecialiseSubject(specialiseSub);
                                    } else {
                                        System.out.print("\nPlease try again. Press enter key to continue...");
                                        scanner.nextLine(); // Wait for user input
                                    }
                                }
                                break;

                            case 11:
                                while (!isValid) {
                                    displayUpdateFmScreen(clearScreen, menuInterface, selectedFMember);

                                    System.out.println("Available Faculties:");
                                    System.out.println(
                                            "==============================================================================");
                                    System.out.printf("%-4s%-15s%-15s\n", "No", "Faculty Code", "Faculty Name");
                                    System.out.println(
                                            "==============================================================================");

                                    for (int i = 0; i < facultyList.size(); i++) {
                                        if (facultyList.get(i).getisActive()) {
                                            System.out.printf("%-4d%-4s%-11s%-15s\n",
                                                    i + 1,
                                                    " ",
                                                    facultyList.get(i).getFacultyCode(),
                                                    facultyList.get(i).getFacultyName());
                                            System.out.println(
                                                    "------------------------------------------------------------------------------");
                                        }
                                    }

                                    System.out.print("Select a faculty (1-" + facultyList.size() + "): ");

                                    String input = scanner.nextLine(); // Always use nextLine() to safely capture user
                                                                       // input
                                    try {
                                        int facultyChoice = Integer.parseInt(input); // Manually parse integer

                                        if (facultyChoice >= 1 && facultyChoice <= facultyList.size()) {
                                            Faculty selectedFaculty = facultyList.get(facultyChoice - 1);

                                            // Check if the selected faculty is active
                                            if (selectedFaculty.getisActive()) {
                                                selectedFMember.setFaculty(selectedFaculty);
                                                isValid = true;
                                            } else {
                                                System.out
                                                        .println("This faculty is deactivated and cannot be assigned.");
                                                System.out.print("Press enter key to try again...");
                                                scanner.nextLine(); // Wait for Enter
                                            }

                                        } else {
                                            System.out.println("Invalid input. Please enter a number between 1 and "
                                                    + facultyList.size());
                                            System.out.print("Press enter key to try again...");
                                            scanner.nextLine();
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid input. Please enter a valid number.");
                                        System.out.print("Press enter key to try again...");
                                        scanner.nextLine(); // Wait for Enter
                                    }
                                }
                                break;
                            case 12:
                                System.out.println("Exiting update menu...");
                                updateMore = false;
                                continue; // Skip "Update successful" message when exiting

                            default:
                                System.out.println("Invalid choice! No updates made.");
                                System.out.println("Press Enter to continue...");
                                scanner.nextLine(); // Wait for user input
                                updateMore = true;
                                continue;
                        }

                        System.out.println("\nFaculty member details updated successfully!");
                        System.out.println("Press Enter to continue...");
                        scanner.nextLine(); // Wait for user input

                        do {
                            clearScreen.clearConsole();
                            menuInterface.updateFacultyMemberIcon();

                            // Display updated student details
                            System.out.println(
                                    "\n======================= Updated Faculty Member Details =======================");
                            selectedFMember.displayInfo();
                            System.out.println(
                                    "------------------------------------------------------------------------------");

                            System.out.print("\nDo you want to update another field? (Y/N): ");
                            char choiceContinue = scanner.next().charAt(0);
                            scanner.nextLine(); // Consume the newline

                            updateMore = (choiceContinue == 'Y' || choiceContinue == 'y');

                            isValid = validationCheck.validationYesNo(choiceContinue);

                            if (isValid == false) {
                                System.out.print("Please try again. Press enter key to continue...");
                                scanner.nextLine(); // Wait for user input
                            }

                        } while (isValid == false);

                    } catch (Exception ex) {
                        System.out.println("Error! Please enter a valid number.Press enter key to continue...");
                        scanner.nextLine();
                        scanner.nextLine();
                        updateMore = true;
                    }

                } while (updateMore == true); // Repeat update menu until the user exits

            } else {
                System.out
                        .println("The faculty member record was not found. Please double-check the Faculty Member ID.");
                continueMenu = isExit(scanner, validationCheck);
            }

        } while (continueMenu == false); // Repeat until user decides to exit
    }

    private void displayUpdateFmScreen(ClearScreen clearScreen, MenuList menuInterface, FacultyMember facultyMember) {
        clearScreen.clearConsole();
        menuInterface.updateFacultyMemberIcon();

        System.out.println("\n========================== Faculty Member Details ==========================");
        facultyMember.displayInfo();
        System.out.println("------------------------------------------------------------------------------");
    }

    private void deactivateFacultyMember(Scanner scanner, ClearScreen clearScreen, ValidationCheck validationCheck,
            MenuList menuInterface, ArrayList<FacultyMember> facultyMemberList) {

        boolean continueMenu;

        do {
            clearScreen.clearConsole();
            menuInterface.deactivateFmIcon();

            if (facultyMemberList.isEmpty()) {
                System.out.println("No faculty member records found.");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                return; // Exit the function if no faculty member
            }

            displayFacultyMemberRecord(facultyMemberList);

            System.out.print("Enter the Faculty Member ID that you want to deactivate: ");
            String fmIdToDeactivate = scanner.nextLine();

            continueMenu = checkFmID(fmIdToDeactivate, facultyMemberList);

            if (continueMenu == true) {
                FacultyMember selectedFm = null;

                for (FacultyMember fMember : facultyMemberList) {
                    if (fMember.getId().equals(fmIdToDeactivate)) {
                        selectedFm = fMember;
                        break;
                    }
                }

                if (selectedFm != null) {
                    clearScreen.clearConsole();
                    menuInterface.deactivateFmIcon();
                    System.out
                            .println(
                                    "\n=========================== Faculty Member Found =============================");
                    selectedFm.displayInfo();
                    System.out.println(
                            "------------------------------------------------------------------------------\n");

                    System.out.print("Are you sure you want to deactivate this faculty member? [Y/N]: ");
                    char confirmDeactivate = scanner.next().charAt(0);
                    scanner.nextLine();

                    if (confirmDeactivate == 'Y' || confirmDeactivate == 'y') {
                        selectedFm.setActive(false);
                        System.out.println("\nFaculty Member with ID " + selectedFm.getId() + " has been deactivated.");
                    } else {
                        System.out.println("Deactivate canceled.");
                    }

                    System.out.println("Press Enter to continue...");
                    scanner.nextLine();
                }

            } else {
                System.out
                        .println("The faculty member record was not found. Please double-check the Faculty Member ID.");
                continueMenu = isExit(scanner, validationCheck);
            }

        } while (continueMenu == false); // Keep looping if user wants to try again
    }

    private void activateFacultyMember(Scanner scanner, ClearScreen clearScreen, ValidationCheck validationCheck,
            MenuList menuInterface, ArrayList<FacultyMember> facultyMemberList) {

        boolean continueMenu;

        do {
            clearScreen.clearConsole();
            menuInterface.activateFmIcon();

            ArrayList<FacultyMember> deactivatedFacultyMembers = new ArrayList<>();
            for (FacultyMember fMember : facultyMemberList) {
                if (!fMember.getIsActive()) {
                    deactivatedFacultyMembers.add(fMember);
                }
            }

            if (deactivatedFacultyMembers.isEmpty()) {
                System.out.println("No deactivated faculty members found.");
                System.out.println("Press Enter to return...");
                scanner.nextLine();
                return;
            }

            System.out.println(
                    "================================ Deactivated Faculty Members ================================");
            System.out.printf("%-4s%-15s%-25s\n", "No", "Faculty Member ID", "Faculty Member Name");
            System.out.println("-------------------------------------------------------------------------------------");
            for (int i = 0; i < deactivatedFacultyMembers.size(); i++) {
                FacultyMember fMember = deactivatedFacultyMembers.get(i);
                System.out.printf("%-4d%-15s%-25s\n", i + 1, fMember.getId(), fMember.getName());
            }

            System.out.println("-------------------------------------------------------------------------------------");
            System.out.print("Enter the Faculty Member ID you want to activate: ");
            String fmIdToActivate = scanner.nextLine();
            FacultyMember selectedFm = null;

            for (FacultyMember fMember : deactivatedFacultyMembers) {
                if (fMember.getId().equalsIgnoreCase(fmIdToActivate)) {
                    selectedFm = fMember;
                    break;
                }
            }

            if (selectedFm != null) {
                clearScreen.clearConsole();
                menuInterface.activateFmIcon();
                System.out
                        .println("============================= Faculty Member Found ===============================");
                selectedFm.displayInfo();
                System.out.println("---------------------------------------------------------------------------");
                System.out.print("Are you sure you want to activate this faculty member? [Y/N]: ");
                char confirmActivate = scanner.next().charAt(0);
                scanner.nextLine();

                if (confirmActivate == 'Y' || confirmActivate == 'y') {
                    selectedFm.setActive(true);
                    System.out.println("\nFaculty Member with ID " + selectedFm.getId() + " has been activated.");
                } else {
                    System.out.println("Activation canceled.");
                }

                System.out.println("Press Enter to continue...");
                scanner.nextLine();
                continueMenu = false; // Stop the loop after processing
            } else {
                System.out.println("The Faculty Member ID was not found in the deactivated list.");
                continueMenu = !isExit(scanner, validationCheck); // Ask if user wants to try again
            }

        } while (continueMenu);
    }

    // ========================================================================
    // ADMIN
    // ========================================================================

    public void insertAdminRecord(Scanner scanner, ClearScreen clearScreen, ValidationCheck validationCheck,
            MenuList menuInterface, ArrayList<Student> studentList, ArrayList<Admin> adminList,
            ArrayList<FacultyMember> facultyMemberList) {

        char continueOption = 'Y';

        // Adding the admin details
        do {
            boolean isValid = false;
            String adminName = "";
            String contactNumber = "";
            String gender = "";
            String icNo = "";
            String position = "";
            String department = "";

            // Validate Admin Name
            do {
                clearScreen.clearConsole();
                menuInterface.addAdminIcon();
                System.out.print("Admin Name\t\t:");
                adminName = scanner.nextLine().toUpperCase(); // Convert input to uppercase;

                // Check for cancellation
                if (adminName.equals("X")) {
                    if (isExit(scanner, validationCheck)) {
                        return; // Exit entire method if user confirms
                    } else {
                        continue; // Continue looping if user chooses not to exit
                    }
                }

                // Validate the name
                isValid = validationCheck.validationName(adminName);

                // If the name is invalid, prompt the user to re-enter
                if (isValid == false) {
                    System.out.print("Please try again. Press any key to continue...");
                    scanner.nextLine(); // Wait for user input
                }
            } while (isValid == false);

            // Validate Contact Number
            do {
                clearScreen.clearConsole();
                menuInterface.addAdminIcon();
                System.out.println("Admin Name\t\t: " + adminName); // Retain admin name
                System.out.print("Contact Number\t\t: + 60");
                contactNumber = scanner.nextLine();

                // Check for cancellation
                if (contactNumber.equals("X")) {
                    if (isExit(scanner, validationCheck)) {
                        return; // Exit entire method if user confirms
                    } else {
                        continue; // Continue looping if user chooses not to exit
                    }
                }

                // Validate the contact number
                isValid = validationCheck.validationContactNumber(contactNumber, studentList, adminList,
                        facultyMemberList);

                // If the contact number is invalid, prompt the user to re-enter
                if (isValid == false) {
                    System.out.print("Please try again. Press any key to continue...");
                    scanner.nextLine(); // Wait for user input
                }
            } while (isValid == false);

            do {
                clearScreen.clearConsole();
                menuInterface.addAdminIcon();
                System.out.println("Admin Name\t\t: " + adminName); // Retain admin name
                System.out.println("Contact Number\t\t: + 60" + contactNumber); // Retain Contact Number
                System.out.print("Gender (M/F)\t\t:");
                gender = scanner.nextLine();

                // Check for cancellation
                if (gender.equals("X")) {
                    if (isExit(scanner, validationCheck)) {
                        return; // Exit entire method if user confirms
                    } else {
                        continue; // Continue looping if user chooses not to exit
                    }
                }

                isValid = validationCheck.validationGender(gender);

                if (isValid == false) {
                    System.out.print("Please try again. Press any key to continue...");
                    scanner.nextLine(); // Wait for user input
                }
            } while (isValid == false);

            // Assign the value M --> Male / F --> Female ignore case
            if (gender.equalsIgnoreCase("M")) {
                gender = "Male";
            } else if (gender.equalsIgnoreCase("F")) {
                gender = "Female";
            }

            // Validation IC Number
            do {
                clearScreen.clearConsole();
                menuInterface.addAdminIcon();
                System.out.println("Admin Name\t\t: " + adminName); // Retain admin name
                System.out.println("Contact Number\t\t: + 60" + contactNumber); // Retain Contact Number
                System.out.println("Gender (M/F)\t\t: " + gender); // Retain gender
                System.out.print("IC No\t\t\t:");
                icNo = scanner.nextLine();

                // Check for cancellation
                if (icNo.equals("X")) {
                    if (isExit(scanner, validationCheck)) {
                        return; // Exit entire method if user confirms
                    } else {
                        continue; // Continue looping if user chooses not to exit
                    }
                }

                isValid = validationCheck.validationIcNo(icNo, studentList, adminList, facultyMemberList);

                if (isValid == false) {
                    System.out.print("Please try again. Press any key to continue...");
                    scanner.nextLine(); // Wait for user input
                }
            } while (isValid == false);

            // Validation for Admin Position
            do {
                clearScreen.clearConsole();
                menuInterface.addAdminIcon();
                System.out.println("Admin Name\t\t: " + adminName); // Retain admin name
                System.out.println("Contact Number\t\t: + 60" + contactNumber); // Retain Contact Number
                System.out.println("Gender (M/F)\t\t: " + gender); // Retain gender
                System.out.println("IC No\t\t\t: " + icNo);

                showAdminPositionList();

                System.out.print("Please enter a number (1-10) to select position: ");
                String input = scanner.nextLine();

                // Check for cancellation
                if (input.equalsIgnoreCase("X")) {
                    if (isExit(scanner, validationCheck)) {
                        return; // Exit entire method if user confirms
                    } else {
                        continue; // Continue looping if user chooses not to exit
                    }
                }

                try {
                    int choice = Integer.parseInt(input);
                    if (choice >= 1 && choice <= adminPositionList.length) {
                        position = adminPositionList[choice - 1]; // Assign selected position
                        isValid = true;
                    } else {
                        isValid = false;
                        System.out.println("Invalid number. Please choose between 1 and 10.");
                        System.out.print("Press any key to continue...");
                        scanner.nextLine();
                    }
                } catch (NumberFormatException e) {
                    isValid = false;
                    System.out.println("Invalid input. Please enter a number.");
                    System.out.print("Press any key to continue...");
                    scanner.nextLine();
                }
            } while (!isValid);

            // Validation Department
            do {
                clearScreen.clearConsole();
                menuInterface.addAdminIcon();
                System.out.println("Admin Name\t\t: " + adminName); // Retain admin name
                System.out.println("Contact Number\t\t: + 60" + contactNumber); // Retain Contact Number
                System.out.println("Gender (M/F)\t\t: " + gender); // Retain gender
                System.out.println("IC No\t\t\t: " + icNo);
                System.out.println("Position\t\t: " + position);

                showDepartmentList();

                System.out.print("Please enter a number (1-10) to select department: ");
                String input = scanner.nextLine();

                // Check for cancellation
                if (input.equalsIgnoreCase("X")) {
                    if (isExit(scanner, validationCheck)) {
                        return; // Exit method
                    } else {
                        continue; // Re-display list
                    }
                }

                try {
                    int choice = Integer.parseInt(input);
                    if (choice >= 1 && choice <= 10) {
                        department = departmentsList[choice - 1]; // Get department name
                        isValid = true;
                    } else {
                        isValid = false;
                        System.out.println("Invalid number. Please choose between 1 and 10.");
                        System.out.print("Press any key to continue...");
                        scanner.nextLine();
                    }
                } catch (NumberFormatException e) {
                    isValid = false;
                    System.out.println("Invalid input. Please enter a number.");
                    System.out.print("Press any key to continue...");
                    scanner.nextLine();
                }

            } while (isValid == false);

            // Create admin object with the selected faculty
            Admin newAdmin = new Admin(adminName, gender, icNo, contactNumber, position, department);
            adminList.add(newAdmin);

            // Display Admin information
            do {
                // Display student details
                clearScreen.clearConsole();
                menuInterface.addAdminIcon();
                System.out.println("\n============================== New Admin Added ===============================");
                newAdmin.displayInfo();
                System.out.println("------------------------------------------------------------------------------");

                // Ask if the user wants to continue
                System.out.print("\nDo you want to continue adding admin? [Y/N]: ");

                String input = scanner.nextLine().trim();
                if (input.length() == 1) {
                    continueOption = input.charAt(0);
                    isValid = validationCheck.validationYesNo(continueOption);
                } else {
                    isValid = false;
                    System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                }

                if (isValid == false) {
                    System.out.print("Please try again. Press any key to continue...");
                    scanner.nextLine(); // Wait for user input
                }

            } while (isValid == false);

        } while (continueOption == 'Y' || continueOption == 'y');
    }

    // Display Admin Record
    private void displayAdminRecord(ArrayList<Admin> adminList) {
        if (adminList.isEmpty()) {
            System.out.println("No admin records found.");
        } else {
            System.out.println(
                    "\n============================================================================= Admin Records ==============================================================================");
            System.out.printf("%-8s%-12s%-8s%-16s%-30s%-12s%-10s%-30s%-20s\n",
                    "ID", "Name", "Gender", "IC No", "Email", "Contact",
                    "Password", "Position", "Department");

            System.out.println(
                    "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            for (Admin admin : adminList) {
                if (admin.getIsActive()) {
                    System.out.printf("%-8s%-12s%-8s%-16s%-30s%-12s%-10s%-30s%-20s\n",
                            admin.getId(), admin.getName(), admin.getGender(),
                            admin.getIcNo(), admin.getEmail(),
                            admin.getPhoneNo(), admin.getPassword(), admin.getPosition(),
                            admin.getDepartment());
                    System.out.println(
                            "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                }
            }
        }
    }

    // Update Admin Record
    private void updateAdminRecord(Scanner scanner, ClearScreen clearScreen, ValidationCheck validationCheck,
            MenuList menuInterface, ArrayList<Student> studentList, ArrayList<Admin> adminList,
            ArrayList<FacultyMember> facultyMemberList) {

        boolean continueMenu;

        do {
            clearScreen.clearConsole();
            // Call the Admin Icon
            menuInterface.updateAdminIcon();
            displayAdminRecord(adminList);

            System.out.print("Enter Admin ID to update:");
            String fmID = scanner.nextLine().trim(); // Trim to remove extra spaces

            continueMenu = checkAdminID(fmID, adminList); // Check if admin ID exists

            if (continueMenu == true) {
                Admin selectedAdmin = null;

                // Find the Faculty Member in the list
                for (Admin admin : adminList) {
                    if (admin.getId().equals(fmID)) {
                        selectedAdmin = admin;
                        break;
                    }
                }

                // Display Admin details
                System.out.println("============================== Admin Found ===================================");
                selectedAdmin.displayInfo();
                System.out.println("------------------------------------------------------------------------------");

                boolean updateMore; // Control loop for multiple updates

                do {
                    try {
                        displayUpdateAdminScreen(clearScreen, menuInterface, selectedAdmin);

                        menuInterface.updateAdminSelectedMenu();
                        ;
                        System.out.print("Enter your choice: ");
                        int choice = scanner.nextInt();
                        scanner.nextLine();
                        boolean isValid = false;

                        switch (choice) {
                            case 1:
                                while (isValid == false) {

                                    displayUpdateAdminScreen(clearScreen, menuInterface, selectedAdmin);

                                    System.out.print("Enter new Name: ");
                                    String fmName = scanner.nextLine().toUpperCase(); // Convert input to uppercase

                                    // Validate Admin Name
                                    isValid = validationCheck.validationName(fmName);

                                    // If the name is invalid, prompt the user to re-enter
                                    if (isValid == true) {
                                        selectedAdmin.setName(fmName);
                                    } else {
                                        System.out.print("Please try again. Press enter key to continue...");
                                        scanner.nextLine(); // Wait for user input
                                    }
                                }
                                break;
                            case 2:
                                while (isValid == false) {
                                    displayUpdateAdminScreen(clearScreen, menuInterface, selectedAdmin);

                                    System.out.print("Enter new Gender (M/F): ");
                                    String fmGender = scanner.nextLine();

                                    // Validate Admin Gender
                                    isValid = validationCheck.validationGender(fmGender);

                                    // If the name is invalid, prompt the user to re-enter
                                    if (isValid == true) {
                                        // Assign the value M --> Male / F --> Female ignore case
                                        if (fmGender.equalsIgnoreCase("M")) {
                                            fmGender = "Male";
                                        } else if (fmGender.equalsIgnoreCase("F")) {
                                            fmGender = "Female";
                                        }
                                        // Assign the value to the data
                                        selectedAdmin.setGender(fmGender);
                                    } else if (isValid == false) {
                                        System.out.print("Please try again. Press enter key to continue...");
                                        scanner.nextLine(); // Wait for user input
                                    }
                                }
                                break;
                            case 3:
                                while (isValid == false) {
                                    displayUpdateAdminScreen(clearScreen, menuInterface, selectedAdmin);

                                    System.out.print("Enter new IC No: ");
                                    String fmIcNo = scanner.nextLine();

                                    // Validate Admin Ic No
                                    isValid = validationCheck.validationIcNo(fmIcNo, studentList, adminList,
                                            facultyMemberList);

                                    // If the name is invalid, prompt the user to re-enter
                                    if (isValid == true) {
                                        selectedAdmin.setIcNo(fmIcNo);
                                    } else {
                                        System.out.print("Please try again. Press enter key to continue...");
                                        scanner.nextLine(); // Wait for user input
                                    }
                                }
                                break;
                            case 4:
                                while (isValid == false) {
                                    displayUpdateAdminScreen(clearScreen, menuInterface, selectedAdmin);

                                    System.out.print("Enter new Email: ");
                                    String fmEmail = scanner.nextLine();

                                    // Validate Admin Email
                                    isValid = validationCheck.validationEmail(fmEmail);

                                    // If the name is invalid, prompt the user to re-enter
                                    if (isValid == true) {
                                        selectedAdmin.setEmail(fmEmail);
                                    } else {
                                        System.out.print("Please try again. Press enter key to continue...");
                                        scanner.nextLine(); // Wait for user input

                                    }
                                }
                                break;
                            case 5:
                                while (isValid == false) {
                                    displayUpdateAdminScreen(clearScreen, menuInterface, selectedAdmin);

                                    System.out.print("Enter new Contact: +60");
                                    String fmContact = scanner.nextLine();

                                    // Validate Admin Name
                                    isValid = validationCheck.validationContactNumber(fmContact, studentList, adminList,
                                            facultyMemberList);

                                    // If the name is invalid, prompt the user to re-enter
                                    if (isValid == true) {
                                        selectedAdmin.setPhoneNo(fmContact);
                                    } else {
                                        System.out.print("Please try again. Press enter key to continue...");
                                        scanner.nextLine(); // Wait for user input

                                    }
                                }
                                break;
                            case 6:
                                while (isValid == false) {
                                    displayUpdateAdminScreen(clearScreen, menuInterface, selectedAdmin);

                                    System.out.print("Enter new Password: ");
                                    String fmPassword = scanner.nextLine();

                                    // Validate Admin Password
                                    isValid = validationCheck.validationPassword(fmPassword);

                                    // If the name is invalid, prompt the user to re-enter
                                    if (isValid == true) {
                                        selectedAdmin.setPassword(fmPassword);
                                    } else {
                                        System.out.print("\nPlease try again. Press enter key to continue...");
                                        scanner.nextLine(); // Wait for user input
                                    }
                                }
                                break;

                            case 7: // Position
                                while (!isValid) {
                                    displayUpdateAdminScreen(clearScreen, menuInterface, selectedAdmin);

                                    showAdminPositionList();

                                    System.out.print("Please enter a number (1-10) to select new position: ");
                                    String input = scanner.nextLine();

                                    // Check for cancellation
                                    if (input.equalsIgnoreCase("X")) {
                                        if (isExit(scanner, validationCheck)) {
                                            return; // Exit entire method if user confirms
                                        } else {
                                            continue; // Continue looping if user chooses not to exit
                                        }
                                    }

                                    int selectedChoice = -1;
                                    try {
                                        selectedChoice = Integer.parseInt(input);
                                        if (selectedChoice >= 1 && selectedChoice <= adminPositionList.length) {
                                            String selectedPosition = adminPositionList[selectedChoice - 1];
                                            selectedAdmin.setPosition(selectedPosition);
                                            isValid = true;
                                        } else {
                                            System.out.println("Invalid number. Please choose between 1 and 10.");
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid input. Please enter a number.");
                                    }

                                    if (!isValid) {
                                        System.out.print("Please try again. Press enter key to continue...");
                                        scanner.nextLine(); // Wait for user input
                                    }
                                }
                                break;

                            case 8: // Department
                                while (!isValid) {
                                    displayUpdateAdminScreen(clearScreen, menuInterface, selectedAdmin);

                                    showDepartmentList();

                                    System.out.print("Please enter a number (1-10) to select the department: ");
                                    String input = scanner.nextLine();

                                    // Check for cancellation
                                    if (input.equalsIgnoreCase("X")) {
                                        if (isExit(scanner, validationCheck)) {
                                            return; // Exit entire method if user confirms
                                        } else {
                                            continue; // Continue looping if user chooses not to exit
                                        }
                                    }

                                    int selectedChoice = -1; // New variable to store the parsed input choice
                                    try {
                                        selectedChoice = Integer.parseInt(input);
                                        if (selectedChoice >= 1 && selectedChoice <= departmentsList.length) {
                                            // Set the selected department
                                            String selectedDepartment = departmentsList[selectedChoice - 1];
                                            selectedAdmin.setDepartment(selectedDepartment); // Assuming setDepartment()
                                                                                             // method exists in
                                                                                             // selectedFMember
                                            isValid = true; // Valid input
                                        } else {
                                            System.out.println("Invalid number. Please choose between 1 and 10.");
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid input. Please enter a number.");
                                    }

                                    if (!isValid) {
                                        System.out.print("Please try again. Press enter key to continue...");
                                        scanner.nextLine(); // Wait for user input
                                    }
                                }
                                break;
                            case 9:
                                System.out.println("Exiting update menu...");
                                updateMore = false;
                                continue; // Skip "Update successful" message when exiting

                            default:
                                System.out.println("Invalid choice! No updates made.");
                                System.out.println("Press Enter to continue...");
                                scanner.nextLine(); // Wait for user input
                                updateMore = true;
                                continue;
                        }

                        System.out.println("\nAdmin details updated successfully!");
                        System.out.println("Press Enter to continue...");
                        scanner.nextLine(); // Wait for user input

                        do {
                            clearScreen.clearConsole();
                            menuInterface.updateAdminIcon();

                            // Display updated admin details
                            System.out.println(
                                    "=========================== Updated Admin Details ===========================");
                            selectedAdmin.displayInfo();
                            System.out.println(
                                    "------------------------------------------------------------------------------");

                            System.out.print("\nDo you want to update another field? (Y/N): ");
                            char choiceContinue = scanner.next().charAt(0);
                            scanner.nextLine(); // Consume the newline

                            updateMore = (choiceContinue == 'Y' || choiceContinue == 'y');

                            isValid = validationCheck.validationYesNo(choiceContinue);

                            if (isValid == false) {
                                System.out.print("Please try again. Press enter key to continue...");
                                scanner.nextLine(); // Wait for user input
                            }

                        } while (isValid == false);

                    } catch (Exception ex) {
                        System.out.println("Error! Please enter a valid number.Press enter key to continue...");
                        scanner.nextLine();
                        scanner.nextLine();
                        updateMore = true;
                    }

                } while (updateMore == true); // Repeat update menu until the user exits

            } else {
                System.out
                        .println("The admin record was not found. Please double-check the Admin ID.");
                continueMenu = isExit(scanner, validationCheck);
            }

        } while (continueMenu == false); // Repeat until user decides to exit
    }

    private void displayUpdateAdminScreen(ClearScreen clearScreen, MenuList menuInterface, Admin admin) {
        clearScreen.clearConsole();
        menuInterface.updateAdminIcon();

        System.out.println("\n=============================== Admin Details ================================");
        admin.displayInfo();
        System.out.println("------------------------------------------------------------------------------");
    }

    private void deactivateAdmin(Scanner scanner, ClearScreen clearScreen, ValidationCheck validationCheck,
            MenuList menuInterface, ArrayList<Admin> adminList) {

        boolean continueMenu;

        do {
            clearScreen.clearConsole();
            menuInterface.deactivateAdminIcon();

            if (adminList.isEmpty()) {
                System.out.println("No admin records found.");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                return; // Exit the function if no admin
            }

            displayAdminRecord(adminList);

            System.out.print("Enter the Admin ID that you want to deactivate: ");
            String adminIdToDeactivate = scanner.nextLine();

            continueMenu = checkAdminID(adminIdToDeactivate, adminList);

            if (continueMenu == true) {
                Admin selectedAdmin = null;

                for (Admin admin : adminList) {
                    if (admin.getId().equals(adminIdToDeactivate)) {
                        selectedAdmin = admin;
                        break;
                    }
                }

                if (selectedAdmin != null) {
                    clearScreen.clearConsole();
                    menuInterface.deactivateAdminIcon();
                    System.out
                            .println(
                                    "\n================================ Admin Found =================================");
                    selectedAdmin.displayInfo();
                    System.out.println(
                            "------------------------------------------------------------------------------\n");

                    System.out.print("Are you sure you want to deactivate this admin? [Y/N]: ");
                    char confirmDeactivate = scanner.next().charAt(0);
                    scanner.nextLine();

                    if (confirmDeactivate == 'Y' || confirmDeactivate == 'y') {
                        selectedAdmin.setActive(false);
                        System.out.println(
                                "\nAdmin with ID " + selectedAdmin.getId() + " has been deactivated.");
                    } else {
                        System.out.println("Deactivate canceled.");
                    }

                    System.out.println("Press Enter to continue...");
                    scanner.nextLine();
                }

            } else {
                System.out
                        .println("The admin record was not found. Please double-check the admin ID.");
                continueMenu = isExit(scanner, validationCheck);
            }

        } while (continueMenu == false); // Keep looping if user wants to try again
    }

    private void activateAdmin(Scanner scanner, ClearScreen clearScreen, ValidationCheck validationCheck,
            MenuList menuInterface, ArrayList<Admin> adminList) {

        boolean continueMenu;

        do {
            clearScreen.clearConsole();
            menuInterface.activateAdminIcon();

            // Gather all deactivated admins
            ArrayList<Admin> deactivatedAdmins = new ArrayList<>();

            for (Admin admin : adminList) {
                if (!admin.getIsActive()) {
                    deactivatedAdmins.add(admin);
                }
            }

            // If no deactivated admins
            if (deactivatedAdmins.isEmpty()) {
                System.out.println("No deactivated admins found.");
                System.out.println("Press Enter to return...");
                scanner.nextLine();
                return;
            }

            // Display deactivated admins
            System.out.println("================================ Deactivated Admins ================================");
            System.out.printf("%-4s%-15s%-25s\n", "No", "Admin ID", "Admin Name");
            System.out.println("-------------------------------------------------------------------------------------");

            for (int i = 0; i < deactivatedAdmins.size(); i++) {
                Admin admin = deactivatedAdmins.get(i);
                System.out.printf("%-4d%-15s%-25s\n", i + 1, admin.getId(), admin.getName());
            }

            System.out.println("-------------------------------------------------------------------------------------");
            System.out.print("Enter the Admin ID you want to activate: ");
            String adminIdToActivate = scanner.nextLine();
            Admin selectedAdmin = null;

            for (Admin admin : deactivatedAdmins) {
                if (admin.getId().equalsIgnoreCase(adminIdToActivate)) {
                    selectedAdmin = admin;
                    break;
                }
            }

            if (selectedAdmin != null) {
                clearScreen.clearConsole();
                menuInterface.activateAdminIcon();
                System.out.println("============================= Admin Found ===============================");
                selectedAdmin.displayInfo();
                System.out.println("---------------------------------------------------------------------------");

                System.out.print("Are you sure you want to activate this admin? [Y/N]: ");
                char confirmActivate = scanner.next().charAt(0);
                scanner.nextLine(); // consume newline

                if (confirmActivate == 'Y' || confirmActivate == 'y') {
                    selectedAdmin.setActive(true);
                    System.out.println("\nAdmin with ID " + selectedAdmin.getId() + " has been activated.");
                } else {
                    System.out.println("Activation canceled.");
                }

                System.out.println("Press Enter to continue...");
                scanner.nextLine();
                continueMenu = false; // Stop loop after processing
            } else {
                System.out.println("The Admin ID was not found in the deactivated list.");
                continueMenu = isExit(scanner, validationCheck); // Ask if user wants to try again
            }

        } while (continueMenu == false); // Keep looping if user wants to try again
    }

    // ======================================================
    // EXTRA check function
    // =======================================================

    // Check Student ID
    public boolean checkStudentID(String id, ArrayList<Student> studentList) {
        for (Student student : studentList) {
            if (student.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    // Check Faculty Member ID
    public boolean checkFmID(String fmID, ArrayList<FacultyMember> facultyMemberList) {
        for (FacultyMember facultyMember : facultyMemberList) {
            if (facultyMember.getId().equals(fmID)) {
                return true;
            }
        }
        return false;
    }

    // Check Admin ID
    public boolean checkAdminID(String adminId, ArrayList<Admin> adminList) {
        for (Admin admin : adminList) {
            if (admin.getId().equals(adminId)) {
                return true;
            }
        }
        return false;
    }

    // isExit method to handle invalid long inputs and overwrite lines properly
    public boolean isExit(Scanner scanner, ValidationCheck validationCheck) {
        boolean isValid = false;
        char exitOption;

        do {
            System.out.print("Do you want to exit this page [Y/N]: ");
            String input = scanner.next().trim();
            scanner.nextLine(); // Consume newline

            if (input.length() == 1) {
                exitOption = input.charAt(0);
                isValid = validationCheck.validationYesNo(exitOption);

                if (isValid) {
                    if (exitOption == 'Y' || exitOption == 'y') {
                        return true;
                    } else if (exitOption == 'N' || exitOption == 'n') {
                        return false;
                    }
                }
            }

            if (!isValid) {
                // Save current position by moving cursor up
                System.out.print("\033[1A"); // Move cursor up one line
                System.out.print("\rInvalid input. Please enter only one character (Y/N).     ");
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
                // Clear both the error message and "Press Enter" line
                System.out.print("\033[1A"); // Move up to "Press Enter" line
                System.out.print("\r\033[K"); // Clear line
                System.out.print("\033[1A"); // Move up to error line
                System.out.print("\r\033[K"); // Clear line
            }
        } while (!isValid);

        return false;
    }

    // Check Student ID
    public boolean checkStudentID_Login(String id, ArrayList<Student> studentList, Scanner scanner) {
        for (Student student : studentList) {
            if (student.getId().equals(id)) {
                return true;
            }
        }
        System.out.println("Wrong Student ID. Please try again.");
        System.out.print("Press Enter to continue...");
        scanner.nextLine(); // Wait for user input
        return false;
    }

    // Check Student Password
    public boolean checkStudentPassword_Login(String id, String password, ArrayList<Student> studentList) {
        for (Student student : studentList) {
            if (student.getId().equals(id) && student.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkFmPassword_Login(String fmID, String password, ArrayList<FacultyMember> facultyMemberList) {
        for (FacultyMember facultyMember : facultyMemberList) {
            if (facultyMember.getId().equals(fmID) && facultyMember.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkAmPassword_Login(String amID, String password, ArrayList<Admin> adminList) {
        for (Admin adminMember : adminList) {
            if (adminMember.getId().equals(amID) && adminMember.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    // Show department list
    public void showDepartmentList() {
        System.out.println("\nPlease select a department:");
        for (int i = 0; i < departmentsList.length; i++) {
            System.out.println("[" + (i + 1) + "] " + departmentsList[i]);
        }
    }

    // Show position list
    public void showPositionList() {
        System.out.println("\nPlease select a position:");
        for (int i = 0; i < positionList.length; i++) {
            System.out.println("[" + (i + 1) + "] " + positionList[i]);
        }
    }

    // Show admin position list
    public void showAdminPositionList() {
        System.out.println("\nPlease select an admin position:");
        for (int i = 0; i < adminPositionList.length; i++) {
            System.out.println("[" + (i + 1) + "] " + adminPositionList[i]);
        }
    }

}