package CourseController;

public class Subject {
    private static int codeCounter = 1; // Start from 1
    private String subjectCode;
    private String subjectName;
    private int creditHour;
    private boolean isActive = true;

    // Constructor
    public Subject(String subjectName, int creditHour) {
        this.subjectName = subjectName;
        this.creditHour = creditHour;
        this.isActive = true;
        this.subjectCode = generateSubjectCode(subjectName, creditHour, codeCounter++);
    }

    // Getters
    public String getSubjectCode() {
        return subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getCreditHour() {
        return creditHour;
    }

    public boolean getisActive() {
        return isActive;
    }

    // Setters
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setCreditHour(int creditHour) {
        this.creditHour = creditHour;
    }

    // Subject Code Generator
    private String generateSubjectCode(String name, int creditHours, int counter) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Subject name cannot be null or empty");
        }

        String[] words = name.trim().split("\\s+");
        StringBuilder code = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                code.append(Character.toUpperCase(word.charAt(0)));
            }
        }

        // Pad or trim to 4 letters
        while (code.length() < 4)
            code.append('X');
        if (code.length() > 4)
            code.setLength(4);

        String creditHourStr = String.format("%02d", creditHours);
        String counterStr = String.format("%03d", counter);

        return code.toString() + creditHourStr + counterStr;
    }

    // Display methods
    public void displayInfo() {
        System.out.println("Subject Code\t: " + subjectCode);
        System.out.println("Subject Name\t: " + subjectName);
        System.out.println("Credit Hours\t: " + creditHour);
    }

    public void displayInfoTable() {
        System.out.printf("%-20s%-45s%-15s\n", subjectCode, subjectName, creditHour);
    }
}
