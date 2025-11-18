package CourseController;

import java.util.ArrayList;

public class Faculty {
    private String facultyCode;
    private String facultyName;
    private boolean isActive = true;
    private ArrayList<Course> courseList = new ArrayList<>();;  

    // Constructor
    public Faculty(String facultyCode, String facultyName) {
        this.facultyCode = facultyCode;
        this.facultyName = facultyName;
        this.isActive = true;
    }

    // Getters
    public String getFacultyCode() {
        return facultyCode;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public boolean getisActive() {
        return isActive;
    }

    public ArrayList<Course> getCourseList() {
        return courseList;
    }

    // Setters
    public void setFacultyCode(String facultyCode) {
        this.facultyCode = facultyCode;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    
    public void setCourseList(ArrayList<Course> courseList) {
        this.courseList = courseList;
    }

    public void addCourse(Course course) {
        courseList.add(course);
    }

    // Display Faculty Information
    public void displayInfo() {
        System.out.printf("%-15s%-15s\n", facultyCode, facultyName);
    }
}
