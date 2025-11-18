import java.util.ArrayList;
import java.util.Scanner;
import UserController.*;
import ExtraFunction.*;
import ResultController.*;
import CourseController.*;
import ExaminationController.*;

public class LccnUni {

    ArrayList<Student> studentList = new ArrayList<>(); // Dynamic List
    ArrayList<FacultyMember> facultyMemberList = new ArrayList<>(); // Dynamic List
    ArrayList<Admin> adminList = new ArrayList<>(); // Dynamic List
    ArrayList<Faculty> facultyList = new ArrayList<>();
    ArrayList<Subject> subjectList = new ArrayList<>();
    ArrayList<Result> resultList = new ArrayList<>();
    ArrayList<Venue> venueList = new ArrayList<>();
    ArrayList<Examination> examinationList = new ArrayList<>();

    // Use an instance of the class
    public static void main(String[] args) {
        LccnUni app = new LccnUni();
        app.start(); // now inside non-static method, can use non-static fields
    }

    public void start() {
        // === Pre-Listed Data Setup ===

        // Faculties
        Faculty faculty1 = new Faculty("FSSH", "Faculty of Social Science and Humanities");
        Faculty faculty2 = new Faculty("FOET", "Faculty of Engineering Technology");
        Faculty faculty3 = new Faculty("FASC", "Faculty of Applied Science");
        Faculty faculty4 = new Faculty("FBIZ", "Faculty of Business");

        facultyList.add(faculty1);
        facultyList.add(faculty2);
        facultyList.add(faculty3);
        facultyList.add(faculty4);

        // Courses
        Course course1 = new Course("Bachelor of Psychology");
        Course course2 = new Course("Diploma in Electrical Engineering");
        Course course3 = new Course("Diploma in Software Engineering");
        Course course4 = new Course("Bachelor of Environmental Science");
        Course course5 = new Course("Bachelor of Biotechnology");
        Course course6 = new Course("Diploma in Accounting");
        Course course7 = new Course("Bachelor of Marketing");

        faculty1.addCourse(course1);
        faculty2.addCourse(course2);
        faculty2.addCourse(course3);
        faculty3.addCourse(course4);
        faculty3.addCourse(course5);
        faculty4.addCourse(course6);
        faculty4.addCourse(course7);

        // Subjects
        subjectList.add(new Subject("Introduction to Psychology", 3));
        subjectList.add(new Subject("Cognitive Psychology", 3));
        subjectList.add(new Subject("Circuit Theory", 4));
        subjectList.add(new Subject("Digital Electronics", 3));
        subjectList.add(new Subject("Object-Oriented Programming", 3));
        subjectList.add(new Subject("Software Project Management", 3));
        subjectList.add(new Subject("Environmental Chemistry", 3));
        subjectList.add(new Subject("Ecology and Biodiversity", 4));
        subjectList.add(new Subject("Molecular Biology", 4));
        subjectList.add(new Subject("Genetic Engineering", 3));
        subjectList.add(new Subject("Financial Accounting", 3));
        subjectList.add(new Subject("Cost Accounting", 3));
        subjectList.add(new Subject("Marketing Principles", 3));
        subjectList.add(new Subject("Consumer Behavior", 3));

        course1.addSubject(subjectList.get(0));
        course1.addSubject(subjectList.get(1));
        course2.addSubject(subjectList.get(2));
        course2.addSubject(subjectList.get(3));
        course3.addSubject(subjectList.get(4));
        course3.addSubject(subjectList.get(5));
        course4.addSubject(subjectList.get(6));
        course4.addSubject(subjectList.get(7));
        course5.addSubject(subjectList.get(8));
        course5.addSubject(subjectList.get(9));
        course6.addSubject(subjectList.get(10));
        course6.addSubject(subjectList.get(11));
        course7.addSubject(subjectList.get(12));
        course7.addSubject(subjectList.get(13));

        // Students
        studentList.add(new Student("Alice Tan", "Female", "001122-05-6789", "012-3456789", 2023));
        studentList.add(new Student("Bob Lim", "Male", "990101-10-2345", "011-9876543", 2022));
        studentList.add(new Student("Carmen Yeo", "Female", "010203-04-5678", "013-2223344", 2024));
        studentList.add(new Student("Daniel Chia", "Male", "981212-06-3456", "016-9988776", 2023));

        studentList.get(0).enrollCourse(course1);
        studentList.get(0).enrollCourse(course2);
        studentList.get(1).enrollCourse(course2);
        studentList.get(2).enrollCourse(course4);
        studentList.get(3).enrollCourse(course6);

        // Faculty Members
        facultyMemberList.add(new FacultyMember("John Doe", "Male", "123456-78-9012", "0123456789", "Lecturer",
                "Psychology Dept", "Behavioral Science", "Cognitive Research", faculty1));
        facultyMemberList.add(new FacultyMember("Jane Smith", "Female", "987654-32-1098", "0198765432", "Professor",
                "Engineering Dept", "Robotics", "Embedded Systems", faculty2));
        facultyMemberList.add(
                new FacultyMember("Aisyah Rahim", "Female", "901010-10-1010", "0145558888", "Senior Lecturer",
                        "Bio Dept", "Genetics", "Biotechnology", faculty3));
        facultyMemberList.add(new FacultyMember("Kelvin Goh", "Male", "880505-11-2323", "0177773333", "Lecturer",
                "Business Dept", "Marketing", "Consumer Behavior", faculty4));

        // Admins
        adminList.add(new Admin("Admin Tan", "Female", "888888-88-8888", "012-8888888", "Manager", "Admin Department"));

        // Venues
        venueList.add(new Venue("B01", "Lecture ABA", 50));
        venueList.add(new Venue("B02", "Lecture ABB", 100));
        venueList.add(new Venue("B03", "Lecture ABC", 150));
        venueList.add(new Venue("B04", "Lecture ABD", 200));

        // Examinations
        examinationList.add(new Examination("Introduction to Psychology", "2024-04-30", "08:30", venueList.get(0)));
        examinationList.add(new Examination("Circuit Theory", "2025-06-20", "09:00", venueList.get(1)));
        examinationList.add(new Examination("Environmental Chemistry", "2025-06-21", "10:00", venueList.get(2)));
        examinationList.add(new Examination("Financial Accounting", "2025-06-22", "13:00", venueList.get(3)));

        boolean Studentloginstatus = false;
        boolean FacultyandAdminloginstatus = false;
        boolean isfaculty = false;
        boolean isadmin = false;
        boolean loginMenu = true;
        String loginID = "";

        Scanner scanner = new Scanner(System.in);
        UserController userController = new UserController();
        MenuList menuList = new MenuList();
        ClearScreen clearScreen = new ClearScreen();
        ValidationCheck validation = new ValidationCheck();
        ExaminationController examinationController = new ExaminationController();
        CourseController courseController = new CourseController();
        ResultController resultController = new ResultController();

        while (true) {

            while (loginMenu == true) {
                int UserChoice;
                switch (UserChoice = validation.validationinputForMenu(scanner, loginMenu, Studentloginstatus,
                        isfaculty, isadmin)) {
                    case 1:
                        boolean case1condition = true;
                        int case1runtime = 0;
                        while (case1condition == true) {

                            boolean inputN = true;
                            // check the runtime of the loop
                            if (case1runtime > 1) {
                                boolean inputYorN = false;
                                while (inputYorN == false) {

                                    clearScreen.clearConsole();
                                    menuList.loginMenu();
                                    System.out.print(UserChoice);
                                    // when wrong input more then 2 times, ask user to go back to menu or not
                                    System.out.print("\nDo you want to try selecting again? (Y/N): ");
                                    char backToMenuChoice = scanner.next().charAt(0);
                                    scanner.nextLine();
                                    inputYorN = false;
                                    if (validation.validationYesNo(backToMenuChoice)
                                            && (backToMenuChoice == 'y' || backToMenuChoice == 'Y')) {
                                        case1condition = false;
                                        inputYorN = true;
                                        inputN = false;
                                        break;
                                    } else if (backToMenuChoice == 'n' || backToMenuChoice == 'N') {
                                        inputYorN = true;
                                        inputN = true;
                                    }
                                }
                            }
                            case1runtime += 1;
                            if (inputN == true && case1condition == true) {
                                clearScreen.clearConsole();
                                menuList.loginMenu();
                                System.out.println(UserChoice);
                                String inputStudentID;
                                System.out.print("Enter Student ID: ");
                                inputStudentID = scanner.nextLine();
                                // check the input student id is valid or not
                                if (validation.validationStudentid(inputStudentID, scanner) == true) {
                                    if (userController.checkStudentID_Login(inputStudentID, studentList, scanner)) {

                                        System.out.print("Enter Password\t: ");
                                        String inputPassword = scanner.nextLine();
                                        // check the input password is valid or not
                                        if (userController.checkStudentPassword_Login(inputStudentID, inputPassword,
                                                studentList)) {
                                            Studentloginstatus = true;
                                            loginMenu = false;
                                            case1condition = false;
                                            loginID = inputStudentID;

                                        } else {
                                            System.out.println("Invalid password. Please try again.");
                                            System.out.print("Press enter key to continue....");
                                            scanner.nextLine();

                                        }

                                    } else {
                                        System.out.println("Student ID not found. Please try again.");
                                    }
                                }

                            }
                        }
                        break;
                    case 2:
                        boolean case2condition = true;
                        int case2runtime = 0;
                        while (case2condition == true) {
                            boolean inputN = true;
                            if (case2runtime > 1) {
                                boolean inputYorN = false;
                                while (inputYorN == false) {

                                    clearScreen.clearConsole();
                                    menuList.loginMenu();
                                    System.out.print(UserChoice);
                                    System.out.print("\nDo you want to try selecting again? (Y/N): ");
                                    char backToMenuChoice = scanner.next().charAt(0);
                                    scanner.nextLine();
                                    inputYorN = false;
                                    if (validation.validationYesNo(backToMenuChoice)
                                            && (backToMenuChoice == 'y' || backToMenuChoice == 'Y')) {
                                        case2condition = false;
                                        inputYorN = true;
                                        inputN = false;
                                        break;
                                    } else if (backToMenuChoice == 'n' || backToMenuChoice == 'N') {
                                        inputYorN = true;
                                        inputN = true;
                                    }
                                }
                            }
                            case2runtime += 1;
                            if (inputN == true && case2condition == true) {
                                clearScreen.clearConsole();
                                menuList.loginMenu();
                                System.out.println(UserChoice);
                                String inputFacultyIDorAdminID;
                                System.out.print("Enter Staff ID: ");
                                inputFacultyIDorAdminID = scanner.nextLine();

                                if (validation.validationFacultyidorAdminid(inputFacultyIDorAdminID, scanner) == true) {
                                    if (userController.checkFmID(inputFacultyIDorAdminID, facultyMemberList)) {
                                        // Faculty login
                                        System.out.print("Enter Password: ");
                                        String inputPassword = scanner.nextLine();
                                        if (userController.checkFmPassword_Login(inputFacultyIDorAdminID, inputPassword,
                                                facultyMemberList)) {
                                            FacultyandAdminloginstatus = true;
                                            isfaculty = true;
                                            loginMenu = false;
                                            case2condition = false;
                                            loginID = inputFacultyIDorAdminID;
                                        } else {
                                            System.out.println("Invalid password. Please try again.");
                                            System.out.print("Press enter key to continue....");
                                            scanner.nextLine();
                                        }
                                    } else if (userController.checkAdminID(inputFacultyIDorAdminID, adminList)) {
                                        // Admin login
                                        System.out.print("Enter Password: ");
                                        String inputPassword = scanner.nextLine();

                                        if (userController.checkAmPassword_Login(inputFacultyIDorAdminID, inputPassword,
                                                adminList)) {
                                            FacultyandAdminloginstatus = true;
                                            isadmin = true; // Set admin flag correctly
                                            loginMenu = false;
                                            case2condition = false;
                                            loginID = inputFacultyIDorAdminID;
                                        } else {
                                            System.out.println("Invalid password. Please try again.");
                                            System.out.print("Press enter key to continue....");
                                            scanner.nextLine();
                                        }
                                    } else {
                                        System.out.println("Faculty ID or Admin ID not found. Please try again.");
                                        System.out.print("Press enter key to continue....");
                                        scanner.nextLine();
                                    }
                                }

                            }
                        }
                        break;

                    default:
                        System.out.println("Exiting system...");
                        scanner.close();
                        return;
                }

            }
            // * Student Menu *//
            while (Studentloginstatus == true) {
                int StudentChoice = 0;
                switch (StudentChoice = validation.validationinputForMenu(scanner, loginMenu, Studentloginstatus,
                        isfaculty, isadmin)) {
                    case 1:
                        clearScreen.clearConsole();
                        examinationController.timetable(loginID, studentList, examinationList);
                        System.out.print("\nPress enter key to continue..... ");
                        scanner.nextLine();

                        break;
                    case 2:
                        resultController.displaystudentresult(loginID, clearScreen, menuList, scanner, studentList,
                                resultList);
                        break;

                    default:
                        Studentloginstatus = false;
                        loginMenu = true;
                        break;
                }

            }
            // * Faculty Menu *//
            while (FacultyandAdminloginstatus == true && isfaculty == true) {
                resultController.displayResultUI(scanner, clearScreen, validation, menuList, facultyMemberList,
                        resultList, studentList, facultyList, examinationList);
                FacultyandAdminloginstatus = false;
                isfaculty = false;
                loginMenu = true;
            }
            // * Admin Menu *//
            while (FacultyandAdminloginstatus == true && isadmin == true) {
                userController.displayStaffUI(scanner, clearScreen, validation, menuList, courseController, studentList,
                        facultyMemberList, adminList, facultyList, subjectList, examinationController, examinationList,
                        venueList);
                ;
                FacultyandAdminloginstatus = false;
                isadmin = false;
                loginMenu = true;

            }
        }
    }
}