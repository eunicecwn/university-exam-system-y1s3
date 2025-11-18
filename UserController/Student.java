package UserController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import CourseController.Course;

public class Student extends Person {
    // Private attributes
    private int intakeYear;
    private static int studentIdCounter = 10000;
    private boolean isActive = true;
    private List<Course> enrolledCourses;
    private double cgpa;
    private Map<String, Double> courseCGPAs = new HashMap<>();

    // Constructor
    public Student(String name, String gender, String icNo, String phoneNo, int intakeYear) {
        super(generateNewId(intakeYear), name, gender, icNo, phoneNo);
        this.intakeYear = intakeYear;
        this.enrolledCourses = new ArrayList<>(); // Initialize the list
    }

    // Thread-safe ID generation
    public static String generateNewId(int intakeYear) {
        return String.format("%dS%d", intakeYear, studentIdCounter++);
    }

    // This overrides Person's abstract generateId() method
    @Override
    public String generateId() {
        return getId(); // Return current ID of this object
    }

    // Getters
    public int getIntakeYear() {
        return intakeYear;
    }

    public boolean getisActive() {
        return isActive;
    }

    public List<Course> getEnrolledCourses() {
        return new ArrayList<>(enrolledCourses); // Return copy for encapsulation
    }

    public double getCgpa() {
        return cgpa;
    }

    public double getCourseCGPA(String courseId) {
        return courseCGPAs.getOrDefault(courseId, 0.0);
    }

    // Setters
    public void setIntakeYear(int intakeYear) {
        this.intakeYear = intakeYear;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    // Improved enrollment method
    public void enrollCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
        }
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public void setCourseCGPA(String courseId, double cgpa) {
        courseCGPAs.put(courseId, cgpa);
    }

    @Override
    public void displayInfo() {
        System.out.println("Student ID\t\t: " + getId());
        super.displayInfo();
        System.out.println("Intake Year\t\t: " + intakeYear);
        System.out.println("Enrolled Courses:");
        if (enrolledCourses.isEmpty()) {
            System.out.println("\tNone");
        } else {
            for (Course course : enrolledCourses) {
                System.out.println("\t- " + course.getCourseName() +
                        " (" + course.getCourseID() + ")");
            }
        }
    }

    public void generateResult() {
        System.out.println("Student ID\t\t: " + getId());
        System.out.println("Student Name\t\t: " + super.getName());
        System.out.print("Enrolled Courses\t: ");
        if (enrolledCourses.isEmpty()) {
            System.out.println("None");
        } else {
            String courses = enrolledCourses.stream()
                    .map(course -> course.getCourseName() + " (" + course.getCourseID() + ")")
                    .collect(Collectors.joining(", "));
            System.out.println(courses);
        }
    }

    public String Overview() {
        List<Course> courses = getEnrolledCourses();
        StringBuilder courseList = new StringBuilder();
        int maxDisplay = 2; // show only 3 courses

        for (int i = 0; i < courses.size(); i++) {
            if (i >= maxDisplay) {
                courseList.append("...");
                break;
            }
            Course course = courses.get(i);
            courseList.append(course.getCourseName())
                    .append(", ");
        }
        if (courseList.length() > 0 && !courseList.toString().endsWith("...")) {
            courseList.setLength(courseList.length() - 2); // remove last ", "
        }

        return String.format("%-13s%-15s%-9s%-13d%-15s%-30s%-15s%-15s%-30s\n",
                getId(), super.getName(), super.getGender(), getIntakeYear(), super.getIcNo(),
                super.getEmail(), super.getPhoneNo(), super.getPassword(), courseList.toString());
    }

}