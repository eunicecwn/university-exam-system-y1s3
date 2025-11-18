package CourseController;

import java.util.ArrayList;

public class Course {
    private static int courseIDCounter = 1000; // Static counter to generate unique course IDs
    private String courseID;
    private String courseName;
    private boolean isActive;
    private ArrayList<Subject> subjectList;


    // Constructor
    public Course(String courseName) {
        this.courseID = generateCourseID(); // Automatically generate course ID
        this.courseName = courseName;
        this.isActive = true;
        this.subjectList = new ArrayList<>();
    }

    // Generate a new course ID
    private static String generateCourseID() {
        return String.format("C%4d", courseIDCounter++);
    }

    // Getter methods
    public String getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public boolean getisActive() {
        return isActive;
    }

    public ArrayList<Subject> getSubjectList() {
        return subjectList;
    }

    // Setter methods
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setSubjectList(ArrayList<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    public void addSubject(Subject subject) {
        if (subjectList == null) {
            subjectList = new ArrayList<>();
        }
        if (!subjectList.contains(subject)) {
            subjectList.add(subject);
        }
    }

    //Calculate the credit hours
    public int getCreditHours() {
        int total = 0;
        for (Subject s : subjectList) {
            total += s.getCreditHour();
        }
        return total;
    }

    // Display course information
    public void displayInfo() {
        System.out.println("Course ID\t: " + courseID);
        System.out.println("Course Name\t: " + courseName);
    }
}
