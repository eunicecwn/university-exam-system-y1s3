package CourseController;

import java.util.ArrayList;
import java.util.Scanner;
import ExtraFunction.*;
import UserController.*;

public class CourseController {
    public void displayFacultyInterface(Scanner scanner, ArrayList<Faculty> facultyList,
            ValidationCheck validationCheck, ClearScreen clearScreen, MenuList menuInterface) {

        // Variable
        boolean continueFaculty = true;

        UserController userController = new UserController();

        do {
            clearScreen.clearConsole(); // Call the clear screen function
            menuInterface.facultyMenu(); // Call the course menu
            try {

                int optionFaculty = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (optionFaculty) {
                    case 1: // Add Faculty
                        insertFaculty(scanner, menuInterface, facultyList, validationCheck, clearScreen,
                                userController);
                        break;
                    case 2: // Faculty Overview
                        clearScreen.clearConsole();
                        displayAllFaculty(facultyList);
                        System.out.println("Press Enter to continue...");
                        scanner.nextLine(); // Wait so user can read
                        break;
                    case 3: // Update faculty
                        updateFaculty(scanner, clearScreen, validationCheck, menuInterface, facultyList,
                                userController);
                        System.out.println("Press Enter to continue...");
                        scanner.nextLine(); // Wait so user can read
                        break;
                    case 4: // Deactivate faculty
                        deactivateFaculty(scanner, clearScreen, validationCheck, menuInterface, facultyList,
                                userController);
                        break;
                    case 5: // Activate faculty
                        activateFaculty(scanner, clearScreen, validationCheck, menuInterface, facultyList,
                                userController);
                        break;
                    case 6: // Return to LCCN main menu
                        continueFaculty = false;
                        break;
                    default:
                        System.out.println("Error! Please enter a number between 1 - 6 to proceed.");
                        System.out.println("Press Enter to continue...");
                        scanner.nextLine(); // Wait for user input
                }

            } catch (Exception ex) {
                System.out.println("Error! Please enter a numeric number to proceed");
                System.out.println("Press Enter to continue...");
                scanner.nextLine(); // Wait for user input
                scanner.nextLine(); // Consume the newline character

            }

        } while (continueFaculty);
    }

    // ========================================================================
    // FACULTY
    // ========================================================================

    public void insertFaculty(Scanner scanner, MenuList menuInterface, ArrayList<Faculty> facultyList,
            ValidationCheck validationCheck, ClearScreen clearScreen, UserController userController) {
        char continueOption = 'Y';

        do {
            boolean isValid = false;
            String facultyCode = "";
            String facultyName = "";

            do {
                clearScreen.clearConsole();
                menuInterface.addFacultyIcon();
                System.out.print("Faculty Code\t:");
                facultyCode = scanner.nextLine().toUpperCase(); // Convert input to uppercase;

                // Check for cancellation
                if (facultyCode.equals("X")) {
                    if (userController.isExit(scanner, validationCheck)) {
                        return; // Exit entire method if user confirms
                    } else {
                        continue; // Continue looping if user chooses not to exit
                    }
                }

                isValid = validationCheck.validationFacultyCode(facultyCode);

                // Inline check for duplicate
                if (isValid) {
                    for (Faculty faculty : facultyList) {
                        if (faculty.getFacultyCode().equalsIgnoreCase(facultyCode)) {
                            System.out.println("Invalid input. Faculty code already exists.");
                            isValid = false;
                            break;
                        }
                    }
                }

                if (isValid == false) {
                    System.out.print("Please try again. Press any key to continue...");
                    scanner.nextLine(); // Wait for user input
                }
            } while (isValid == false);

            do {
                clearScreen.clearConsole();
                menuInterface.addFacultyIcon();
                System.out.println("Faculty Code\t: " + facultyCode);
                System.out.print("Faculty Name\t:");
                facultyName = scanner.nextLine();

                // Check for cancellation
                if (facultyName.equals("X")) {
                    if (userController.isExit(scanner, validationCheck)) {
                        return; // Exit entire method if user confirms
                    } else {
                        continue; // Continue looping if user chooses not to exit
                    }
                }

                isValid = validationCheck.validationName(facultyName);

                if (isValid) {
                    // Check for duplicate
                    for (Faculty faculty : facultyList) {
                        if (faculty.getFacultyName().equalsIgnoreCase(facultyName)) {
                            System.out.println("Invalid input. Faculty already exists!");
                            isValid = false;
                            break;
                        }
                    }
                }

                if (isValid == false) {
                    System.out.print("Please try again. Press any key to continue...");
                    scanner.nextLine(); // Wait for user input
                }
            } while (isValid == false);

            // Add new faculty
            Faculty newFaculty = new Faculty(facultyCode, facultyName);
            facultyList.add(newFaculty);

            do {
                // Display faculty details
                clearScreen.clearConsole();
                menuInterface.addFacultyIcon();
                System.out.println("\n=============================== Faculty Added ================================");
                System.out.printf("%-15s%-15s\n", "Faculty Code", "Faculty Name");
                System.out.println("==============================================================================");
                newFaculty.displayInfo();
                System.out.println("------------------------------------------------------------------------------");

                // Ask if the user wants to continue
                System.out.print("Do you want to continue adding faculty? [Y/N]: ");
                continueOption = scanner.next().charAt(0);
                scanner.nextLine(); // Consume the newline character

                isValid = validationCheck.validationYesNo(continueOption);

                if (isValid == false) {
                    System.out.print("Please try again. Press any key to continue...");
                    scanner.nextLine(); // Wait for user input
                }

            } while (isValid == false);
        } while (continueOption == 'Y' || continueOption == 'y');
    }

    public void displayAllFaculty(ArrayList<Faculty> facultyList) {
        if (facultyList.isEmpty()) {
            System.out.println("No faculty records found.");
        } else {
            System.out.println("\n================================= All Faculty =================================");
            System.out.printf("%-15s%-15s\n", "Faculty Code", "Faculty Name");
            System.out.println("===============================================================================");
            // Loop through the faculty list and display each faculty's details
            for (Faculty faculty : facultyList) {
                if (faculty.getisActive()) {
                    faculty.displayInfo(); // This will call the displayInfo() method to show faculty details
                    System.out
                            .println("-------------------------------------------------------------------------------");
                }
            }
        }
    }

    // Update faculty
    private void updateFaculty(Scanner scanner, ClearScreen clearScreen, ValidationCheck validationCheck,
            MenuList menuInterface, ArrayList<Faculty> facultyList, UserController userController) {

        boolean continueMenu;

        do {
            clearScreen.clearConsole();
            menuInterface.updateFacultyIcon();
            displayAllFaculty(facultyList);

            System.out.print("\nEnter Faculty Code to update: ");
            String facultyCode = scanner.nextLine().trim();

            continueMenu = checkFacultyCode(facultyCode, facultyList); // Check if faculty code exists

            if (continueMenu == true) {
                Faculty selectedFaculty = null;

                // Find the faculty by code
                for (Faculty faculty : facultyList) {
                    if (faculty.getFacultyCode().equalsIgnoreCase(facultyCode)) {
                        selectedFaculty = faculty;
                        break;
                    }
                }

                // Faculty found
                System.out
                        .println("=============================== Faculty Found ================================");
                System.out.printf("%-15s%-15s\n", "Faculty Code", "Faculty Name");
                System.out
                        .println("==============================================================================");
                selectedFaculty.displayInfo();
                System.out
                        .println("------------------------------------------------------------------------------");

                boolean updateMore;

                do {
                    try {
                        clearScreen.clearConsole();
                        menuInterface.updateFacultyIcon();
                        System.out
                                .println(
                                        "=============================== Faculty Found ================================");
                        System.out.printf("%-15s%-15s\n", "Faculty Code", "Faculty Name");
                        System.out
                                .println(
                                        "==============================================================================");
                        selectedFaculty.displayInfo();
                        System.out
                                .println(
                                        "------------------------------------------------------------------------------");
                        menuInterface.updateFacultySelectedMenu();
                        System.out.print("Enter your choice: ");
                        int choice = scanner.nextInt();
                        scanner.nextLine(); // consume newline
                        boolean isValid = false;

                        switch (choice) {
                            case 1: // Update Faculty Code
                                while (isValid == false) {

                                    clearScreen.clearConsole();
                                    menuInterface.updateFacultyIcon();

                                    System.out
                                            .println(
                                                    "=============================== Faculty Found ================================");
                                    System.out.printf("%-15s%-15s\n", "Faculty Code", "Faculty Name");
                                    System.out
                                            .println(
                                                    "==============================================================================");
                                    selectedFaculty.displayInfo();
                                    System.out
                                            .println(
                                                    "------------------------------------------------------------------------------");

                                    System.out.print("Enter new Faculty Code: ");
                                    facultyCode = scanner.nextLine().toUpperCase();

                                    isValid = validationCheck.validationFacultyCode(facultyCode);

                                    if (isValid) {
                                        for (Faculty faculty : facultyList) {
                                            if (faculty.getFacultyCode().equalsIgnoreCase(facultyCode)) {
                                                System.out.println("Invalid input. Faculty code already exists.");
                                                isValid = false;
                                                break;
                                            }
                                        }
                                    }

                                    if (isValid == true) {
                                        selectedFaculty.setFacultyCode(facultyCode);
                                    } else {
                                        System.out.print("Please try again. Press enter key to continue...");
                                        scanner.nextLine();
                                    }
                                }
                                break;

                            case 2: // Update Faculty Name
                                while (isValid == false) {
                                    clearScreen.clearConsole();
                                    menuInterface.updateFacultyIcon();

                                    System.out
                                            .println(
                                                    "=============================== Faculty Found ================================");
                                    System.out.printf("%-15s%-15s\n", "Faculty Code", "Faculty Name");
                                    System.out
                                            .println(
                                                    "==============================================================================");
                                    selectedFaculty.displayInfo();
                                    System.out
                                            .println(
                                                    "------------------------------------------------------------------------------");

                                    System.out.print("Enter new Faculty Name: ");
                                    String facultyName = scanner.nextLine();

                                    isValid = validationCheck.validationName(facultyName);

                                    if (isValid) {
                                        // Check for duplicate
                                        for (Faculty faculty : facultyList) {
                                            if (faculty.getFacultyName().equalsIgnoreCase(facultyName)) {
                                                System.out.println("Invalid input. Faculty already exists!");
                                                isValid = false;
                                                break;
                                            }
                                        }
                                    }

                                    if (isValid) {
                                        selectedFaculty.setFacultyName(facultyName);
                                    } else {
                                        System.out.print("Please try again. Press enter key to continue...");
                                        scanner.nextLine();
                                    }
                                }
                                break;

                            case 3: // Exit update menu
                                System.out.println("Exiting update menu...");
                                updateMore = false;
                                continue;

                            default:
                                System.out.println("Invalid choice! No updates made.");
                                System.out.println("Press Enter to continue...");
                                scanner.nextLine();
                                updateMore = true;
                                continue;
                        }

                        System.out.println("\nFaculty details updated successfully!");
                        System.out.println("Press Enter to continue...");
                        scanner.nextLine();

                        do {
                            clearScreen.clearConsole();
                            menuInterface.updateFacultyIcon();

                            System.out.println(
                                    "============================== Updated Faculty ===============================");
                            System.out.printf("%-15s%-15s\n", "Faculty Code", "Faculty Name");
                            System.out.println(
                                    "==============================================================================");
                            selectedFaculty.displayInfo();
                            System.out.println(
                                    "------------------------------------------------------------------------------");

                            System.out.print("\nDo you want to update another field? (Y/N): ");
                            char choiceContinue = scanner.next().charAt(0);
                            scanner.nextLine();

                            updateMore = (choiceContinue == 'Y' || choiceContinue == 'y');
                            isValid = validationCheck.validationYesNo(choiceContinue);

                            if (isValid == false) {
                                System.out.print("Please try again. Press enter key to continue...");
                                scanner.nextLine();
                            }

                        } while (isValid == false);

                    } catch (Exception ex) {
                        System.out.println("Error! Please enter a valid number. Press enter key to continue...");
                        scanner.nextLine();
                        scanner.nextLine();
                        updateMore = true;
                    }

                } while (updateMore == true);

            } else {
                System.out.println("The faculty record was not found. Please double-check the Faculty Code.");
                continueMenu = userController.isExit(scanner, validationCheck);
            }

        } while (continueMenu == false);
    }

    private void deactivateFaculty(Scanner scanner, ClearScreen clearScreen, ValidationCheck validationCheck,
            MenuList menuInterface, ArrayList<Faculty> facultyList, UserController userController) {

        boolean continueMenu;

        do {
            clearScreen.clearConsole();
            menuInterface.deactivateFacultyIcon();

            if (facultyList.isEmpty()) {
                System.out.println("No faculty records found.");
                System.out.println("Press Enter to continue...");
                scanner.nextLine();
                return; // Exit the function if no faculty
            }

            displayAllFaculty(facultyList); // Show all faculty

            System.out.print("Enter the Faculty Code that you want to deactivate: ");
            String facultyCodeToDeactivate = scanner.nextLine().toUpperCase();

            continueMenu = checkFacultyCode(facultyCodeToDeactivate, facultyList);

            if (continueMenu == true) {
                Faculty selectedFaculty = null;

                // Assign the faculty detail to selected faculty
                for (Faculty faculty : facultyList) {
                    if (faculty.getFacultyCode().equalsIgnoreCase(facultyCodeToDeactivate)) {
                        selectedFaculty = faculty;
                        break;
                    }
                }

                if (selectedFaculty != null) {
                    clearScreen.clearConsole();
                    menuInterface.deactivateFacultyIcon();
                    System.out
                            .println("================================ Faculty Found ===============================");
                    System.out.printf("%-15s%-15s\n", "Faculty Code", "Faculty Name");
                    System.out
                            .println("==============================================================================");
                    System.out.printf("%-15s%-15s\n", selectedFaculty.getFacultyCode(),
                            selectedFaculty.getFacultyName());
                    System.out
                            .println("-----------------------------------------------------------------------------");

                    System.out.print("Are you sure you want to deactivate this faculty? [Y/N]: ");
                    char confirmDeactivate = scanner.next().charAt(0);
                    scanner.nextLine();

                    if (confirmDeactivate == 'Y' || confirmDeactivate == 'y') {
                        selectedFaculty.setActive(false);
                        System.out.println(
                                "\nFaculty with code " + selectedFaculty.getFacultyCode() + " has been deactivated.");
                    } else {
                        System.out.println("Deactivate canceled.");
                    }

                    System.out.println("Press Enter to continue...");
                    scanner.nextLine();
                }

            } else {
                System.out.println("The faculty code was not found. Please double-check the Faculty Code.");
                continueMenu = userController.isExit(scanner, validationCheck);
            }

        } while (continueMenu == false); // Keep looping if user wants to try again

    }

    private void activateFaculty(Scanner scanner, ClearScreen clearScreen, ValidationCheck validationCheck,
            MenuList menuInterface, ArrayList<Faculty> facultyList, UserController userController) {

        boolean continueMenu;

        do {
            clearScreen.clearConsole();
            menuInterface.activateFacultyIcon();

            // Filter deactivated faculty
            ArrayList<Faculty> deactivatedFacultyList = new ArrayList<>();
            for (Faculty faculty : facultyList) {
                if (!faculty.getisActive()) {
                    deactivatedFacultyList.add(faculty);
                }
            }

            if (deactivatedFacultyList.isEmpty()) {
                System.out.println("No deactivated faculty records found.");
                System.out.println("Press Enter to continue...");
                scanner.nextLine();
                return;
            }

            // Display deactivated faculty
            System.out.println("================================ Deactivated Faculty ================================");
            System.out.printf("%-4s%-15s%-15s\n", "No", "Faculty Code", "Faculty Name");
            System.out
                    .println("--------------------------------------------------------------------------------------");

            for (int i = 0; i < deactivatedFacultyList.size(); i++) {
                Faculty faculty = deactivatedFacultyList.get(i);
                System.out.printf("%-4d%-15s%-15s\n", i + 1, faculty.getFacultyCode(), faculty.getFacultyName());
            }

            System.out
                    .println("--------------------------------------------------------------------------------------");
            System.out.print("Enter the Faculty Code you want to activate: ");
            String facultyCodeToActivate = scanner.nextLine().toUpperCase();

            Faculty selectedFaculty = null;
            for (Faculty faculty : deactivatedFacultyList) {
                if (faculty.getFacultyCode().equalsIgnoreCase(facultyCodeToActivate)) {
                    selectedFaculty = faculty;
                    break;
                }
            }

            if (selectedFaculty != null) {
                clearScreen.clearConsole();
                menuInterface.activateFacultyIcon();
                System.out.println("============================= Faculty Found ==============================");
                System.out.printf("%-15s%-15s\n", "Faculty Code", "Faculty Name");
                System.out.println("=========================================================================");
                System.out.printf("%-15s%-15s\n", selectedFaculty.getFacultyCode(), selectedFaculty.getFacultyName());
                System.out.println("-------------------------------------------------------------------------");

                System.out.print("Are you sure you want to activate this faculty? [Y/N]: ");
                char confirmActivate = scanner.next().charAt(0);
                scanner.nextLine(); // consume newline

                if (confirmActivate == 'Y' || confirmActivate == 'y') {
                    selectedFaculty.setActive(true);
                    System.out.println(
                            "\nFaculty with code " + selectedFaculty.getFacultyCode() + " has been activated.");
                } else {
                    System.out.println("Activation canceled.");
                }

                System.out.println("Press Enter to continue...");
                scanner.nextLine();
                continueMenu = false;

            } else {
                System.out.println("The faculty code was not found in the deactivated list.");
                continueMenu = !userController.isExit(scanner, validationCheck);
            }

        } while (continueMenu);
    }

    // ========================================================================
    // COURSE
    // ========================================================================

    public void displayCourseInterface(Scanner scanner, ClearScreen clearScreen, ValidationCheck validationCheck,
            MenuList menuInterface, ArrayList<Faculty> facultyList) {

        // Variable
        boolean continueCourse = true;

        UserController userController = new UserController();

        do {
            clearScreen.clearConsole();
            menuInterface.courseMenu();
            try {

                int optionCourse = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (optionCourse) {
                    case 1: // Add course
                        insertCourseRecord(scanner, clearScreen, validationCheck, menuInterface, userController,
                                facultyList);
                        break;
                    case 2: // Course Overview
                        clearScreen.clearConsole();
                        displayCourseOverview(facultyList);
                        System.out.println("Press Enter to continue...");
                        scanner.nextLine(); // Wait for user input
                        break;
                    case 3: // Update course
                        updateCourse(scanner, clearScreen, validationCheck, menuInterface, facultyList, userController);
                        break;
                    case 4: // Deactiavte course
                        deactivateCourse(scanner, clearScreen, validationCheck, menuInterface, facultyList,
                                userController);
                        break;
                    case 5: // Activate course
                        activateCourse(scanner, clearScreen, validationCheck, menuInterface, facultyList,
                                userController);
                        break;
                    case 6: // Return to LCCN main menu
                        continueCourse = false;
                        break;
                    default:
                        System.out.println("Error! Please enter a number between 1 - 6 to proceed.");
                        System.out.println("Press Enter to continue...");
                        scanner.nextLine(); // Wait for user input
                }

            } catch (Exception ex) {
                System.out.println("Error! Please enter a numeric number to proceed");
                System.out.println("Press Enter to continue...");
                scanner.nextLine(); // Wait for user input
                scanner.nextLine(); // Consume the newline character
            }

        } while (continueCourse);
    }

    private void insertCourseRecord(Scanner scanner, ClearScreen clearScreen, ValidationCheck validationCheck,
            MenuList menuInterface, UserController userController, ArrayList<Faculty> facultyList) {

        String courseName = "";
        boolean isValid;
        char continueOption = 'Y';

        do {
            // Display available faculties
            int choice = -1;

            do {
                clearScreen.clearConsole();
                menuInterface.addCourseIcon();

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
                            isValid = true;
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

            // Get course name
            Faculty selectedFaculty = facultyList.get(choice - 1);

            do {
                clearScreen.clearConsole();
                menuInterface.addCourseIcon();

                System.out.print("Course Name\t: ");
                courseName = scanner.nextLine();

                if (courseName.equalsIgnoreCase("X")) {
                    if (userController.isExit(scanner, validationCheck)) {
                        return;
                    } else {
                        continue;
                    }
                }

                isValid = validationCheck.validationName(courseName);

                if (isValid) {
                    // Check for duplicate
                    for (Course course : selectedFaculty.getCourseList()) {
                        if (course.getCourseName().equalsIgnoreCase(courseName)) {
                            System.out.println("Invalid input. Course already exists!");
                            isValid = false;
                            break;
                        }
                    }
                }

                if (!isValid) {
                    System.out.print("Please try again. Press any key to continue...");
                    scanner.nextLine();
                }
            } while (!isValid);

            // Add course to faculty
            Course addCourse = new Course(courseName);
            selectedFaculty.addCourse(addCourse);

            do {
                clearScreen.clearConsole();
                menuInterface.addCourseIcon();
                System.out.println("\n============================ New Course Added ===============================");
                addCourse.displayInfo();
                System.out.println("Faculty\t\t: " + selectedFaculty.getFacultyName() + " (Code:"
                        + selectedFaculty.getFacultyCode() + ")");
                System.out.println("------------------------------------------------------------------------------");

                System.out.print("\nDo you want to continue adding Course? [Y/N]: ");
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
                    scanner.nextLine();
                }

            } while (isValid == false);

        } while (continueOption == 'Y' || continueOption == 'y');
    }

    public void displayCourseOverview(ArrayList<Faculty> facultyList) {
        System.out.println("=============================== All Course Record ================================");
        for (Faculty faculty : facultyList) {
            if (faculty.getisActive()) {
                System.out.println("\nFaculty: " + faculty.getFacultyCode() + " - " + faculty.getFacultyName());
                System.out
                        .println("----------------------------------------------------------------------------------");

                boolean hasActiveCourse = false;
                for (Course course : faculty.getCourseList()) {
                    if (course.getisActive()) {
                        if (!hasActiveCourse) {
                            System.out.printf("%-12s%-50s %-15s\n", "Course ID", "Course Name", "Credit Hours");
                            System.out.println(
                                    "----------------------------------------------------------------------------------");
                            hasActiveCourse = true;
                        }
                        System.out.printf("%-12s %-50s %-15d\n",
                                course.getCourseID(),
                                course.getCourseName(),
                                course.getCreditHours());
                    }
                }

                if (!hasActiveCourse) {
                    System.out.println("No active courses available for this faculty.");
                }
            }
        }
    }

    private void updateCourse(Scanner scanner, ClearScreen clearScreen, ValidationCheck validationCheck,
            MenuList menuInterface, ArrayList<Faculty> facultyList, UserController userController) {

        boolean continueMenu;

        do {
            clearScreen.clearConsole();
            menuInterface.updateCourseIcon();
            displayCourseOverview(facultyList);

            System.out.print("\nEnter Course ID to update: ");
            String courseID = scanner.nextLine().trim();

            continueMenu = checkCourseID(courseID, facultyList); // Check if faculty code exists

            if (continueMenu == true) {
                Course selectedCourse = null;

                // Find the faculty by code
                for (Faculty faculty : facultyList) {
                    for (Course course : faculty.getCourseList()) {
                        if (course.getCourseID().equalsIgnoreCase(courseID)) {
                            selectedCourse = course;
                            break;
                        }
                    }
                }
                // Faculty found
                System.out
                        .println("=============================== Course Found ================================");
                selectedCourse.displayInfo();
                System.out
                        .println("------------------------------------------------------------------------------");

                boolean updateMore;

                do {
                    try {
                        clearScreen.clearConsole();
                        menuInterface.updateCourseIcon();
                        System.out
                                .println(
                                        "=============================== Course Found ================================");
                        selectedCourse.displayInfo();
                        System.out
                                .println(
                                        "------------------------------------------------------------------------------");
                        menuInterface.updateCourseSelectedMenu();
                        System.out.print("Enter your choice: ");
                        int choice = scanner.nextInt();
                        scanner.nextLine(); // consume newline
                        boolean isValid = false;

                        switch (choice) {
                            case 1: // Update Course Name
                                while (isValid == false) {

                                    clearScreen.clearConsole();
                                    menuInterface.updateCourseIcon();

                                    System.out
                                            .println(
                                                    "=============================== Course Found ================================");
                                    selectedCourse.displayInfo();
                                    System.out
                                            .println(
                                                    "------------------------------------------------------------------------------");

                                    System.out.print("Enter new Course Name: ");
                                    String courseName = scanner.nextLine();

                                    isValid = validationCheck.validationName(courseName);

                                    if (isValid) {
                                        // Check for duplicate
                                        for (Faculty faculty : facultyList) {
                                            for (Course course : faculty.getCourseList()) {
                                                if (course.getCourseName().equalsIgnoreCase(courseName)) {
                                                    System.out.println("Invalid input. Course already exists!");
                                                    isValid = false;
                                                    break;
                                                }
                                            }
                                        }
                                    }

                                    if (isValid == true) {
                                        selectedCourse.setCourseName(courseName);

                                    } else {
                                        System.out.print("Please try again. Press enter key to continue...");
                                        scanner.nextLine();
                                    }
                                }
                                break;

                            case 2: // Exit update menu
                                System.out.println("Exiting update menu...");
                                updateMore = false;
                                continue;

                            default:
                                System.out.println("Invalid choice! No updates made.");
                                System.out.println("Press Enter to continue...");
                                scanner.nextLine();
                                updateMore = true;
                                continue;
                        }

                        System.out.println("\nCourse details updated successfully!");
                        System.out.println("Press Enter to continue...");
                        scanner.nextLine();

                        do {
                            clearScreen.clearConsole();
                            menuInterface.updateCourseIcon();

                            System.out.println(
                                    "============================== Updated Course ===============================");
                            selectedCourse.displayInfo();
                            System.out.println(
                                    "------------------------------------------------------------------------------");

                            System.out.print("\nDo you want to update another field? (Y/N): ");
                            char choiceContinue = scanner.next().charAt(0);
                            scanner.nextLine();

                            updateMore = (choiceContinue == 'Y' || choiceContinue == 'y');
                            isValid = validationCheck.validationYesNo(choiceContinue);

                            if (isValid == false) {
                                System.out.print("Please try again. Press enter key to continue...");
                                scanner.nextLine();
                            }

                        } while (isValid == false);

                    } catch (Exception ex) {
                        System.out.println("Error! Please enter a valid number. Press enter key to continue...");
                        scanner.nextLine();
                        scanner.nextLine();
                        updateMore = true;
                    }

                } while (updateMore == true);

            } else {
                System.out.println("The faculty record was not found. Please double-check the Faculty Code.");
                continueMenu = userController.isExit(scanner, validationCheck);
            }

        } while (continueMenu == false);
    }

    private void deactivateCourse(Scanner scanner, ClearScreen clearScreen, ValidationCheck validationCheck,
            MenuList menuInterface, ArrayList<Faculty> facultyList, UserController userController) {

        boolean continueMenu;

        do {
            clearScreen.clearConsole();
            menuInterface.deactivateCourseIcon();

            for (Faculty faculty : facultyList) {
                if (faculty.getCourseList().isEmpty()) {
                    System.out.println("No course records found.");
                    System.out.println("Press Enter to continue...");
                    scanner.nextLine();
                    return; // Exit the function if no faculty
                }
            }

            displayCourseOverview(facultyList); // Show all faculty

            System.out.print("\nEnter the course ID that you want to deactivate: ");
            String courseIDToDeactivate = scanner.nextLine().toUpperCase();

            continueMenu = checkCourseID(courseIDToDeactivate, facultyList);

            if (continueMenu == true) {
                Course selectedCourse = null;

                for (Faculty faculty : facultyList) {
                    for (Course course : faculty.getCourseList()) {
                        if (course.getCourseID().equalsIgnoreCase(courseIDToDeactivate)) {
                            selectedCourse = course;
                            break;
                        }
                    }
                }

                if (selectedCourse != null) {
                    clearScreen.clearConsole();
                    menuInterface.deactivateCourseIcon();
                    System.out
                            .println("================================ Course Found ===============================");
                    selectedCourse.displayInfo();
                    System.out
                            .println("-----------------------------------------------------------------------------");

                    System.out.print("Are you sure you want to deactivate this course? [Y/N]: ");
                    char confirmDeactivate = scanner.next().charAt(0);
                    scanner.nextLine();

                    if (confirmDeactivate == 'Y' || confirmDeactivate == 'y') {
                        selectedCourse.setActive(false);
                        System.out.println("\n" + selectedCourse.getCourseName() +
                                " course with " + selectedCourse.getCourseID() + " has been deactivated.");
                    } else {
                        System.out.println("Deactivate canceled.");
                    }

                    System.out.println("Press Enter to continue...");
                    scanner.nextLine();
                }

            } else {
                System.out.println("The course ID was not found. Please double-check the course ID.");
                continueMenu = userController.isExit(scanner, validationCheck);
            }

        } while (continueMenu == false); // Keep looping if user wants to try again

    }

    private void activateCourse(Scanner scanner, ClearScreen clearScreen, ValidationCheck validationCheck,
            MenuList menuInterface, ArrayList<Faculty> facultyList, UserController userController) {

        boolean continueMenu;

        do {
            clearScreen.clearConsole();
            menuInterface.activateCourseIcon();

            // Gather all deactivated courses
            ArrayList<Course> deactivatedCourses = new ArrayList<>();

            for (Faculty faculty : facultyList) {
                for (Course course : faculty.getCourseList()) {
                    if (!course.getisActive()) {
                        deactivatedCourses.add(course);
                    }
                }
            }

            // If none are deactivated
            if (deactivatedCourses.isEmpty()) {
                System.out.println("No deactivated courses found.");
                System.out.println("Press Enter to return...");
                scanner.nextLine();
                return;
            }

            // Display deactivated courses
            System.out.println("================================ Deactivated Courses ================================");
            System.out.printf("%-4s%-12s%-50s %-15s\n", "No", "Course ID", "Course Name", "Faculty Code");
            System.out.println("-------------------------------------------------------------------------------------");

            for (int i = 0; i < deactivatedCourses.size(); i++) {
                Course course = deactivatedCourses.get(i);

                // Find which faculty it belongs to
                String facultyCode = "";
                for (Faculty faculty : facultyList) {
                    if (faculty.getCourseList().contains(course)) {
                        facultyCode = faculty.getFacultyCode();
                        break;
                    }
                }

                System.out.printf("%-4d%-12s%-50s %-15s\n", i + 1, course.getCourseID(), course.getCourseName(),
                        facultyCode);
            }

            System.out.println("-------------------------------------------------------------------------------------");
            System.out.print("Enter the Course ID you want to activate: ");
            String courseIDToActivate = scanner.nextLine().toUpperCase();

            Course selectedCourse = null;
            for (Course course : deactivatedCourses) {
                if (course.getCourseID().equalsIgnoreCase(courseIDToActivate)) {
                    selectedCourse = course;
                    break;
                }
            }

            if (selectedCourse != null) {
                clearScreen.clearConsole();
                menuInterface.activateCourseIcon();
                System.out.println("============================= Course Found ===============================");
                selectedCourse.displayInfo();
                System.out.println("--------------------------------------------------------------------------");

                System.out.print("Are you sure you want to activate this course? [Y/N]: ");
                char confirmActivate = scanner.next().charAt(0);
                scanner.nextLine(); // consume newline

                if (confirmActivate == 'Y' || confirmActivate == 'y') {
                    selectedCourse.setActive(true);
                    System.out.println("\n" + selectedCourse.getCourseName() + " has been activated.");
                } else {
                    System.out.println("Activation canceled.");
                }

                System.out.println("Press Enter to continue...");
                scanner.nextLine();
                continueMenu = false; // Stop loop after processing
            } else {
                System.out.println("The Course ID was not found in the deactivated list.");
                continueMenu = !userController.isExit(scanner, validationCheck); // Ask if user wants to try again
            }

        } while (continueMenu);
    }

    // ========================================================================
    // SUBJECT
    // ========================================================================

    public void displaySubjectInterface(Scanner scanner, ArrayList<Subject> subjectList,
            ValidationCheck validationCheck,
            ClearScreen clearScreen, MenuList menuInterface, ArrayList<Faculty> facultyList) {

        // Variable
        boolean continueSubject = true;

        UserController userController = new UserController();

        do {
            clearScreen.clearConsole();
            menuInterface.subjectMenu();

            int optionSubject = -1;

            try {
                optionSubject = scanner.nextInt();
                scanner.nextLine(); // Clear newline after number input

                switch (optionSubject) {
                    case 1:
                        insertSubject(scanner, menuInterface, subjectList, validationCheck, clearScreen,
                                userController);
                        break;
                    case 2:
                        clearScreen.clearConsole();
                        displayAllSubject(scanner, clearScreen, subjectList);
                        System.out.println("Press Enter to continue...");
                        scanner.nextLine();
                        break;
                    case 3:
                        updateSubject(scanner, clearScreen, validationCheck, menuInterface, subjectList,
                                userController);
                        System.out.println("Press Enter to continue...");
                        scanner.nextLine();
                        break;
                    case 4:
                        deactivateSubject(scanner, clearScreen, validationCheck, menuInterface, subjectList,
                                userController);
                        break;
                    case 5:
                        activateSubject(scanner, clearScreen, validationCheck, menuInterface, subjectList,
                                userController);
                        break;
                    case 6:
                        assignSubjectToCourse(scanner, clearScreen, validationCheck, facultyList, subjectList, menuInterface);
                        break;
                    case 7:
                        displayCourseAndSubjects(facultyList, scanner, clearScreen, validationCheck);
                        System.out.println("Press Enter to continue...");
                        scanner.nextLine(); // Wait so user can read
                        break;
                    case 8:
                        continueSubject = false;
                        break;
                    default:
                        System.out.println("Error! Please enter a number between 1 - 8 to proceed.");
                        System.out.println("Press Enter to continue...");
                        scanner.nextLine();
                }

            } catch (Exception ex) {
                System.out.println("Error! Please enter a numeric number to proceed");
                System.out.println("Press Enter to continue...");
                scanner.nextLine(); // Discard the bad input
            }

        } while (continueSubject);

    }

    public void insertSubject(Scanner scanner, MenuList menuInterface, ArrayList<Subject> subjectList,
            ValidationCheck validationCheck, ClearScreen clearScreen, UserController userController) {
        char continueOption = 'Y';

        do {
            boolean isValid = false;
            String subjectName = "";
            int creditHour = 0;

            do {
                clearScreen.clearConsole();
                menuInterface.addSubjectIcon();
                System.out.print("Subject Name\t:");
                subjectName = scanner.nextLine();

                // Check for cancellation
                if (subjectName.equals("X")) {
                    if (userController.isExit(scanner, validationCheck)) {
                        return; // Exit entire method if user confirms
                    } else {
                        continue; // Continue looping if user chooses not to exit
                    }
                }

                isValid = validationCheck.validationName(subjectName);

                if (isValid) {
                    // Check for duplicate
                    for (Subject subject : subjectList) {
                        if (subject.getSubjectName().equalsIgnoreCase(subjectName)) {
                            System.out.println("Invalid input. Subject already exists!");
                            isValid = false;
                            break;
                        }
                    }
                }

                if (isValid == false) {
                    System.out.print("Please try again. Press any key to continue...");
                    scanner.nextLine(); // Wait for user input
                }
            } while (isValid == false);

            // get credit hours
            do {
                clearScreen.clearConsole();
                menuInterface.addSubjectIcon();
                System.out.println("Subject Name\t: " + subjectName);
                System.out.print("Credit Hours\t: ");

                isValid = false;
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("X")) {
                    if (userController.isExit(scanner, validationCheck)) {
                        return;
                    } else {
                        continue;
                    }
                }

                try {
                    creditHour = Integer.parseInt(input);
                    isValid = validationCheck.validationCreditHours(creditHour);

                    if (!isValid) {
                        System.out.print("Invalid credit hours. Press Enter to try again...");
                        scanner.nextLine();
                    }

                } catch (NumberFormatException ex) {
                    System.out.println("Invalid input. Please enter a numeric value.");
                    System.out.print("Press Enter to retry...");
                    scanner.nextLine();
                    creditHour = 0;
                    isValid = false;
                }
            } while (!isValid);

            // Add new subject
            Subject newSubject = new Subject(subjectName, creditHour);
            subjectList.add(newSubject);

            do {
                // Display subject details
                clearScreen.clearConsole();
                menuInterface.addSubjectIcon();
                System.out.println("=============================== Subject Added ================================");
                System.out.println("Subject Code\t: " + newSubject.getSubjectCode());
                System.out.println("Subject Name\t: " + subjectName);
                System.out.println("Credit Hours\t: " + creditHour);
                System.out.println("------------------------------------------------------------------------------");

                // Ask if the user wants to continue
                System.out.print("Do you want to continue adding subject? [Y/N]: ");
                continueOption = scanner.next().charAt(0);
                scanner.nextLine(); // Consume the newline character

                isValid = validationCheck.validationYesNo(continueOption);

                if (isValid == false) {
                    System.out.print("Please try again. Press any key to continue...");
                    scanner.nextLine(); // Wait for user input
                }

            } while (isValid == false);
        } while (continueOption == 'Y' || continueOption == 'y');
    }

    public void displayAllSubject(Scanner scanner, ClearScreen clearScreen, ArrayList<Subject> subjectList) {

        if (subjectList.isEmpty()) {
            System.out.println("No subject records found.");
        } else {
            System.out.println("\n================================= All Subject =================================");
            System.out.printf("%-20s%-45s%-15s%n", "Subject Code", "Subject Name", "Credit Hours");
            System.out.println("===============================================================================");

            for (Subject subject : subjectList) {
                if (subject.getisActive()) {
                    subject.displayInfoTable();
                    System.out
                            .println("-------------------------------------------------------------------------------");
                }
            }
        }
    }

    // Update subject
    private void updateSubject(Scanner scanner, ClearScreen clearScreen, ValidationCheck validationCheck,
            MenuList menuInterface, ArrayList<Subject> subjectList, UserController userController) {

        boolean continueMenu;

        do {
            clearScreen.clearConsole();
            menuInterface.updateSubjectIcon();
            displayAllSubject(scanner, clearScreen, subjectList);

            System.out.print("\nEnter Subject Code to update: ");
            String subjectCode = scanner.nextLine().trim();

            continueMenu = checkSubjectCode(subjectCode, subjectList); // Check if subject code exists

            if (continueMenu == true) {
                Subject selectedSubject = null;

                // Find the subject by code
                for (Subject subject : subjectList) {
                    if (subject.getSubjectCode().equalsIgnoreCase(subjectCode)) {
                        selectedSubject = subject;
                        break;
                    }
                }

                // Subject found
                System.out
                        .println("=============================== Subject Found ================================");
                System.out.printf("%-20s%-45s%-15s\n", "Subject Code", "Subject Name", "Credit Hours");
                System.out
                        .println("==============================================================================");
                selectedSubject.displayInfoTable();
                System.out
                        .println("------------------------------------------------------------------------------");

                boolean updateMore;

                do {
                    try {
                        clearScreen.clearConsole();
                        menuInterface.updateSubjectIcon();
                        System.out
                                .println(
                                        "=============================== Subject Found ================================");
                        System.out.printf("%-20s%-45s%-15s\n", "Subject Code", "Subject Name", "Credit Hours");
                        System.out
                                .println(
                                        "==============================================================================");
                        selectedSubject.displayInfoTable();
                        System.out
                                .println(
                                        "------------------------------------------------------------------------------");
                        menuInterface.updateSubjectSelectedMenu();
                        System.out.print("Enter your choice: ");
                        int choice = scanner.nextInt();
                        scanner.nextLine(); // consume newline
                        boolean isValid = false;

                        switch (choice) {
                            case 1: // Update Subject Code
                                while (isValid == false) {

                                    clearScreen.clearConsole();
                                    menuInterface.updateSubjectIcon();

                                    System.out
                                            .println(
                                                    "=============================== Subject Found ================================");
                                    System.out.printf("%-20s%-45s%-15s\n", "Subject Code", "Subject Name",
                                            "Credit Hours");
                                    System.out
                                            .println(
                                                    "==============================================================================");
                                    selectedSubject.displayInfoTable();
                                    System.out
                                            .println(
                                                    "------------------------------------------------------------------------------");

                                    System.out.print("Enter new Subject Code: ");
                                    subjectCode = scanner.nextLine().toUpperCase();

                                    isValid = validationCheck.validationSubjectCode(subjectCode);

                                    if (isValid) {
                                        // Check for duplicate
                                        for (Subject subject : subjectList) {
                                            if (subject.getSubjectCode().equalsIgnoreCase(subjectCode)) {
                                                System.out.println("Invalid input. Subject already exists!");
                                                isValid = false;
                                                break;
                                            }
                                        }
                                    }

                                    if (isValid == true) {
                                        selectedSubject.setSubjectCode(subjectCode);
                                    } else {
                                        System.out.print("Please try again. Press enter key to continue...");
                                        scanner.nextLine();
                                    }
                                }
                                break;

                            case 2: // Update Subject Name
                                while (isValid == false) {
                                    clearScreen.clearConsole();
                                    menuInterface.updateSubjectIcon();

                                    System.out
                                            .println(
                                                    "=============================== Subject Found ================================");
                                    System.out.printf("%-20s%-45s%-15s\n", "Subject Code", "Subject Name",
                                            "Credit Hours");
                                    System.out
                                            .println(
                                                    "==============================================================================");
                                    selectedSubject.displayInfoTable();
                                    System.out
                                            .println(
                                                    "------------------------------------------------------------------------------");

                                    System.out.print("Enter new Subject Name: ");
                                    String subjectName = scanner.nextLine();

                                    isValid = validationCheck.validationName(subjectName);

                                    if (isValid) {
                                        // Check for duplicate
                                        for (Subject subject : subjectList) {
                                            if (subject.getSubjectName().equalsIgnoreCase(subjectName)) {
                                                System.out.println("Invalid input. Subject already exists!");
                                                isValid = false;
                                                break;
                                            }
                                        }
                                    }

                                    if (isValid) {
                                        selectedSubject.setSubjectName(subjectName);
                                    } else {
                                        System.out.print("Please try again. Press enter key to continue...");
                                        scanner.nextLine();
                                    }
                                }
                                break;

                            case 3: // Update Credit Hours
                                while (isValid == false) {
                                    clearScreen.clearConsole();
                                    menuInterface.updateSubjectIcon();

                                    System.out
                                            .println(
                                                    "=============================== Subject Found ================================");
                                    System.out.printf("%-20s%-45s%-15s\n", "Subject Code", "Subject Name",
                                            "Credit Hours");
                                    selectedSubject.displayInfoTable();
                                    System.out
                                            .println(
                                                    "------------------------------------------------------------------------------");

                                    System.out.print("Enter new Subject Credit Hours: ");
                                    int creditHour = scanner.nextInt();

                                    isValid = validationCheck.validationCreditHours(creditHour);

                                    if (isValid) {
                                        selectedSubject.setCreditHour(creditHour);
                                    } else {
                                        System.out.print("Please try again. Press enter key to continue...");
                                        scanner.nextLine();
                                    }
                                }
                                break;

                            case 4: // Exit update menu
                                System.out.println("Exiting update menu...");
                                updateMore = false;
                                continue;

                            default:
                                System.out.println("Invalid choice! No updates made.");
                                System.out.println("Press Enter to continue...");
                                scanner.nextLine();
                                updateMore = true;
                                continue;
                        }

                        System.out.println("\nSubject details updated successfully!");
                        System.out.println("Press Enter to continue...");
                        scanner.nextLine();

                        do {
                            clearScreen.clearConsole();
                            menuInterface.updateSubjectIcon();

                            System.out.println(
                                    "============================== Updated Subject ===============================");
                            System.out.printf("%-20s%-45s%-15s\n", "Subject Code", "Subject Name", "Credit Hours");
                            System.out.println(
                                    "==============================================================================");
                            selectedSubject.displayInfoTable();
                            System.out.println(
                                    "------------------------------------------------------------------------------");

                            System.out.print("\nDo you want to update another field? (Y/N): ");
                            char choiceContinue = scanner.next().charAt(0);
                            scanner.nextLine();

                            updateMore = (choiceContinue == 'Y' || choiceContinue == 'y');
                            isValid = validationCheck.validationYesNo(choiceContinue);

                            if (isValid == false) {
                                System.out.print("Please try again. Press enter key to continue...");
                                scanner.nextLine();
                            }

                        } while (isValid == false);

                    } catch (Exception ex) {
                        System.out.println("Error! Please enter a valid number. Press enter key to continue...");
                        scanner.nextLine();
                        scanner.nextLine();
                        updateMore = true;
                    }

                } while (updateMore == true);

            } else {
                System.out.println("The subject record was not found. Please double-check the Subject Code.");
                continueMenu = userController.isExit(scanner, validationCheck);
            }

        } while (continueMenu == false);
    }

    private void deactivateSubject(Scanner scanner, ClearScreen clearScreen, ValidationCheck validationCheck,
            MenuList menuInterface, ArrayList<Subject> subjectList, UserController userController) {

        boolean continueMenu;

        do {
            clearScreen.clearConsole();
            menuInterface.deactivateSubjectIcon();

            if (subjectList.isEmpty()) {
                System.out.println("No subject records found.");
                System.out.println("Press Enter to continue...");
                scanner.nextLine();
                return; // Exit the function if no subject
            }

            displayAllSubject(scanner, clearScreen, subjectList); // Show all subject

            System.out.print("Enter the Subject Code that you want to deactivate: ");
            String subjectCodeToDeactivate = scanner.nextLine().toUpperCase();

            continueMenu = checkSubjectCode(subjectCodeToDeactivate, subjectList);

            if (continueMenu == true) {
                Subject selectedSubject = null;

                // Assign the subject detail to selected subject
                for (Subject subject : subjectList) {
                    if (subject.getSubjectCode().equalsIgnoreCase(subjectCodeToDeactivate)) {
                        selectedSubject = subject;
                        break;
                    }
                }

                if (selectedSubject != null) {
                    clearScreen.clearConsole();
                    menuInterface.deactivateSubjectIcon();
                    System.out
                            .println("================================ Subject Found ===============================");
                    System.out.printf("%-20s%-45s%-15s\n", "Subject Code", "Subject Name", "Credit Hours");
                    System.out
                            .println("==============================================================================");
                    System.out.printf("%-20s%-45s%-15d\n", selectedSubject.getSubjectCode(),
                            selectedSubject.getSubjectName(), selectedSubject.getCreditHour());
                    System.out
                            .println("-----------------------------------------------------------------------------");

                    System.out.print("Are you sure you want to deactivate this subject? [Y/N]: ");
                    char confirmDeactivate = scanner.next().charAt(0);
                    scanner.nextLine();

                    if (confirmDeactivate == 'Y' || confirmDeactivate == 'y') {
                        selectedSubject.setActive(false);
                        System.out.println(
                                "\nSubject with code " + selectedSubject.getSubjectCode() + " has been deactivated.");
                    } else {
                        System.out.println("Deactivate canceled.");
                    }

                    System.out.println("Press Enter to continue...");
                    scanner.nextLine();
                }

            } else {
                System.out.println("The subject code was not found. Please double-check the Subject Code.");
                continueMenu = userController.isExit(scanner, validationCheck);
            }

        } while (continueMenu == false); // Keep looping if user wants to try again

    }

    private void activateSubject(Scanner scanner, ClearScreen clearScreen, ValidationCheck validationCheck,
            MenuList menuInterface, ArrayList<Subject> subjectList, UserController userController) {

        boolean continueMenu;

        do {
            clearScreen.clearConsole();
            menuInterface.activateSubjectIcon();

            // Gather all deactivated subjects
            ArrayList<Subject> deactivatedSubjects = new ArrayList<>();

            for (Subject subject : subjectList) {
                if (!subject.getisActive()) {
                    deactivatedSubjects.add(subject);
                }
            }

            // If none are deactivated
            if (deactivatedSubjects.isEmpty()) {
                System.out.println("No deactivated subjects found.");
                System.out.println("Press Enter to return...");
                scanner.nextLine();
                return;
            }

            // Display deactivated subjects
            System.out
                    .println("================================ Deactivated Subjects ================================");
            System.out.printf("%-4s%-20s%-45s%-15s\n", "No", "Subject Code", "Subject Name", "Credit Hours");
            System.out.println("-------------------------------------------------------------------------------------");

            for (int i = 0; i < deactivatedSubjects.size(); i++) {
                Subject subject = deactivatedSubjects.get(i);

                System.out.printf("%-4d%-20s%-45s%-15s\n", i + 1, subject.getSubjectCode(), subject.getSubjectName(),
                        subject.getCreditHour());
            }

            System.out.println("-------------------------------------------------------------------------------------");
            System.out.print("Enter the Subject Code you want to activate: ");
            String subjectCodeToActivate = scanner.nextLine().toUpperCase();

            Subject selectedSubject = null;
            for (Subject subject : deactivatedSubjects) {
                if (subject.getSubjectCode().equalsIgnoreCase(subjectCodeToActivate)) {
                    selectedSubject = subject;
                    break;
                }
            }

            if (selectedSubject != null) {
                clearScreen.clearConsole();
                menuInterface.activateSubjectIcon();
                System.out.println("============================= Subject Found ===============================");
                System.out.printf("%-20s%-45s%-15s\n", "Subject Code", "Subject Name", "Credit Hours");
                System.out.println("===========================================================================");
                System.out.printf("%-20s%-45s%-15d\n", selectedSubject.getSubjectCode(),
                        selectedSubject.getSubjectName(), selectedSubject.getCreditHour());
                System.out.println("---------------------------------------------------------------------------");

                System.out.print("Are you sure you want to activate this subject? [Y/N]: ");
                char confirmActivate = scanner.next().charAt(0);
                scanner.nextLine(); // consume newline

                if (confirmActivate == 'Y' || confirmActivate == 'y') {
                    selectedSubject.setActive(true);
                    System.out.println(
                            "\nSubject with code " + selectedSubject.getSubjectCode() + " has been activated.");
                } else {
                    System.out.println("Activation canceled.");
                }

                System.out.println("Press Enter to continue...");
                scanner.nextLine();
                continueMenu = false; // Stop loop after processing
            } else {
                System.out.println("The Subject Code was not found in the deactivated list.");
                continueMenu = !userController.isExit(scanner, validationCheck); // Ask if user wants to try again
            }

        } while (continueMenu); // Keep looping if user wants to try again
    }


    public void assignSubjectToCourse(Scanner scanner, ClearScreen clearScreen, ValidationCheck validationCheck,
            ArrayList<Faculty> facultyList, ArrayList<Subject> allSubjects, MenuList menuList) {

        UserController userController = new UserController();
        boolean continueFacultySearch = true;

        while (continueFacultySearch) {
            clearScreen.clearConsole();
            menuList.subjectAllocationIcon();
            System.out.println("================================== Faculty List ==================================");

            int facultyCounter = 0;
            for (Faculty f : facultyList) {
                if (f.getisActive()) {
                    facultyCounter++;
                }
            }

            if (facultyCounter == 0) {
                System.out.println("No active faculties available.");
                System.out.print("Press Enter to return to menu...");
                scanner.nextLine();
                return;
            }

            System.out.printf("%-5s%-15s%-30s\n", "No.", "Faculty Code", "Faculty Name");
            System.out.println("==================================================================================");

            int displayIndex = 1;
            for (Faculty f : facultyList) {
                if (f.getisActive()) {
                    System.out.printf("[%-2d]%-1s%-15s%-30s\n", displayIndex++, "", f.getFacultyCode(),
                            f.getFacultyName());
                }
            }

            System.out.println("==================================================================================");
            System.out.print("Select a faculty 1-" + facultyCounter + ": ");
            String facultyChoice = scanner.nextLine().toUpperCase();

            if (facultyChoice.equals("X")) {
                return;
            }

            try {
                int facultyNum = Integer.parseInt(facultyChoice);
                if (facultyNum < 1 || facultyNum > facultyCounter) {
                    System.out.println("Invalid faculty selection.");
                    System.out.print("Press Enter to try again...");
                    scanner.nextLine();
                    continue;
                }

                Faculty selectedFaculty = null;
                int currentCount = 0;
                for (Faculty f : facultyList) {
                    if (f.getisActive()) {
                        currentCount++;
                        if (currentCount == facultyNum) {
                            selectedFaculty = f;
                            break;
                        }
                    }
                }

                boolean continueCourseSearch = true;
                while (continueCourseSearch) {
                    clearScreen.clearConsole();
                    menuList.subjectAllocationIcon();
                    System.out.println("Course List for Faculty: " + selectedFaculty.getFacultyName());
                    System.out.println(
                            "==================================================================================");

                    int courseCounter = 0;
                    for (Course c : selectedFaculty.getCourseList()) {
                        if (c.getisActive()) {
                            courseCounter++;
                        }
                    }

                    if (courseCounter == 0) {
                        System.out.println("No active courses available in this faculty.");
                        System.out.print("Press Enter to return to faculty selection...");
                        scanner.nextLine();
                        continueCourseSearch = false;
                        continue;
                    }

                    System.out.printf("%-5s%-12s%-50s%-15s\n", "No.", "Course ID", "Course Name", "Credit Hours");
                    System.out.println(
                            "==================================================================================");

                    displayIndex = 1;
                    for (Course c : selectedFaculty.getCourseList()) {
                        if (c.getisActive()) {
                            System.out.printf("[%-2d]%-1s%-12s%-50s%-15d\n", displayIndex++, "", c.getCourseID(),
                                    c.getCourseName(), c.getCreditHours());
                            System.out.println(
                                    "----------------------------------------------------------------------------------");
                        }
                    }

                    System.out.print("Select a course 1-" + courseCounter + ": ");
                    String courseChoice = scanner.nextLine().toUpperCase();

                    if (courseChoice.equals("X")) {
                        continueCourseSearch = false;
                        continue;
                    }

                    try {
                        int courseNum = Integer.parseInt(courseChoice);
                        if (courseNum < 1 || courseNum > courseCounter) {
                            System.out.println("Invalid course selection.");
                            System.out.print("Press Enter to try again...");
                            scanner.nextLine();
                            continue;
                        }

                        Course selectedCourse = null;
                        currentCount = 0;
                        for (Course c : selectedFaculty.getCourseList()) {
                            if (c.getisActive()) {
                                currentCount++;
                                if (currentCount == courseNum) {
                                    selectedCourse = c;
                                    break;
                                }
                            }
                        }

                        boolean continueSubjectAssignment = true;
                        while (continueSubjectAssignment) {
                            clearScreen.clearConsole();
                            menuList.subjectAllocationIcon();
                            System.out.println("Assign Subjects to: " + selectedCourse.getCourseName());
                            System.out.println(
                                    "==================================================================================");

                            System.out.println("Current Subjects:");
                            boolean hasSubjects = false;
                            for (Subject s : selectedCourse.getSubjectList()) {
                                if (s.getisActive()) {
                                    hasSubjects = true;
                                    System.out.printf("- %s (%s) [%d credits]\n", s.getSubjectName(),
                                            s.getSubjectCode(), s.getCreditHour());
                                }
                            }
                            if (!hasSubjects) {
                                System.out.println("No subjects assigned yet.");
                            }

                            System.out.println("Total Credits: " + selectedCourse.getCreditHours() + "/90");
                            System.out.println(
                                    "==================================================================================");

                            System.out.println("\nAvailable Subjects to Assign:");
                            ArrayList<Subject> availableSubjects = new ArrayList<>();
                            for (Subject s : allSubjects) {
                                if (s.getisActive() && !selectedCourse.getSubjectList().contains(s)) {
                                    availableSubjects.add(s);
                                }
                            }

                            if (availableSubjects.isEmpty()) {
                                System.out.println("No more subjects available to assign.");
                                System.out.print("Press Enter to go back...");
                                scanner.nextLine();
                                continueSubjectAssignment = false;
                                continue;
                            }

                            System.out.printf("%-5s%-15s%-50s%-10s\n", "No.", "Subject Code", "Subject Name",
                                    "Credits");
                            System.out.println(
                                    "==================================================================================");
                            for (int i = 0; i < availableSubjects.size(); i++) {
                                Subject s = availableSubjects.get(i);
                                System.out.printf("[%-2d]%-1s%-15s%-50s%-10d\n", i + 1, "", s.getSubjectCode(),
                                        s.getSubjectName(), s.getCreditHour());
                                System.out.println(
                                        "----------------------------------------------------------------------------------");
                            }

                            System.out.print("Select subjects to assign (comma-separated numbers): ");
                            String subjectChoice = scanner.nextLine().toUpperCase();

                            if (subjectChoice.equals("X")) {
                                continueSubjectAssignment = false;
                                continue;
                            }

                            String[] selections = subjectChoice.split(",");
                            ArrayList<Subject> newlyAddedSubjects = new ArrayList<>();

                            for (String indexStr : selections) {
                                try {
                                    int index = Integer.parseInt(indexStr.trim()) - 1;
                                    if (index >= 0 && index < availableSubjects.size()) {
                                        Subject subject = availableSubjects.get(index);
                                        if (selectedCourse.getCreditHours() + subject.getCreditHour() > 90) {
                                            System.out.println("Cannot assign " + subject.getSubjectName()
                                                    + " - would exceed 90 credit limit");
                                            continue;
                                        }
                                        if (!selectedCourse.getSubjectList().contains(subject)) {
                                            selectedCourse.addSubject(subject);
                                            newlyAddedSubjects.add(subject);
                                            System.out.println("Added: " + subject.getSubjectName());
                                        } else {
                                            System.out.println(subject.getSubjectName() + " is already in this course");
                                        }
                                    } else {
                                        System.out.println("Invalid selection: " + (index + 1));
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input: " + indexStr.trim());
                                }
                            }

                            if (!newlyAddedSubjects.isEmpty()) {
                                System.out.println("\nSuccessfully assigned subjects:");
                                for (Subject s : newlyAddedSubjects) {
                                    System.out.println("- " + s.getSubjectName() + " (" + s.getSubjectCode() + ")");
                                }
                            }

                            char continueChoice = 'N'; // Declare outside
                            boolean validYesNo = false;
                            while (!validYesNo) {
                                System.out.print("\nAssign more subjects to this course? (Y/N): ");
                                String continueChoiceInput = scanner.nextLine().toUpperCase();

                                if (continueChoiceInput.length() == 1 &&
                                        validationCheck.validationYesNo(continueChoiceInput.charAt(0))) {
                                    continueChoice = continueChoiceInput.charAt(0);
                                    validYesNo = true;
                                } else {
                                    clearScreen.clearConsole();
                                    menuList.subjectAllocationIcon();
                                    System.out.println("Assign Subjects to: " + selectedCourse.getCourseName());
                                    System.out.println(
                                            "==================================================================================");

                                    System.out.println("Current Subjects:");
                                    hasSubjects = false;
                                    for (Subject s : selectedCourse.getSubjectList()) {
                                        if (s.getisActive()) {
                                            hasSubjects = true;
                                            System.out.printf("- %s (%s) [%d credits]\n", s.getSubjectName(),
                                                    s.getSubjectCode(), s.getCreditHour());
                                        }
                                    }
                                    if (!hasSubjects) {
                                        System.out.println("No subjects assigned yet.");
                                    }

                                    System.out.println("Total Credits: " + selectedCourse.getCreditHours() + "/90");
                                    System.out.println(
                                            "==================================================================================");

                                    System.out.println("\nAvailable Subjects to Assign:");
                                    if (availableSubjects.isEmpty()) {
                                        System.out.println("No more subjects available to assign.");
                                    } else {
                                        System.out.printf("%-5s%-15s%-50s%-10s\n", "No.", "Subject Code",
                                                "Subject Name", "Credits");
                                        System.out.println(
                                                "==================================================================================");
                                        for (int i = 0; i < availableSubjects.size(); i++) {
                                            Subject s = availableSubjects.get(i);
                                            System.out.printf("[%-2d]%-1s%-15s%-50s%-10d\n", i + 1, "",
                                                    s.getSubjectCode(), s.getSubjectName(), s.getCreditHour());
                                            System.out.println(
                                                    "----------------------------------------------------------------------------------");
                                        }
                                    }

                                    System.out.println("\nInvalid input. Please enter Y or N.");
                                }
                            }

                            continueSubjectAssignment = (continueChoice == 'Y');

                            if (continueChoice == 'N') {
                                clearScreen.clearConsole();
                                menuList.subjectAllocationIcon();
                                System.out.println(
                                        "==================== Subject Assignment Summary ====================");
                                System.out.println("Faculty : " + selectedFaculty.getFacultyName() + " ("
                                        + selectedFaculty.getFacultyCode() + ")");
                                System.out.println("Course  : " + selectedCourse.getCourseName() + " ("
                                        + selectedCourse.getCourseID() + ")");
                                System.out.println(
                                        "--------------------------------------------------------------------");
                                System.out.println("Subjects in this course:");

                                int totalCredits = 0;
                                for (Subject s : selectedCourse.getSubjectList()) {
                                    if (s.getisActive()) {
                                        System.out.printf("- %s (%s) [%d credits]\n", s.getSubjectName(),
                                                s.getSubjectCode(), s.getCreditHour());
                                        totalCredits += s.getCreditHour();
                                    }
                                }

                                System.out.println(
                                        "--------------------------------------------------------------------");
                                System.out.println("Total Credit Hours: " + totalCredits + "/90");
                                System.out.println(
                                        "====================================================================");
                                System.out.print("Press Enter to return to menu...");
                                scanner.nextLine();
                                return;
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        System.out.print("Press Enter to try again...");
                        scanner.nextLine();
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                System.out.print("Press Enter to try again...");
                scanner.nextLine();
            }
        }
    }

    public void displayCourseAndSubjects(ArrayList<Faculty> facultyList, Scanner scanner, ClearScreen clearscreen,
            ValidationCheck validationCheck) {
        UserController userController = new UserController();

        // Main faculty/course loop
        boolean continueFacultySearch = true;

        while (continueFacultySearch) {
            clearscreen.clearConsole();
            System.out.println("\nPress 'X' to exit.");
            System.out.println("================================== Faculty List ==================================");

            if (facultyList.isEmpty()) {
                System.out.println("No faculties available.");
                return;
            }

            ArrayList<Faculty> activeFaculties = new ArrayList<>();
            for (Faculty f : facultyList) {
                if (f.getisActive()) {
                    activeFaculties.add(f);
                }
            }

            if (activeFaculties.isEmpty()) {
                System.out.println("No active faculties available.");
                return;
            }

            System.out.printf("%-5s%-15s%-30s\n", "No.", "Faculty Code", "Faculty Name");
            System.out.println("==================================================================================");

            for (int i = 0; i < activeFaculties.size(); i++) {
                Faculty f = activeFaculties.get(i);
                System.out.printf("[%-2d]%-1s%-15s%-30s\n", i + 1, "", f.getFacultyCode(), f.getFacultyName());
            }

            System.out.println("==================================================================================");
            System.out.print("Select a faculty 1-" + activeFaculties.size() + ": ");
            String facultyChoice = scanner.nextLine().toUpperCase();

            if (facultyChoice.equals("X") && userController.isExit(scanner, validationCheck)) {
                return; // Exit the method completely if 'X' is selected
            }

            try {
                int facultyNum = Integer.parseInt(facultyChoice);
                if (facultyNum < 1 || facultyNum > activeFaculties.size()) {
                    System.out.println("Invalid faculty selection.");
                    System.out.print("Press Enter to try again...");
                    scanner.nextLine();
                    continue;
                }

                Faculty selectedFaculty = activeFaculties.get(facultyNum - 1);
                ArrayList<Course> courses = selectedFaculty.getCourseList();

                boolean continueCourseSearch = true;

                while (continueCourseSearch) {
                    clearscreen.clearConsole();
                    System.out.println(
                            "Course List for Faculty: " + selectedFaculty.getFacultyName() + " (Press 'X' to exit)");
                    System.out.println(
                            "==================================================================================");

                    ArrayList<Course> activeCourses = new ArrayList<>();
                    for (Course c : courses) {
                        if (c.getisActive()) {
                            activeCourses.add(c);
                        }
                    }

                    if (activeCourses.isEmpty()) {
                        System.out.println("No active courses available in this faculty.");
                        break;
                    }

                    System.out.printf("%-5s%-12s%-50s%-15s\n", "No.", "Course ID", "Course Name", "Credit Hours");
                    System.out.println(
                            "==================================================================================");

                    for (int i = 0; i < activeCourses.size(); i++) {
                        Course course = activeCourses.get(i);
                        System.out.printf("[%-2d]%-1s%-12s%-50s%-15d\n", i + 1, "", course.getCourseID(),
                                course.getCourseName(), course.getCreditHours());
                        System.out.println(
                                "----------------------------------------------------------------------------------");
                    }

                    System.out.print("Select a course 1-" + activeCourses.size() + ":");
                    String courseChoice = scanner.nextLine().toUpperCase();

                    if (courseChoice.equals("X") && userController.isExit(scanner, validationCheck)) {
                        continueCourseSearch = false;
                        continue; // This will go back to faculty selection
                    }

                    try {
                        int courseNum = Integer.parseInt(courseChoice);
                        if (courseNum < 1 || courseNum > activeCourses.size()) {
                            System.out.println("Invalid course selection.");
                            System.out.print("Press Enter to try again...");
                            scanner.nextLine();
                            continue;
                        }

                        Course selectedCourse = activeCourses.get(courseNum - 1);
                        ArrayList<Subject> subjects = selectedCourse.getSubjectList();

                        boolean hasSubject = false;
                        boolean showSubjectList = true;
                        String retryChoice;

                        do {
                            if (showSubjectList) {
                                clearscreen.clearConsole();
                                System.out.println("\nSubjects under Course: " + selectedCourse.getCourseName()
                                        + " (Press 'X' to exit)");
                                System.out.println(
                                        "==================================================================================");
                                System.out.printf("%-15s%-50s%-15s\n", "Subject Code", "Subject Name", "Credit Hours");
                                System.out.println(
                                        "==================================================================================");

                                for (Subject s : subjects) {
                                    if (s.getisActive()) {
                                        hasSubject = true;
                                        System.out.printf("%-15s%-50s%-15d\n", s.getSubjectCode(), s.getSubjectName(),
                                                s.getCreditHour());
                                        System.out.println(
                                                "----------------------------------------------------------------------------------");
                                    }
                                }

                                if (!hasSubject) {
                                    System.out.println("No active subjects found for this course.");
                                }
                            }

                            System.out.print("\nWould you like to select another faculty? (Y/N): ");
                            retryChoice = scanner.nextLine().toUpperCase();
                            showSubjectList = true; // Reset flag for next iteration

                            if (validationCheck.validationYesNo(retryChoice.charAt(0))) {
                                if (retryChoice.equals("Y")) {
                                    continueCourseSearch = false; // Will go back to faculty selection
                                } else {
                                    continueCourseSearch = false;
                                    continueFacultySearch = false; // Will exit both loops and return to menu
                                }
                            } else {
                                System.out.print("Press Enter to try again...");
                                scanner.nextLine();
                            }
                        } while (!validationCheck.validationYesNo(retryChoice.charAt(0)));

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input, please enter a valid number or press X to exit.");
                        System.out.print("Press Enter to try again...");
                        scanner.nextLine();
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a valid number or press X to exit.");
                System.out.print("Press Enter to try again...");
                scanner.nextLine();
            }
        }
    }

    // ======================================================
    // EXTRA function
    // =======================================================

    // Check faculty code
    public boolean checkFacultyCode(String facultyCode, ArrayList<Faculty> facultyList) {
        for (Faculty faculty : facultyList) {
            if (faculty.getFacultyCode().equalsIgnoreCase(facultyCode)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkCourseID(String courseID, ArrayList<Faculty> facultyList) {
        for (Faculty faculty : facultyList) {
            for (Course course : faculty.getCourseList()) {
                if (course.getCourseID().equalsIgnoreCase(courseID)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Check subject code
    public boolean checkSubjectCode(String subjectCode, ArrayList<Subject> subjectList) {
        for (Subject subject : subjectList) {
            if (subject.getSubjectCode().equalsIgnoreCase(subjectCode)) {
                return true;
            }
        }
        return false;
    }

}
