package ExaminationController;

public class Examination {
    private String examID;
    private String examName;
    private String examDate;
    private String examTime;
    private Venue venue;
    private static int counter = 1000;

    public Examination (String exam, String examDate, String examTime, Venue venue) {
        this.examID = "EX" + counter++;
        this.examName = exam;
        this.examDate = examDate;
        this.examTime = examTime;
        this.venue = venue;
    }

    public String getExamID() {
        return examID;
    }

    public String getExam() {
        return examName;
    }

    public String getExamDate() {
        return examDate;
    }

    public String getExamTime() {
        return examTime;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public void setExam(String exam) {
        this.examName = exam;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }
    
    public void displayInfo(){
        System.out.print("\nSubject\t\t:"+examName+"\nExam Date\t:"+examDate+"\nExamTime\t:"+examTime +"\nVenue\t\t:"+venue.getName());
    }
    public String toString() {
        return String.format("%-8s%-12s%-10s%-5s", "examID", "exam", "examDate", "examTime");
    }
    
    
    public void displayInfoTable(){
        System.out.printf("%-8s%-20s%-12s%-5s\n", examID,examName, examDate, examTime);
    }
}
