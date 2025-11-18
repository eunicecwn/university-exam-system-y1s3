package UserController;

public class Admin extends Staff {
    private static int idCounter = 10000;

    public Admin(String name, String gender, String icNo, String phoneNo, String position, String department) {
        super(generateNewId(), name, gender, icNo, phoneNo, position, department);
    }

    // Generate Admin ID and store it in Person's ID field
    private static String generateNewId() {
        return String.format("A%05d", idCounter++);
    }

    // This overrides Person's abstract generateId() method
    @Override
    public String generateId() {
        return getId(); // Return current ID of this object
    }

    @Override
    public void displayInfo() {
        System.out.println("Admin ID\t\t: " + super.getId());
        super.displayInfo();
    }
}
