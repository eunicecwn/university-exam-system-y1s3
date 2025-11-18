package UserController;

import CourseController.Faculty;

public class FacultyMember extends Staff {
    private static int idCounter = 10000;
    private Faculty faculty;
    private String areaInterest;
    private String specialiseSubject;

    public FacultyMember(String name, String gender, String icNo, String phoneNo, String position, String department,String areaInterest,
            String specialiseSubject, Faculty faculty) {
        super(generateNewId(), name, gender, icNo, phoneNo, position, department);
        this.areaInterest = areaInterest;
        this.specialiseSubject = specialiseSubject;
        this.faculty = faculty;
    }

    // getter
    public Faculty getFaculty() {
        return faculty;
    }

    public String getAreaInterest() {
        return areaInterest;
    }

    public String getSpeciliseSubject() {
        return specialiseSubject;
    }

    // Setters
    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public void setAreaInterest(String areaInterest) {
        this.areaInterest = areaInterest;
    }

    public void setSpecialiseSubject(String specialiseSubject) {
        this.specialiseSubject = specialiseSubject;
    }

    // This static method generates the ID for constructor
    private static String generateNewId() {
        return String.format("FM%05d", idCounter++);
    }

    // This overrides Person's abstract generateId() method
    @Override
    public String generateId() {
        return getId(); // Return current ID of this object
    }

    @Override
    public void displayInfo() {
        System.out.println("Faculty Member ID\t: " + super.getId());
        super.displayInfo();
        System.out.println("Area of Interest\t: " + areaInterest);
        System.out.println("Specialise Subject\t: " + specialiseSubject);
        System.out.println("Faculty\t\t\t: " + getFaculty().getFacultyName() + " (Code : " + getFaculty().getFacultyCode() + ")");
    }
}
