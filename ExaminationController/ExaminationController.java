package ExaminationController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import CourseController.*;
import UserController.*;
import ExtraFunction.*;

public class ExaminationController {

    public void displayExaminationMaintenanceUI(Scanner scanner, ClearScreen clearScreen,
            ValidationCheck validationCheck,
            MenuList menuInterface, CourseController courseController, ArrayList<Student> studentList,
            ArrayList<Faculty> facultyList,
            ArrayList<Examination> examinationList, ArrayList<Venue> venueList, MenuList menuList) {
        // Variable
        boolean continueAdmin = true;

        do {
            clearScreen.clearConsole(); // Call the clear screen function
            menuInterface.examinationMenu(); // Call the course menu

            try {

                int optionAdmin = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (optionAdmin) {
                    case 1: // Add Examination
                        addExamination(scanner, clearScreen, venueList, validationCheck, examinationList, facultyList,
                                menuList);
                        break;
                    case 2: // View Examination
                        clearScreen.clearConsole();
                        displayAllExamination(examinationList, facultyList);
                        System.out.print("Press Enter to continue...");
                        scanner.nextLine(); // Wait for user input
                        break;
                    case 3: // Exit program
                        return;
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

        } while (continueAdmin);

        scanner.close();
    }

    public void timetable(String studentID, ArrayList<Student> studentList, ArrayList<Examination> examinationList) {
        Student studentDetail = null;

        // Search for the student with the given ID
        for (Student student : studentList) {
            if (student.getId().equalsIgnoreCase(studentID)) {
                studentDetail = student;
                break;
            }
        }

        // If student is not found, exit the method
        if (studentDetail == null) {
            System.out.println("Student with ID " + studentID + " not found.");
            return;
        }

        List<Course> studentCourses = studentDetail.getEnrolledCourses();

        // Header
        System.out.println(
                "\n==================================================================== Examination Timetable ==================================================================");
        System.out.printf("%-50s%-15s%-10s%-45s%-15s%-10s%-10s\n",
                "Course Name", "Subject ID", "Exam ID", "Exam Name", "Exam Date", "Exam Time","Venue");
        System.out
                .println(
                        "-------------------------------------------------------------------------------------------------------------------------------------------------------------");

        // Display examination info for each subject in active courses
        boolean examFound = false;
        for (Course course : studentCourses) {
            if (course.getisActive()) {
                for (Subject subject : course.getSubjectList()) {
                    for (Examination examination : examinationList) {
                        if (examination.getExam().equalsIgnoreCase(subject.getSubjectName())) {
                            System.out.printf("%-50s%-15s%-10s%-45s%-15s%-10s%-10s\n",
                                    course.getCourseName(),
                                    subject.getSubjectCode(),
                                    examination.getExamID(),
                                    examination.getExam(),
                                    examination.getExamDate(),
                                    examination.getExamTime(),
                                    examination.getVenue().getName());
                            examFound = true;
                            System.out.println(
                                    "-------------------------------------------------------------------------------------------------------------------------------------------------------------");
                        }
                    }
                }
            }
        }

        if (!examFound) {
            System.out.println("No examinations found for student ID " + studentID);
        }
    }

    public void addExamination(Scanner scanner, ClearScreen clearScreen,
            ArrayList<Venue> venueList, ValidationCheck validationCheck,
            ArrayList<Examination> examinationList, ArrayList<Faculty> facultyList, MenuList menuList) {

        Course selectedCourse = null;
        Subject selectedSubject = null;
        String inputCourseID = "", inputSubjectCode = "", examDate = "", examTime = "";
        int numberOfStudents = 0;
        boolean isValid = false;

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        // ===== Select Course =====
        do {
            clearScreen.clearConsole();
            menuList.addExaminationMenu();

            System.out.println("\nAvailable Courses:");
            boolean found = false;

            for (Faculty faculty : facultyList) {
                if (faculty.getisActive()) {
                    System.out.println("Faculty: " + faculty.getFacultyName());
                    System.out.println(
                            "---------------------------------------------------------------------------------------");
                    for (Course course : faculty.getCourseList()) {
                        if (course.getisActive()) {
                            course.displayInfo();
                            System.out.println();
                            found = true;
                        }
                    }
                }
            }

            if (!found) {
                System.out.println("No active courses available.");
            }

            System.out.print("\nEnter the Course ID:");
            inputCourseID = scanner.nextLine().trim();

            if (inputCourseID.equalsIgnoreCase("X")) {
                if (isExit(scanner, validationCheck))
                    return;
                continue;
            }

            isValid = false;
            for (Faculty faculty : facultyList) {
                for (Course course : faculty.getCourseList()) {
                    if (course.getCourseID().equalsIgnoreCase(inputCourseID) && course.getisActive()) {
                        selectedCourse = course;
                        isValid = true;
                        break;
                    }
                }
                if (isValid)
                    break;
            }

            if (!isValid) {
                System.out.println("Invalid Course ID. Press Enter to retry...");
                scanner.nextLine();
            }

        } while (!isValid);

        // ===== Select Subject =====
        do {
            clearScreen.clearConsole();

            menuList.addExaminationMenu();
            System.out.println("\nCourse: " + selectedCourse.getCourseName());
            System.out.println("\nAvailable Subjects:");
            for (Subject subject : selectedCourse.getSubjectList()) {
                subject.displayInfo();
                System.out.println();
            }

            System.out.print("\nEnter the Subject Code: ");
            inputSubjectCode = scanner.nextLine().trim();

            if (inputSubjectCode.equalsIgnoreCase("X")) {
                if (isExit(scanner, validationCheck))
                    return;
                continue;
            }

            isValid = false;
            for (Subject subject : selectedCourse.getSubjectList()) {
                if (subject.getSubjectCode().equalsIgnoreCase(inputSubjectCode)) {
                    selectedSubject = subject;
                    isValid = true;
                    break;
                }
            }

            if (!isValid) {
                System.out.println("Invalid Subject Code. Press Enter to retry...");
                scanner.nextLine();
            }

        } while (!isValid);

        // ===== Enter Exam Date =====
        LocalDate inputDate = null;
        do {
            clearScreen.clearConsole();

            menuList.addExaminationMenu();

            System.out.println("Course\t\t: " + selectedCourse.getCourseName());
            System.out.println("Subject\t\t: " + selectedSubject.getSubjectName());

            System.out.print("\nEnter the Exam Date (YYYY-MM-DD): ");
            examDate = scanner.nextLine().trim();

            if (examDate.equalsIgnoreCase("X")) {
                if (isExit(scanner, validationCheck))
                    return;
                continue;
            }

            try {
                inputDate = LocalDate.parse(examDate);
                if (!inputDate.isBefore(currentDate)) {
                    isValid = true;
                } else {
                    System.out.println("Date must not be in the past. Press Enter to retry...");
                    isValid = false;
                    scanner.nextLine();
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Press Enter to retry...");
                isValid = false;
                scanner.nextLine();
            }
        } while (!isValid);

        // ===== Enter Exam Time =====
        LocalTime inputTime = null;
        do {
            clearScreen.clearConsole();

            menuList.addExaminationMenu();

            System.out.println("Course\t\t: " + selectedCourse.getCourseName());
            System.out.println("Subject\t\t: " + selectedSubject.getSubjectName());
            System.out.println("Exam Date\t: " + examDate);

            System.out.print("\nEnter the Exam Time (HH:MM): ");
            examTime = scanner.nextLine().trim();

            if (examTime.equalsIgnoreCase("X")) {
                if (isExit(scanner, validationCheck))
                    return;
                continue;
            }

            try {
                inputTime = LocalTime.parse(examTime);

                if (inputDate.isAfter(currentDate)
                        || (inputDate.equals(currentDate) && inputTime.isAfter(currentTime))) {
                    isValid = true;
                } else {
                    System.out.println("Time must be in the future. Press Enter to retry...");
                    isValid = false;
                    scanner.nextLine();
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format. Press Enter to retry...");
                isValid = false;
                scanner.nextLine();
            }

        } while (!isValid);

        // ===== Enter Number of Students & Choose Venue =====
        Venue selectedVenue = null;

        do {
            selectedVenue = null;
            isValid = false;

            clearScreen.clearConsole();
            menuList.addExaminationMenu();
            System.out.println("Course\t\t: " + selectedCourse.getCourseName());
            System.out.println("Subject\t\t: " + selectedSubject.getSubjectName());
            System.out.println("Exam Date\t: " + examDate);
            System.out.println("Exam Time\t: " + examTime);

            System.out.print("\nEnter number of students: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("X")) {
                if (isExit(scanner, validationCheck))
                    return;
                continue;
            }

            try {
                numberOfStudents = Integer.parseInt(input);
                isValid = validationCheck.validationStudentCount(numberOfStudents);
                if (!isValid) {
                    System.out.println("Invalid number of students. Press Enter to retry...");
                    scanner.nextLine();
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numbers only.");
                System.out.print("Press Enter to retry...");
                scanner.nextLine();
                continue;
            }

            if (venueList == null || venueList.isEmpty()) {
                System.out.println("Venue list is empty. Please add venues first.");
                return;
            }

            // ===== Find suitable venue without conflict =====
            for (Venue venue : venueList) {
                if (venue.getCapacity() >= numberOfStudents) {
                    boolean isOccupied = false;

                    for (Examination existingExam : examinationList) {
                        if (existingExam.getVenue().equals(venue) &&
                                existingExam.getExamDate().equals(examDate) &&
                                existingExam.getExamTime().equals(examTime)) {
                            isOccupied = true;
                            break;
                        }
                    }

                    if (!isOccupied) {
                        selectedVenue = venue;
                        break;
                    }
                }
            }

            if (selectedVenue == null) {
                System.out.println(
                        "No suitable venue available at this date/time. Press Enter to re-enter student number...");
                scanner.nextLine();
            }

        } while (selectedVenue == null);

        // ===== Create Examination Object =====
        Examination exam = new Examination(selectedSubject.getSubjectName(), examDate, examTime, selectedVenue);
        examinationList.add(exam);

        clearScreen.clearConsole();
        menuList.addExaminationMenu();
        System.out.println("============================= Examination Added Successfully ===========================");
        System.out.print("Subject Code\t:" + selectedSubject.getSubjectCode());
        exam.displayInfo();
        System.out.println("\nTotal Student\t:" + numberOfStudents);
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void displayAllExamination(ArrayList<Examination> examinationList, ArrayList<Faculty> facultyList) {
        System.out
                .println("======================================= All Examination Record ======================================");
        System.out.printf("%-15s%-10s%-35s%-15s%-10s%-10s\n", "Subject Code", "ExamID", "Exam", "Exam Date", "Exam Time", "Venue");
        System.out.println("-----------------------------------------------------------------------------------------------------");
        
        for (Faculty faculty : facultyList) {
            for (Course course : faculty.getCourseList()) {
                for (Subject subject : course.getSubjectList()) {
                    for (Examination examination : examinationList) {
                        if (examination.getExam().equalsIgnoreCase(subject.getSubjectName())) {
                            System.out.printf("%-15s%-10s%-35s%-15s%-10s%-10s\n",
                                    subject.getSubjectCode(),
                                    examination.getExamID(),
                                    examination.getExam(),
                                    examination.getExamDate(),
                                    examination.getExamTime(),
                                    examination.getVenue().getName());
                                    System.out.println("-----------------------------------------------------------------------------------------------------");
                        }
                    }
                }
            }
        }
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

}
