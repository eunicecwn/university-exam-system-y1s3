package UserController;

public abstract class Staff extends Person {
    private boolean isActive;
    private String position;
    private String department;

    // Constructor
    protected Staff(String id, String name, String gender, String icNo, String phoneNo, String position,
            String department) {
        super(id, name, gender, icNo, phoneNo);
        this.isActive = true;
        this.position = position;
        this.department = department;
    }

    // Getter
    public String getPosition() {
        return position;
    }

    public String getDepartment() {
        return department;
    }

    public boolean getIsActive() {
        return isActive;
    }

    // Setter
    public void setPosition(String position) {
        this.position = position;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    // This overrides Person's abstract generateId() method
    @Override
    public String generateId() {
        return getId(); // Return current ID of this object
    }

    @Override
    public void displayInfo() {
        super.displayInfo(); // Calls Person's displayInfo()
        System.out.println("Position\t\t: " + position);
        System.out.println("Department\t\t: " + department);
    }
}
