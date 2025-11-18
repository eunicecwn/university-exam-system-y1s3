package ResultController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import UserController.*;
import CourseController.*;
import ExaminationController.*;
import ExtraFunction.*;

public class ResultController {

    // Menu List
    // In ResultController.java

    public void displayResultUI(Scanner scanner, ClearScreen clearScreen, ValidationCheck validationCheck,
            MenuList menuList, ArrayList<FacultyMember> facultyMemberList,
            ArrayList<Result> resultList, ArrayList<Student> studentList,
            ArrayList<Faculty> facultyList, ArrayList<Examination> examinationList) {
        boolean continueResult = true;

        while (continueResult) {
            clearScreen.clearConsole();
            menuList.fmMenuForResult();

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            try {
                int option = Integer.parseInt(input);

                switch (option) {
                    case 1:
                        clearScreen.clearConsole();
                        Student selectedStudent = addStudentResult(scanner, clearScreen, validationCheck, menuList,
                                resultList, facultyList, studentList, examinationList);
                        if (selectedStudent != null) {
                            updateStudentCGPA(selectedStudent, resultList);
                        }
                        break;

                    case 2:
                        clearScreen.clearConsole();
                        viewAllResults(scanner, clearScreen, menuList, resultList, studentList, facultyList);
                        break;

                    case 3:
                        clearScreen.clearConsole();
                        Result updatedResult = updateStudentResult(scanner, clearScreen, validationCheck,
                                menuList, resultList, studentList, facultyList);
                        if (updatedResult != null) {
                            updateStudentCGPA(updatedResult.getStudent(), resultList);
                        }
                        break;

                    case 4:
                        clearScreen.clearConsole();
                        Result deactivatedResult = deactivateStudentResult(scanner, clearScreen, validationCheck,
                                menuList, resultList, studentList, facultyList);
                        if (deactivatedResult != null) {
                            updateStudentCGPA(deactivatedResult.getStudent(), resultList);
                        }
                        break;

                    case 5:
                        clearScreen.clearConsole();
                        Result activatedResult = activateStudentResult(scanner, clearScreen, validationCheck,
                                menuList, resultList, studentList, facultyList);
                        if (activatedResult != null) {
                            updateStudentCGPA(activatedResult.getStudent(), resultList);
                        }
                        break;

                    case 6:
                        continueResult = false;
                        break;

                    default:
                        System.out.println("\nPlease enter a number between 1-6.");
                        System.out.print("Press Enter to continue...");
                        scanner.nextLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("\nPlease enter a number between 1-6.");
                System.out.print("Press Enter to continue...");
                waitForEnter(scanner);
            }
        }
    }

    private Result activateStudentResult(Scanner scanner, ClearScreen clearScreen,
            ValidationCheck validationCheck, MenuList menuList,
            ArrayList<Result> resultList, ArrayList<Student> studentList,
            ArrayList<Faculty> facultyList) {
        boolean isValid = false;
        Result selectedResult = null;
        Student selectedStudent = null;

        do {
            clearScreen.clearConsole();
            menuList.activateResultIcon();

            // First check if there are any results to activate
            if (resultList.isEmpty()) {
                System.out.println("There are no results available to activate.");
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
                return null;
            }

            // Get list of students with inactive results
            List<Student> studentsWithResults = studentList.stream()
                    .filter(student -> {
                        final Student s = student;
                        return resultList.stream()
                                .anyMatch(result -> result.getStudent().equals(s) &&
                                        !result.getIsActive());
                    })
                    .collect(Collectors.toList());

            if (studentsWithResults.isEmpty()) {
                System.out.println("There are no students with inactive results to activate.");
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
                return null;
            }

            // Display available students with inactive results
            System.out.println("Students with Inactive Results:\n");
            System.out.println("================================================================================");
            System.out.printf("%-15s%-20s%-10s\n", "Student ID", "Name", "Intake");
            System.out.println("================================================================================");

            for (Student student : studentsWithResults) {
                if (student.getisActive()) {
                    System.out.printf("%-15s%-20s%-10d\n",
                            student.getId(),
                            student.getName(),
                            student.getIntakeYear());
                }
            }

            // Prompt for student ID
            System.out.print("\nEnter Student ID to activate result : ");
            String studentId = scanner.nextLine().trim();

            if (studentId.equalsIgnoreCase("X")) {
                return null;
            }

            if (!studentId.matches("^\\d{4}S\\d{5}$")) {
                System.out.println("Invalid Student ID format! Must be in format YYYYSNNNNN (e.g., 2023S10000).");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                continue;
            }

            selectedStudent = findStudentById(studentId, studentList);
            if (selectedStudent == null) {
                System.out.println("Student not found! Please check the ID and try again.");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                continue;
            }

            if (!selectedStudent.getisActive()) {
                System.out.println("Student is inactive! Cannot modify results for inactive students.");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                continue;
            }

            // Create final copy for use in lambda
            final Student finalSelectedStudent = selectedStudent;

            // Check if student has any inactive results
            boolean hasInactiveResults = resultList.stream()
                    .anyMatch(r -> r.getStudent().equals(finalSelectedStudent) && !r.getIsActive());

            if (!hasInactiveResults) {
                System.out.println("This student has no inactive results to activate!");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                continue;
            }

            // Get courses with inactive results for this student
            Map<String, List<Result>> coursesWithResults = resultList.stream()
                    .filter(r -> r.getStudent().equals(finalSelectedStudent) && !r.getIsActive())
                    .collect(Collectors.groupingBy(r -> r.getCourse().getCourseID()));

            // Course selection loop
            courseLoop: while (true) {
                clearScreen.clearConsole();
                menuList.activateResultIcon();
                System.out.print("Student: " + selectedStudent.getName() + " (" + selectedStudent.getId() + ")");
                System.out.println("\nCourses with Inactive Results:\n");
                System.out.println("================================================================================");
                System.out.printf("%-10s%-50s%-10s\n", "ID", "Course Name", "Results");
                System.out.println("================================================================================");

                for (Map.Entry<String, List<Result>> entry : coursesWithResults.entrySet()) {
                    String courseId = entry.getKey();
                    List<Result> results = entry.getValue();
                    Course course = findCourseByID(courseId, facultyList);

                    if (course != null) {
                        System.out.printf("%-10s%-50s%-10d\n",
                                courseId,
                                course.getCourseName(),
                                results.size());
                    }
                }

                // Prompt for course ID
                System.out.print("\nEnter Course ID to activate result from (or 'X' to go back): ");
                String courseID = scanner.nextLine().trim();

                if (courseID.equalsIgnoreCase("X")) {
                    break courseLoop; // Go back to student selection
                }

                // Validate course selection
                if (!coursesWithResults.containsKey(courseID)) {
                    System.out.println("Invalid Course ID or no inactive results for this course!");
                    System.out.print("Press Enter to continue...");
                    scanner.nextLine();
                    continue courseLoop;
                }

                List<Result> courseResults = coursesWithResults.get(courseID);

                if (courseResults.size() == 1) {
                    selectedResult = courseResults.get(0);
                    isValid = true;
                    break courseLoop;
                } else {
                    // Subject selection loop
                    subjectLoop: while (true) {
                        clearScreen.clearConsole();
                        menuList.activateResultIcon();
                        System.out.println(
                                "Student: " + selectedStudent.getName() + " (" + selectedStudent.getId() + ")");
                        System.out.println("Course: " + findCourseByID(courseID, facultyList).getCourseName() + " ("
                                + courseID + ")");
                        System.out.println("\nAvailable Subjects for this Course:");
                        System.out.printf("%-10s%-30s\n", "Code", "Subject Name");
                        System.out.println(
                                "---------------------------------------------------------------------------------");
                        for (Result result : courseResults) {
                            Subject subject = result.getSubject();
                            System.out.printf("%-10s%-30s\n",
                                    subject.getSubjectCode(),
                                    subject.getSubjectName());
                        }

                        System.out.print("\nEnter Subject Code to activate (or 'X' to go back): ");
                        String subjectCode = scanner.nextLine().trim();

                        if (subjectCode.equalsIgnoreCase("X")) {
                            continue courseLoop; // Go back to course selection
                        }

                        for (Result result : courseResults) {
                            if (result.getSubject().getSubjectCode().equalsIgnoreCase(subjectCode)) {
                                selectedResult = result;
                                isValid = true;
                                break subjectLoop;
                            }
                        }

                        System.out.println("\nInvalid Subject Code! Please try again.");
                        System.out.print("Press Enter to continue...");
                        scanner.nextLine();
                    }
                }

                if (isValid) {
                    break; // Exit the course loop if we have a valid selection
                }
            }
        } while (!isValid);

        clearScreen.clearConsole();
        menuList.activateResultIcon();
        System.out.println("Result to be activated:\n");
        System.out.println(
                "===================================================================================================================================================================================================");
        System.out.printf("%-15s%-20s%-10s%-40s%-10s%-40s%-10s%-10s%-10s%-10s\n",
                "Student ID", "Name", "Course", "Course Name", "Subject", "Subject Name", "Marks", "Grade", "GPA",
                "CGPA");
        System.out.println(
                "===================================================================================================================================================================================================");
        selectedResult.displayInfo(true);

        // Confirmation section with case-insensitive validation
        boolean validConfirm = false;
        String confirm = "";
        do {
            System.out.print("\nAre you sure you want to activate this result? (Y/N): ");
            confirm = scanner.nextLine().trim();

            if (confirm.equalsIgnoreCase("Y") || confirm.equalsIgnoreCase("N")) {
                validConfirm = true;
            } else {
                System.out.println("Invalid input! Please enter only 'Y' or 'N'.");
                System.out.print("Press Enter to try again...");
                scanner.nextLine();

                // Redisplay the result information
                clearScreen.clearConsole();
                menuList.activateResultIcon();
                System.out.println("Result to be activated:\n");
                System.out.println(
                        "===================================================================================================================================================================================================");
                System.out.printf("%-15s%-20s%-10s%-40s%-10s%-40s%-10s%-10s%-10s\n",
                        "Student ID", "Name", "Course", "Course Name", "Subject", "Subject Name", "Marks", "Grade",
                        "CGPA");
                System.out.println(
                        "===================================================================================================================================================================================================");
                selectedResult.displayInfo(true);
            }
        } while (!validConfirm);

        clearScreen.clearConsole();
        if (confirm.equalsIgnoreCase("Y")) {
            selectedResult.setIsActive(true);
            System.out.println(
                    "\n==========================================================================================");
            System.out.println("                      RESULT ACTIVATED SUCCESSFULLY                         ");
            System.out.println(
                    "==========================================================================================");
            System.out.println("  Student: " + selectedResult.getStudent().getName() + " ("
                    + selectedResult.getStudent().getId() + ")");
            System.out.println("  Course : " + selectedResult.getCourse().getCourseName() + " ("
                    + selectedResult.getCourse().getCourseID() + ")");
            if (selectedResult.getSubject() != null) {
                System.out.println("  Subject: " + selectedResult.getSubject().getSubjectName() + " ("
                        + selectedResult.getSubject().getSubjectCode() + ")");
            }
            System.out.println("  Marks  : " + selectedResult.getMarks());
            System.out.println("  Grade  : " + selectedResult.getGrade());
            System.out.println(
                    "------------------------------------------------------------------------------------------");
            System.out.println("  Note: This result has been activated and will now be visible to students.");
            System.out.println(
                    "==========================================================================================");
            System.out.print("\nPress Enter to continue...");
            scanner.nextLine();
            return selectedResult;
        } else {
            System.out.println(
                    "\n==========================================================================================");
            System.out.println("                             ACTIVATION CANCELLED                               ");
            System.out.println(
                    "==========================================================================================");
            System.out.println("  No changes have been made to the result record.");
            System.out.println(
                    "==========================================================================================");
            System.out.print("\nPress Enter to continue...");
            scanner.nextLine();
            return null;
        }
    }

    private void updateStudentCGPA(Student student, ArrayList<Result> resultList) {
        Map<String, Double> courseGradePoints = new HashMap<>();
        Map<String, Integer> courseCredits = new HashMap<>();

        // 1. Calculate grade points and credits for each course
        for (Result result : resultList) {
            if (result.getStudent().equals(student) && result.getIsActive()) {
                String courseId = result.getCourse().getCourseID();
                double gradePoint = result.getGradePoint();
                int credit = result.getCourse().getCreditHours();

                courseGradePoints.merge(courseId, gradePoint * credit, Double::sum);
                courseCredits.merge(courseId, credit, Integer::sum);
            }
        }

        // 2. Calculate and store course-specific GPAs
        for (Map.Entry<String, Double> entry : courseGradePoints.entrySet()) {
            String courseId = entry.getKey();
            double totalGradePoints = entry.getValue();
            int totalCreditHours = courseCredits.get(courseId);
            double courseGPA = (totalCreditHours > 0) ? totalGradePoints / totalCreditHours : 0.0;

            student.setCourseCGPA(courseId, courseGPA);
        }

        // 3. Calculate overall CGPA
        double overallTotalGradePoints = courseGradePoints.values().stream().mapToDouble(Double::doubleValue).sum();
        int overallTotalCredits = courseCredits.values().stream().mapToInt(Integer::intValue).sum();
        double overallCGPA = (overallTotalCredits > 0) ? overallTotalGradePoints / overallTotalCredits : 0.0;

        student.setCgpa(overallCGPA);
    }

    private Student addStudentResult(Scanner scanner, ClearScreen clearScreen,
            ValidationCheck validationCheck, MenuList menuList, ArrayList<Result> resultList,
            ArrayList<Faculty> facultyList, ArrayList<Student> studentList, ArrayList<Examination> examinationList) {

        boolean isValid = false;
        Student selectedStudent = null;
        Course selectedCourse = null;

        // Student selection loop
        do {
            clearScreen.clearConsole();
            menuList.addResultIcon();
            System.out.println("Available Students:\n");
            displayAllActiveStudents(studentList);

            System.out.print("\nEnter Student ID: ");
            String studentId = scanner.nextLine().trim();

            if (studentId.equalsIgnoreCase("X")) {
                clearScreen.clearConsole();
                return null;
            }

            if (!studentId.matches("^\\d{4}S\\d{5}$")) {
                System.out.println("\nInvalid Student ID format! Must be in format YYYYSNNNNN (e.g., 2023S10000).");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                continue;
            }

            selectedStudent = findStudentById(studentId, studentList);
            if (selectedStudent == null) {
                System.out.println("\nStudent not found! Please check the ID and try again.");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
            } else if (!selectedStudent.getisActive()) {
                System.out.println("\nStudent is inactive! Cannot add results for inactive students.");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
            } else {
                isValid = true;
            }
        } while (!isValid);

        // Course selection loop
        isValid = false;
        do {
            clearScreen.clearConsole();
            menuList.addResultIcon();
            System.out.println("Student: " + selectedStudent.getName() + " (" + selectedStudent.getId() + ")");

            // Get student's enrolled courses
            List<Course> enrolledCourses = selectedStudent.getEnrolledCourses();

            if (enrolledCourses.isEmpty()) {
                System.out.println("\nThis student is not enrolled in any courses!");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                return null;
            }

            System.out.println("\nEnrolled Courses:\n");
            System.out.println("================================================================================");
            System.out.printf("%-10s%-50s\n", "Code", "Course Name");
            System.out.println("================================================================================");
            for (Course course : enrolledCourses) {
                System.out.printf("%-10s%-50s\n", course.getCourseID(), course.getCourseName());
            }

            System.out.print("\nEnter Course ID: ");
            String courseIDInput = scanner.nextLine().trim();

            if (courseIDInput.equalsIgnoreCase("X")) {
                clearScreen.clearConsole();
                return null;
            }

            // Check if the course exists in the system
            selectedCourse = findCourseByID(courseIDInput, facultyList);
            if (selectedCourse == null) {
                System.out.println("\nInvalid Course Code! Please enter a valid course code.");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                continue;
            }

            // Check if student is enrolled in this course
            if (!enrolledCourses.contains(selectedCourse)) {
                System.out.println("\nStudent is not enrolled in this course! Please select from enrolled courses.");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                continue;
            }

            // Check if all subjects already have results
            List<Subject> subjects = selectedCourse.getSubjectList();
            List<Subject> subjectsWithResults = new ArrayList<>();

            for (Result result : resultList) {
                if (result.getStudent().equals(selectedStudent) &&
                        result.getCourse().equals(selectedCourse) &&
                        !subjectsWithResults.contains(result.getSubject())) {
                    subjectsWithResults.add(result.getSubject());
                }
            }

            if (subjectsWithResults.size() >= subjects.size()) {
                System.out.println("\nResult already exists for all subjects in this course!");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
            } else {
                isValid = true;
            }
        } while (!isValid);

        clearScreen.clearConsole();
        menuList.addResultIcon();
        System.out.println("Student: " + selectedStudent.getName() + " (" + selectedStudent.getId() + ")");
        System.out.println("Course: " + selectedCourse.getCourseName() + " (" + selectedCourse.getCourseID() + ")");

        List<Subject> subjects = selectedCourse.getSubjectList();
        if (subjects == null || subjects.isEmpty()) {
            System.out.println("No subjects found for this course!");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
            return null;
        }

        List<Subject> subjectsWithResults = new ArrayList<>();
        for (Result result : resultList) {
            if (result.getStudent().equals(selectedStudent) &&
                    result.getCourse().equals(selectedCourse) &&
                    !subjectsWithResults.contains(result.getSubject())) {
                subjectsWithResults.add(result.getSubject());
            }
        }

        boolean addMoreSubjects = true;
        List<Result> createdResults = new ArrayList<>();

        while (addMoreSubjects) {
            clearScreen.clearConsole();
            menuList.addResultIcon();
            System.out.println("Student: " + selectedStudent.getName() + " (" + selectedStudent.getId() + ")");
            System.out.println("Course: " + selectedCourse.getCourseName() + " (" + selectedCourse.getCourseID() + ")");
            System.out.println("\nAvailable Subjects with Examination Status:\n");
            System.out.println(
                    "============================================================================================================");
            System.out.printf("%-10s%-40s%-25s%-20s\n", "Code", "Subject Name", "Status", "Exam Date/Time");
            System.out.println(
                    "============================================================================================================");

            List<Subject> availableSubjects = new ArrayList<>();
            List<Subject> courseSubjects = selectedCourse.getSubjectList();

            for (Subject subject : courseSubjects) {
                if (!subjectsWithResults.contains(subject)) {
                    String status = "";
                    String examDateTimeInfo = "";
                    boolean examFound = false;
                    boolean examPassed = false;

                    // Check examination status
                    for (Examination exam : examinationList) {
                        if (exam.getExam().equalsIgnoreCase(subject.getSubjectName())) {
                            examFound = true;
                            try {
                                LocalDate examDate = LocalDate.parse(exam.getExamDate(),
                                        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                LocalTime examTime = LocalTime.parse(exam.getExamTime(),
                                        DateTimeFormatter.ofPattern("HH:mm"));
                                LocalDateTime examDateTime = LocalDateTime.of(examDate, examTime);

                                examDateTimeInfo = exam.getExamDate() + " " + exam.getExamTime();

                                if (LocalDateTime.now().isAfter(examDateTime)) {
                                    status = "Available (Exam Passed)";
                                    examPassed = true;
                                } else {
                                    status = "Not Available (Upcoming)";
                                }
                            } catch (Exception e) {
                                status = "Error parsing date";
                            }
                            break;
                        }
                    }

                    if (!examFound) {
                        status = "Assignment-based";
                        examDateTimeInfo = "N/A";
                        examPassed = true; // Allow adding results for assignment-based subjects
                    }

                    // Only add to available subjects if exam has passed or is assignment-based
                    if (examPassed) {
                        availableSubjects.add(subject);
                        System.out.printf("%-10s%-40s%-25s%-20s\n",
                                subject.getSubjectCode(),
                                subject.getSubjectName(),
                                status,
                                examDateTimeInfo);
                    }
                }
            }

            if (availableSubjects.isEmpty()) {
                System.out.println("\nNo subjects available for result entry at this time.");
                System.out.println("All subjects either have results already or their exams haven't occurred yet.");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                return null;
            }

            // Subject selection with improved validation
            List<Course> enrolledCourses = selectedStudent.getEnrolledCourses();
            Subject selectedSubject = null;
            while (selectedSubject == null) {
                System.out.println(
                        "------------------------------------------------------------------------------------------------------------");
                System.out.print("Enter Subject Code to add marks (or 'X' to cancel): ");
                String subjectCode = scanner.nextLine().trim();

                if (subjectCode.equalsIgnoreCase("X")) {
                    addMoreSubjects = false;
                    break;
                }

                // Check if student is enrolled in the selected course first
                if (!enrolledCourses.contains(selectedCourse)) {
                    System.out.println("\nStudent is not enrolled in this course!");
                    System.out.print("Press Enter to continue...");
                    scanner.nextLine();
                    break;
                }

                // Find the subject in the available subjects list
                for (Subject subject : availableSubjects) {
                    if (subject.getSubjectCode().equalsIgnoreCase(subjectCode)) {
                        selectedSubject = subject;
                        break;
                    }
                }

                if (selectedSubject == null) {
                    System.out
                            .println("\nInvalid subject code or not available for result entry! Please enter one of:");
                    for (Subject subject : availableSubjects) {
                        System.out.println(subject.getSubjectCode() + " - " + subject.getSubjectName());
                    }
                    System.out.print("Press Enter to try again...");
                    scanner.nextLine();
                    clearScreen.clearConsole();
                    menuList.addResultIcon();
                    System.out.println("Student: " + selectedStudent.getName() + " (" + selectedStudent.getId() + ")");
                    System.out.println(
                            "Course: " + selectedCourse.getCourseName() + " (" + selectedCourse.getCourseID() + ")");
                    System.out.println("\nAvailable Subjects with Examination Status:\n");
                    System.out.println(
                            "============================================================================================================");
                    System.out.printf("%-10s%-40s%-25s%-20s\n", "Code", "Subject Name", "Status", "Exam Date/Time");
                    System.out.println(
                            "============================================================================================================");
                    for (Subject subject : availableSubjects) {
                        // Re-display the status information
                        String status = "";
                        String examDateTimeInfo = "";
                        boolean examFound = false;

                        for (Examination exam : examinationList) {
                            if (exam.getExam().equalsIgnoreCase(subject.getSubjectName())) {
                                examFound = true;
                                examDateTimeInfo = exam.getExamDate() + " " + exam.getExamTime();
                                try {
                                    LocalDate examDate = LocalDate.parse(exam.getExamDate(),
                                            DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                    LocalTime examTime = LocalTime.parse(exam.getExamTime(),
                                            DateTimeFormatter.ofPattern("HH:mm"));
                                    LocalDateTime examDateTime = LocalDateTime.of(examDate, examTime);

                                    if (LocalDateTime.now().isAfter(examDateTime)) {
                                        status = "Available (Exam Passed)";
                                    } else {
                                        status = "Not Available (Upcoming)";
                                    }
                                } catch (Exception e) {
                                    status = "Error parsing date";
                                }
                                break;
                            }
                        }

                        if (!examFound) {
                            status = "Assignment-based";
                            examDateTimeInfo = "N/A";
                        }

                        System.out.printf("%-10s%-40s%-25s%-20s\n",
                                subject.getSubjectCode(),
                                subject.getSubjectName(),
                                status,
                                examDateTimeInfo);
                    }
                }
            }

            if (!addMoreSubjects || selectedSubject == null) {
                break;
            }

            // Marks entry with improved validation
            boolean validMark = false;
            double marks = 0;
            while (!validMark) {
                clearScreen.clearConsole();
                menuList.addResultIcon();
                System.out.println("Student: " + selectedStudent.getName() + " (" + selectedStudent.getId() + ")");
                System.out.println(
                        "Course: " + selectedCourse.getCourseName() + " (" + selectedCourse.getCourseID() + ")");
                System.out
                        .println("\n---------------------------------------------------------------------------------");
                System.out.println("Enter marks for " + selectedSubject.getSubjectCode() +
                        " - " + selectedSubject.getSubjectName() + " (0-100) (or 'X' to cancel): ");

                String marksInput = scanner.nextLine().trim();

                if (marksInput.equalsIgnoreCase("X")) {
                    return null;
                }

                if (marksInput.isEmpty()) {
                    System.out.println("\nNo input provided! Please enter a number or 'X' to cancel.");
                    System.out.print("Press Enter to try again...");
                    scanner.nextLine();
                    continue;
                }

                try {
                    marks = Double.parseDouble(marksInput);
                    if (marks >= 0 && marks <= 100) {
                        validMark = true;
                    } else {
                        System.out.println("\nMarks must be between 0-100! Please try again.");
                        System.out.print("Press Enter to continue...");
                        scanner.nextLine();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\nInvalid marks format! Please enter a valid number (e.g., 85 or 75.5).");
                    System.out.print("Press Enter to continue...");
                    scanner.nextLine();
                }
            }

            // Create and add the result
            Result newResult = new Result(selectedStudent, selectedCourse, marks);
            newResult.setSubject(selectedSubject);
            newResult.setGrade(newResult.calculateGrade(marks));
            newResult.setIsActive(true);
            resultList.add(newResult);

            // After successfully entering marks for a subject
            createdResults.add(newResult);
            subjectsWithResults.add(selectedSubject);

            if (subjectsWithResults.size() < subjects.size()) {
                // Ask if user wants to add more subjects
                boolean addAnotherSubject = true;
                while (addAnotherSubject) {
                    System.out.print("\nAdd another subject? (Y/N): ");
                    String choice = scanner.nextLine().trim().toUpperCase();

                    if (choice.equals("Y")) {
                        addAnotherSubject = false; // Exit this loop to continue to next subject
                        // The outer while(addMoreSubjects) loop will continue
                    } else if (choice.equals("N")) {
                        addMoreSubjects = false; // Exit both loops
                        addAnotherSubject = false;
                    } else {
                        // Invalid input - redisplay marks entry for current subject
                        System.out.println("\nInvalid input! Please enter Y or N.");
                        System.out.print("Press Enter to continue...");
                        scanner.nextLine();

                        // Redisplay marks entry screen for current subject
                        clearScreen.clearConsole();
                        menuList.addResultIcon();
                        System.out.println(
                                "Student: " + selectedStudent.getName() + " (" + selectedStudent.getId() + ")");
                        System.out.println("Course: " + selectedCourse.getCourseName() + " ("
                                + selectedCourse.getCourseID() + ")");
                        System.out.println(
                                "\n---------------------------------------------------------------------------------");
                        System.out.println("Enter marks for " + selectedSubject.getSubjectCode() +
                                " - " + selectedSubject.getSubjectName() + " (0-100): ");
                        System.out.println("Current marks: " + marks);
                    }
                }
            } else {
                System.out.println("\nAll subjects now have results.");
                addMoreSubjects = false;
            }
        }

        if (!createdResults.isEmpty()) {
            clearScreen.clearConsole();

            // Update CGPA calculations first
            updateStudentCGPA(selectedStudent, resultList);

            System.out.println("\n================================================================================");
            System.out.println("                           RESULTS ADDED SUCCESSFULLY");
            System.out.println("================================================================================");
            System.out.println("\nStudent: " + selectedStudent.getName() + " (" + selectedStudent.getId() + ")");
            System.out.println("Course: " + selectedCourse.getCourseName() + " (" + selectedCourse.getCourseID() + ")");

            System.out.println("\n--------------------------------------------------------------------------------");
            System.out.printf("%-10s%-40s%-10s%-10s\n", "Code", "Subject", "Marks", "Grade");
            System.out.println("--------------------------------------------------------------------------------");
            for (Result result : createdResults) {
                System.out.printf("%-10s%-40s%-10.2f%-10s\n",
                        result.getSubject().getSubjectCode(),
                        result.getSubject().getSubjectName(),
                        result.getMarks(),
                        result.getGrade());
            }

            // Display course-specific CGPA and overall CGPA
            double courseCGPA = selectedStudent.getCourseCGPA(selectedCourse.getCourseID());
            System.out.println("\n--------------------------------------------------------------------------------");
            System.out.printf("%-20s: %.2f\n", "Course CGPA", courseCGPA);
            System.out.println("================================================================================");

            // Single consistent prompt
            waitForEnter(scanner);
            return selectedStudent;
        }
        return null;
    }

    private Result updateStudentResult(Scanner scanner, ClearScreen clearScreen,
            ValidationCheck validationCheck, MenuList menuList,
            ArrayList<Result> resultList, ArrayList<Student> studentList,
            ArrayList<Faculty> facultyList) {
        boolean isValid = false;
        Result selectedResult = null;
        Student selectedStudent = null;
        Map<String, List<Result>> coursesWithResults = null;

        // Student selection loop
        studentLoop: do {
            clearScreen.clearConsole();
            menuList.updateResultIcon();

            // First check if there are any results to update
            if (resultList.isEmpty()) {
                System.out.println("There are no results available to update.");
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
                return null;
            }

            // Get list of students with active results
            List<Student> studentsWithResults = studentList.stream()
                    .filter(student -> {
                        final Student s = student;
                        return resultList.stream()
                                .anyMatch(result -> result.getStudent().equals(s) &&
                                        result.getIsActive());
                    })
                    .collect(Collectors.toList());

            if (studentsWithResults.isEmpty()) {
                System.out.println("There are no students with active results to update.");
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
                return null;
            }

            // Display available students with active results
            System.out.println("Students with Active Results:\n");
            System.out.println("================================================================================");
            System.out.printf("%-15s%-20s%-10s\n", "Student ID", "Name", "Intake");
            System.out.println("================================================================================");

            for (Student student : studentsWithResults) {
                if (student.getisActive()) {
                    System.out.printf("%-15s%-20s%-10d\n",
                            student.getId(),
                            student.getName(),
                            student.getIntakeYear());
                }
            }

            // Prompt for student ID
            System.out.print("\nEnter Student ID to update result: ");
            String studentId = scanner.nextLine().trim();

            if (studentId.equalsIgnoreCase("X")) {
                return null;
            }

            if (!studentId.matches("^\\d{4}S\\d{5}$")) {
                System.out.println("\nInvalid Student ID format! Must be in format YYYYSNNNNN (e.g., 2023S10000).");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                continue;
            }

            selectedStudent = findStudentById(studentId, studentList);
            if (selectedStudent == null) {
                System.out.println("\nStudent not found! Please check the ID and try again.");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                continue;
            }

            if (!selectedStudent.getisActive()) {
                System.out.println("\nStudent is inactive! Cannot update results for inactive students.");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                continue;
            }

            // Create final copy for use in lambda
            final Student finalSelectedStudent = selectedStudent;

            // Check if student has any active results
            boolean hasActiveResults = resultList.stream()
                    .anyMatch(r -> r.getStudent().equals(finalSelectedStudent) && r.getIsActive());

            if (!hasActiveResults) {
                System.out.println("\nThis student has no active results to update!");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                continue;
            }

            // Course selection loop
            courseLoop: while (true) {
                clearScreen.clearConsole();
                menuList.updateResultIcon();

                // Get courses with active results for this student
                coursesWithResults = resultList.stream()
                        .filter(r -> r.getStudent().equals(finalSelectedStudent) && r.getIsActive())
                        .collect(Collectors.groupingBy(r -> r.getCourse().getCourseID()));

                // Display available courses for this student
                System.out.println("Student: " + selectedStudent.getName() + " (" + selectedStudent.getId() + ")");
                System.out.println("Courses with Active Results:\n");
                System.out.println("================================================================================");
                System.out.printf("%-10s%-50s%-10s\n", "ID", "Course Name", "Results");
                System.out.println("================================================================================");

                for (Map.Entry<String, List<Result>> entry : coursesWithResults.entrySet()) {
                    String courseId = entry.getKey();
                    List<Result> results = entry.getValue();
                    Course course = findCourseByID(courseId, facultyList);

                    if (course != null) {
                        System.out.printf("%-10s%-50s%-10d\n",
                                courseId,
                                course.getCourseName(),
                                results.size());
                    }
                }

                // Prompt for course ID
                System.out.print("\nEnter Course ID to update result from (or 'X' to go back): ");
                String courseID = scanner.nextLine().trim();

                if (courseID.equalsIgnoreCase("X")) {
                    continue studentLoop; // Go back to student selection
                }

                // Validate course selection
                if (!coursesWithResults.containsKey(courseID)) {
                    System.out.println("\nInvalid Course ID or no active results for this course!");
                    System.out.print("Press Enter to continue...");
                    scanner.nextLine();
                    continue; // Stay in course selection loop
                }

                List<Result> courseResults = coursesWithResults.get(courseID);

                // Subject selection (if needed)
                if (courseResults.size() == 1) {
                    selectedResult = courseResults.get(0);
                } else {
                    System.out.println("\nAvailable Subjects for this Course:\n");
                    System.out.println(
                            "================================================================================");
                    System.out.printf("%-10s%-30s\n", "Code", "Subject Name");
                    System.out.println(
                            "================================================================================");
                    for (Result result : courseResults) {
                        Subject subject = result.getSubject();
                        System.out.printf("%-10s%-30s\n",
                                subject.getSubjectCode(),
                                subject.getSubjectName());
                    }

                    System.out.println(
                            "---------------------------------------------------------------------------------");
                    System.out.print("\nEnter Subject Code to update (or 'X' to go back): ");
                    String subjectCode = scanner.nextLine().trim();

                    if (subjectCode.equalsIgnoreCase("X")) {
                        continue; // Go back to course selection
                    }

                    for (Result result : courseResults) {
                        if (result.getSubject().getSubjectCode().equalsIgnoreCase(subjectCode)) {
                            selectedResult = result;
                            break;
                        }
                    }

                    if (selectedResult == null) {
                        System.out.println("\nInvalid Subject Code! Please try again.");
                        System.out.print("Press Enter to continue...");
                        scanner.nextLine();
                        continue; // Stay in subject selection
                    }
                }

                // Marks entry loop
                marksLoop: while (true) {
                    clearScreen.clearConsole();
                    menuList.updateResultIcon();
                    System.out.print("Current Result:\n");
                    System.out.println(
                            "===================================================================================================================================================================================================");
                    System.out.printf("%-15s%-20s%-10s%-40s%-10s%-40s%-10s%-10s%-10s%-10s\n",
                            "Student ID", "Name", "Course", "Course Name", "Subject", "Subject Name", "Marks", "Grade",
                            "GPA", "CGPA");
                    System.out.println(
                            "===================================================================================================================================================================================================");
                    selectedResult.displayInfo(true);

                    System.out.print("\nEnter new Marks (0-100) (or 'X' to go back): ");
                    String marksInput = scanner.nextLine().trim();

                    if (marksInput.equalsIgnoreCase("X")) {
                        continue courseLoop; // Go back to course selection
                    }

                    try {
                        double newMarks = Double.parseDouble(marksInput);
                        if (newMarks >= 0 && newMarks <= 100) {
                            selectedResult.setMarks(newMarks);
                            selectedResult.setGrade(selectedResult.calculateGrade(newMarks));

                            // Success message
                            clearScreen.clearConsole();
                            System.out.println(
                                    "\n================================================================================");
                            System.out.println("                         RESULTS UPDATED SUCCESSFULLY");
                            System.out.println(
                                    "================================================================================");
                            System.out.println("  Student: " + selectedResult.getStudent().getName() + " ("
                                    + selectedResult.getStudent().getId() + ")");
                            System.out.println("  Course : " + selectedResult.getCourse().getCourseName() + " ("
                                    + selectedResult.getCourse().getCourseID() + ")");
                            if (selectedResult.getSubject() != null) {
                                System.out.println("  Subject: " + selectedResult.getSubject().getSubjectName() + " ("
                                        + selectedResult.getSubject().getSubjectCode() + ")");
                            }
                            System.out.println("  Marks  : " + selectedResult.getMarks());
                            System.out.println("  Grade  : " + selectedResult.getGrade());
                            System.out.println(
                                    "================================================================================");
                            System.out.print("\nPress Enter to continue...");
                            scanner.nextLine();

                            return selectedResult;
                        } else {
                            System.out.println("\nMarks must be between 0-100!");
                            System.out.print("Press Enter to continue...");
                            scanner.nextLine();
                            continue marksLoop; // Stay in marks entry
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("\nInvalid marks format!");
                        System.out.print("Press Enter to continue...");
                        scanner.nextLine();
                        continue marksLoop; // Stay in marks entry
                    }
                }
            }
        } while (!isValid);

        return null;
    }

    private void viewAllResults(Scanner scanner, ClearScreen clearScreen, MenuList menuList,
            ArrayList<Result> resultList, ArrayList<Student> studentList,
            ArrayList<Faculty> facultyList) {

        // First check if there are any results to display
        if (resultList.isEmpty()) {
            clearScreen.clearConsole();
            menuList.viewResultIcon();
            System.out.println("There are no results to view.");
            System.out.print("\nPress Enter to continue...");
            scanner.nextLine();
            return;
        }

        studentSelection: while (true) {
            clearScreen.clearConsole();
            menuList.viewResultIcon();

            // Display available students
            System.out.println("Available Students:\n");
            System.out.println("================================================================================");
            System.out.printf("%-15s%-20s%-10s\n", "Student ID", "Name", "Intake");
            System.out.println("================================================================================");
            for (Student student : studentList) {
                if (student.getisActive()) {
                    System.out.printf("%-15s%-20s%-10d\n",
                            student.getId(),
                            student.getName(),
                            student.getIntakeYear());
                }
            }

            // Prompt for student ID
            System.out.print("\nEnter Student ID to view results (or 'X' to cancel): ");
            String studentId = scanner.nextLine().trim();

            if (studentId.equalsIgnoreCase("X")) {
                return;
            }

            Student selectedStudent = findStudentById(studentId, studentList);
            if (selectedStudent == null) {
                System.out.println("\nInvalid Student ID! Please try again.");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                continue;
            }

            if (!selectedStudent.getisActive()) {
                System.out.println("\nStudent is inactive! Please select an active student.");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                continue;
            }

            // Now display results for the selected student
            clearScreen.clearConsole();
            menuList.viewResultIcon();
            System.out.println(
                    "================================================================================== Student Results ==================================================================================");
            System.out.printf("%-15s%-20s%-10s%-40s%-10s%-40s%-10s%-10s%-10s%-10s\n",
                    "Student ID", "Name", "Course", "Course Name", "Subject", "Subject Name", "Marks", "Grade", "GPA",
                    "CGPA");

            System.out.println(
                    "=====================================================================================================================================================================================");

            // Filter results for the selected student
            Map<String, List<Result>> studentResultsByCourse = resultList.stream()
                    .filter(r -> r.getIsActive() && r.getStudent().equals(selectedStudent))
                    .collect(Collectors.groupingBy(r -> r.getCourse().getCourseID()));

            if (studentResultsByCourse.isEmpty()) {
                System.out.println("\nNo active results found for this student.");
            } else {
                boolean isFirstCourse = true;

                for (Map.Entry<String, List<Result>> courseEntry : studentResultsByCourse.entrySet()) {
                    String courseId = courseEntry.getKey();
                    List<Result> courseResults = courseEntry.getValue();
                    boolean isFirstResult = true;

                    // Get course-specific CGPA, but if only 1 subject, CGPA = GPA of that subject
                    double courseCGPA;
                    if (courseResults.size() == 1) {
                        courseCGPA = courseResults.get(0).getGradePoint();
                    } else {
                        courseCGPA = selectedStudent.getCourseCGPA(courseId);
                    }

                    for (Result result : courseResults) {
                        System.out.printf("%-15s%-20s%-10s%-40s%-10s%-40s%-10s%-10s%-10s%-10s\n",
                                isFirstCourse ? selectedStudent.getId() : "",
                                isFirstCourse ? selectedStudent.getName() : "",
                                isFirstResult ? result.getCourse().getCourseID() : "",
                                isFirstResult ? result.getCourse().getCourseName() : "",
                                result.getSubject() != null ? result.getSubject().getSubjectCode() : "N/A",
                                result.getSubject() != null ? result.getSubject().getSubjectName() : "N/A",
                                result.getMarks(),
                                result.getGrade(),
                                result.getGradePoint(), // Subject GPA
                                isFirstResult ? courseCGPA : "");

                        isFirstCourse = false;
                        isFirstResult = false;
                    }
                    System.out.println(
                            "-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                }
            }

            System.out.print("\nPress Enter to return to student selection...");
            scanner.nextLine();
        }
    }

    private Result deactivateStudentResult(Scanner scanner, ClearScreen clearScreen,
            ValidationCheck validationCheck, MenuList menuList,
            ArrayList<Result> resultList, ArrayList<Student> studentList,
            ArrayList<Faculty> facultyList) {
        boolean isValid = false;
        Result selectedResult = null;
        Student selectedStudent = null;

        do {
            clearScreen.clearConsole();
            menuList.deactivateResultIcon();

            // First check if there are any results to deactivate
            if (resultList.isEmpty()) {
                System.out.println("There are no results available to deactivate.");
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
                return null;
            }

            // Get list of students with active results
            List<Student> studentsWithResults = studentList.stream()
                    .filter(student -> {
                        final Student s = student;
                        return resultList.stream()
                                .anyMatch(result -> result.getStudent().equals(s) &&
                                        result.getIsActive());
                    })
                    .collect(Collectors.toList());

            if (studentsWithResults.isEmpty()) {
                System.out.println("There are no students with active results to deactivate.");
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
                return null;
            }

            // Display available students with active results
            System.out.println("Students with Active Results:\n");
            System.out.println("================================================================================");
            System.out.printf("%-15s%-20s%-10s\n", "Student ID", "Name", "Intake");
            System.out.println("================================================================================");
            for (Student student : studentsWithResults) {
                if (student.getisActive()) {
                    System.out.printf("%-15s%-20s%-10d\n",
                            student.getId(),
                            student.getName(),
                            student.getIntakeYear());
                }
            }

            // Prompt for student ID
            System.out.print("\nEnter Student ID to deactivate result : ");
            String studentId = scanner.nextLine().trim();

            if (studentId.equalsIgnoreCase("X")) {
                return null;
            }

            if (!studentId.matches("^\\d{4}S\\d{5}$")) {
                System.out.println("Invalid Student ID format! Must be in format YYYYSNNNNN (e.g., 2023S10000).");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                continue;
            }

            selectedStudent = findStudentById(studentId, studentList);
            if (selectedStudent == null) {
                System.out.println("Student not found! Please check the ID and try again.");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                continue;
            }

            if (!selectedStudent.getisActive()) {
                System.out.println("Student is inactive! Cannot modify results for inactive students.");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                continue;
            }

            // Create final copy for use in lambda
            final Student finalSelectedStudent = selectedStudent;

            // Check if student has any active results
            boolean hasActiveResults = resultList.stream()
                    .anyMatch(r -> r.getStudent().equals(finalSelectedStudent) && r.getIsActive());

            if (!hasActiveResults) {
                System.out.println("This student has no active results to deactivate!");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                continue;
            }

            // Get courses with active results for this student
            Map<String, List<Result>> coursesWithResults = resultList.stream()
                    .filter(r -> r.getStudent().equals(finalSelectedStudent) && r.getIsActive())
                    .collect(Collectors.groupingBy(r -> r.getCourse().getCourseID()));

            // Course selection loop
            courseLoop: while (true) {
                clearScreen.clearConsole();
                menuList.deactivateResultIcon();
                System.out.print("Student: " + selectedStudent.getName() + " (" + selectedStudent.getId() + ")");
                System.out.println("\nCourses with Active Results:\n");
                System.out.println("================================================================================");
                System.out.printf("%-10s%-50s%-10s\n", "ID", "Course Name", "Results");
                System.out.println("================================================================================");

                for (Map.Entry<String, List<Result>> entry : coursesWithResults.entrySet()) {
                    String courseId = entry.getKey();
                    List<Result> results = entry.getValue();
                    Course course = findCourseByID(courseId, facultyList);

                    if (course != null) {
                        System.out.printf("%-10s%-50s%-10d\n",
                                courseId,
                                course.getCourseName(),
                                results.size());
                    }
                }

                // Prompt for course ID
                System.out.print("\nEnter Course ID to deactivate result from (or 'X' to go back): ");
                String courseID = scanner.nextLine().trim();

                if (courseID.equalsIgnoreCase("X")) {
                    break courseLoop; // Go back to student selection
                }

                // Validate course selection
                if (!coursesWithResults.containsKey(courseID)) {
                    System.out.println("Invalid Course ID or no active results for this course!");
                    System.out.print("Press Enter to continue...");
                    scanner.nextLine();
                    continue courseLoop;
                }

                List<Result> courseResults = coursesWithResults.get(courseID);

                if (courseResults.size() == 1) {
                    selectedResult = courseResults.get(0);
                    isValid = true;
                    break courseLoop;
                } else {
                    // Subject selection loop
                    subjectLoop: while (true) {
                        clearScreen.clearConsole();
                        menuList.deactivateResultIcon();
                        System.out.println(
                                "Student: " + selectedStudent.getName() + " (" + selectedStudent.getId() + ")");
                        System.out.println("Course: " + findCourseByID(courseID, facultyList).getCourseName() + " ("
                                + courseID + ")");
                        System.out.println("\nAvailable Subjects for this Course:");
                        System.out.printf("%-10s%-30s\n", "Code", "Subject Name");
                        System.out.println(
                                "---------------------------------------------------------------------------------");
                        for (Result result : courseResults) {
                            Subject subject = result.getSubject();
                            System.out.printf("%-10s%-30s\n",
                                    subject.getSubjectCode(),
                                    subject.getSubjectName());
                        }

                        System.out.print("\nEnter Subject Code to deactivate (or 'X' to go back): ");
                        String subjectCode = scanner.nextLine().trim();

                        if (subjectCode.equalsIgnoreCase("X")) {
                            continue courseLoop; // Go back to course selection
                        }

                        for (Result result : courseResults) {
                            if (result.getSubject().getSubjectCode().equalsIgnoreCase(subjectCode)) {
                                selectedResult = result;
                                isValid = true;
                                break subjectLoop;
                            }
                        }

                        System.out.println("\nInvalid Subject Code! Please try again.");
                        System.out.print("Press Enter to continue...");
                        scanner.nextLine();
                    }
                }

                if (isValid) {
                    break; // Exit the course loop if we have a valid selection
                }
            }
        } while (!isValid);

        clearScreen.clearConsole();
        menuList.deactivateResultIcon();
        System.out.println("Result to be deactivated:\n");
        System.out.println(
                "===================================================================================================================================================================================================");
        System.out.printf("%-15s%-20s%-10s%-40s%-10s%-40s%-10s%-10s%-10s%-10s\n",
                "Student ID", "Name", "Course", "Course Name", "Subject", "Subject Name", "Marks", "Grade", "GPA",
                "CGPA");
        System.out.println(
                "===================================================================================================================================================================================================");
        selectedResult.displayInfo(true);

        // Confirmation section with case-insensitive validation
        boolean validConfirm = false;
        String confirm = "";
        do {
            System.out.print("\nAre you sure you want to deactivate this result? (Y/N): ");
            confirm = scanner.nextLine().trim();

            if (confirm.equalsIgnoreCase("Y") || confirm.equalsIgnoreCase("N")) {
                validConfirm = true;
            } else {
                System.out.println("Invalid input! Please enter only 'Y' or 'N'.");
                System.out.print("Press Enter to try again...");
                scanner.nextLine();

                // Redisplay the result information
                clearScreen.clearConsole();
                menuList.deactivateResultIcon();
                System.out.println("Result to be deactivated:\n");
                System.out.println(
                        "===================================================================================================================================================================================================");
                System.out.printf("%-15s%-20s%-10s%-40s%-10s%-40s%-10s%-10s%-10s\n",
                        "Student ID", "Name", "Course", "Course Name", "Subject", "Subject Name", "Marks", "Grade",
                        "CGPA");
                System.out.println(
                        "===================================================================================================================================================================================================");
                selectedResult.displayInfo(true);
            }
        } while (!validConfirm);

        clearScreen.clearConsole();
        if (confirm.equalsIgnoreCase("Y")) {
            selectedResult.setIsActive(false);
            System.out.println(
                    "\n==========================================================================================");
            System.out.println("                      RESULT DEACTIVATED SUCCESSFULLY                         ");
            System.out.println(
                    "==========================================================================================");
            System.out.println("  Student: " + selectedResult.getStudent().getName() + " ("
                    + selectedResult.getStudent().getId() + ")");
            System.out.println("  Course : " + selectedResult.getCourse().getCourseName() + " ("
                    + selectedResult.getCourse().getCourseID() + ")");
            if (selectedResult.getSubject() != null) {
                System.out.println("  Subject: " + selectedResult.getSubject().getSubjectName() + " ("
                        + selectedResult.getSubject().getSubjectCode() + ")");
            }
            System.out.println("  Marks  : " + selectedResult.getMarks());
            System.out.println("  Grade  : " + selectedResult.getGrade());
            System.out.println(
                    "------------------------------------------------------------------------------------------");
            System.out.println("  Note: This result has been deactivated and will no longer be visible to students.");
            System.out.println(
                    "==========================================================================================");
            System.out.print("\nPress Enter to continue...");
            scanner.nextLine();
            return selectedResult;
        } else {
            System.out.println(
                    "\n==========================================================================================");
            System.out.println("                             DEACTIVATION CANCELLED                               ");
            System.out.println(
                    "==========================================================================================");
            System.out.println("  No changes have been made to the result record.");
            System.out.println(
                    "==========================================================================================");
            System.out.print("\nPress Enter to continue...");
            scanner.nextLine();
            return null;
        }
    }

    private Student findStudentById(String id, ArrayList<Student> students) {
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(id.trim())) {
                return s;
            }
        }
        return null;
    }

    private Course findCourseByID(String courseID, ArrayList<Faculty> facultyList) {
        for (Faculty faculty : facultyList) {
            for (Course course : faculty.getCourseList()) {
                if (course.getCourseID().equals(courseID)) {
                    return course;
                }
            }
        }
        return null;
    }

    private void displayAllActiveStudents(ArrayList<Student> studentList) {
        System.out.println("================================================================================");
        System.out.printf("%-15s%-20s%-10s\n", "Student ID", "Name", "Intake");
        System.out.println("================================================================================");
        for (Student student : studentList) {
            if (student.getisActive()) {
                System.out.printf("%-15s%-20s%-10d\n",
                        student.getId(),
                        student.getName(),
                        student.getIntakeYear());
            }
        }
    }

    private void waitForEnter(Scanner scanner) {
        System.out.print("Press Enter to continue...");
        scanner.nextLine(); // This properly consumes the Enter key
    }

    private void displayAllActiveResults(ArrayList<Result> resultList, ArrayList<Student> studentList,
            ArrayList<Faculty> facultyList) {
        // First check if there are any results to display
        if (resultList.isEmpty()) {
            System.out.println("No active results found.");
            return;
        }

        // Group results first by student, then by course
        Map<Student, Map<String, List<Result>>> groupedResults = resultList.stream()
                .filter(Result::getIsActive)
                .collect(Collectors.groupingBy(
                        Result::getStudent,
                        Collectors.groupingBy(r -> r.getCourse().getCourseID())));

        System.out.println(
                "================================================================================== All Active Results ===========================================================================================");
        System.out.printf("%-15s%-20s%-10s%-40s%-10s%-40s%-10s%-10s%-10s\n",
                "Student ID", "Name", "Course", "Course Name", "Subject", "Subject Name", "Marks", "Grade", "CGPA");
        System.out.println(
                "===================================================================================================================================================================================================");

        for (Map.Entry<Student, Map<String, List<Result>>> studentEntry : groupedResults.entrySet()) {
            Student student = studentEntry.getKey();
            boolean isFirstStudentRow = true;

            for (Map.Entry<String, List<Result>> courseEntry : studentEntry.getValue().entrySet()) {
                String courseId = courseEntry.getKey();
                List<Result> courseResults = courseEntry.getValue();
                boolean isFirstCourseRow = true;

                // Get course-specific CGPA
                double courseCGPA = student.getCourseCGPA(courseId);

                for (Result result : courseResults) {
                    System.out.printf("%-15s%-20s%-10s%-40s%-10s%-40s%-10.2f%-10s%-10s\n",
                            isFirstStudentRow ? student.getId() : "",
                            isFirstStudentRow ? student.getName() : "",
                            isFirstCourseRow ? result.getCourse().getCourseID() : "",
                            isFirstCourseRow ? result.getCourse().getCourseName() : "",
                            result.getSubject() != null ? result.getSubject().getSubjectCode() : "N/A",
                            result.getSubject() != null ? result.getSubject().getSubjectName() : "N/A",
                            result.getMarks(),
                            result.getGrade(),
                            isFirstCourseRow ? String.format("%.2f", courseCGPA) : "");

                    isFirstStudentRow = false;
                    isFirstCourseRow = false;
                }
                System.out.println(
                        "---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            }
        }
    }

    public void displaystudentresult(String loginID, ClearScreen clearScreen, MenuList menuList,
            Scanner scanner, ArrayList<Student> studentList, ArrayList<Result> resultList) {
        clearScreen.clearConsole();
        menuList.viewResultIcon();

        // Find the student first
        Student student = findStudentById(loginID, studentList);
        if (student == null) {
            System.out.println("Student not found!");
            waitForEnter(scanner);
            return;
        }

        // Update CGPA before displaying
        updateStudentCGPA(student, resultList);

        if (resultList.isEmpty()) {
            System.out.println("There are no results found.");
        } else {
            System.out.println(
                    "================================================================================== Student Results ==================================================================================");
            System.out.printf("%-15s%-20s%-10s%-40s%-10s%-40s%-10s%-10s%-10s%-10s\n",
                    "Student ID", "Name", "Course", "Course Name", "Subject", "Subject Name", "Marks", "Grade", "GPA",
                    "CGPA");
            System.out.println(
                    "=====================================================================================================================================================================================");

            List<Result> studentResults = resultList.stream()
                    .filter(r -> r.getIsActive() && r.getStudent().getId().equals(loginID))
                    .collect(Collectors.toList());

            if (studentResults.isEmpty()) {
                System.out.println("No active results found for this student.");
            } else {
                // Group by course
                Map<String, List<Result>> courseGroups = studentResults.stream()
                        .collect(Collectors.groupingBy(
                                r -> r.getCourse().getCourseID(),
                                LinkedHashMap::new,
                                Collectors.toList()));

                boolean isFirstCourse = true;
                for (Map.Entry<String, List<Result>> entry : courseGroups.entrySet()) {
                    List<Result> courseResults = entry.getValue();
                    String courseId = entry.getKey();

                    // Get course CGPA
                    double courseGPA = student.getCourseCGPA(courseId);

                    boolean isFirstResult = true;
                    for (Result result : courseResults) {
                        System.out.printf("%-15s%-20s%-10s%-40s%-10s%-40s%-10s%-10s%-10s%-10s\n",
                                isFirstCourse ? student.getId() : "",
                                isFirstCourse ? student.getName() : "",
                                isFirstResult ? result.getCourse().getCourseID() : "",
                                isFirstResult ? result.getCourse().getCourseName() : "",
                                result.getSubject() != null ? result.getSubject().getSubjectCode() : "N/A",
                                result.getSubject() != null ? result.getSubject().getSubjectName() : "N/A",
                                result.getMarks(),
                                result.getGrade(),
                                result.getGradePoint(),
                                isFirstResult ? courseGPA : "");

                        isFirstCourse = false;
                        isFirstResult = false;
                    }
                    System.out.println(
                            "-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                }
            }
        }

        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

}