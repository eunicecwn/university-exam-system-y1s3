package ResultController;

import UserController.Student;

import CourseController.*;

public class Result {
    private Student student;
    private Course course;
    private double marks;
    private String grade;
    private boolean isActive = true;
    private Subject subject;

    public Result(Student student, Course course, double marks) {
        this.student = student;
        this.course = course;
        this.marks = marks;
        this.grade = calculateGrade(marks);
    }


    // Getters
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public double getMarks() {
        return marks;
    }

    public String getGrade() {
        return grade;
    }

    public boolean getIsActive() {
        return isActive;
    }


    // Setters
    public void setMarks(double marks) {
        this.marks = marks;
        this.grade = calculateGrade(marks);
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void displayInfo(boolean showCGPA) {
        if (showCGPA) {
            // Display with CGPA (only show CGPA for the first result of each student)
            System.out.printf("%-15s%-20s%-10s%-40s%-10s%-40s%-10.2f%-10s%-10s%-10s\n",
            student.getId(),
                    student.getName(),
                    course.getCourseID(),
                    course.getCourseName(),
                    subject != null ? subject.getSubjectCode() : "N/A",
                    subject != null ? subject.getSubjectName() : "N/A",
                    marks,
                    grade,
                    getGradePoint(),  // Subject GPA
                    student.getCourseCGPA(course.getCourseID())); // Course GPA
        } else {
            // Normal display without CGPA
            System.out.printf("%-15s%-20s%-10s%-40s%-10s%-40s%-10.2f%-10s%-10.2f\n",
                    student.getId(),
                    student.getName(),
                    course.getCourseID(),
                    course.getCourseName(),
                    subject != null ? subject.getSubjectCode() : "N/A",
                    subject != null ? subject.getSubjectName() : "N/A",
                    marks,
                    grade,
                    getGradePoint()); // Subject GPA
        }
    }

    public String calculateGrade(double marks) {
        if (marks >= 90)
            return "A+";
        else if (marks >= 80)
            return "A";
        else if (marks >= 70)
            return "B";
        else if (marks >= 60)
            return "C";
        else if (marks >= 50)
            return "D";
        else
            return "F";
    }

    // New method to get grade point
    public double getGradePoint() {
        switch (grade) {
            case "A+":
                return 4.0;
            case "A":
                return 4.0;
            case "B":
                return 3.0;
            case "C":
                return 2.0;
            case "D":
                return 1.0;
            default:
                return 0.0;
        }
    }

   
    

}