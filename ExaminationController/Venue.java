package ExaminationController;
 
public class Venue {
    private String venueID;
    private String name;
    private int capacity;

    public Venue(String venueID, String name, int capacity) {
        this.venueID = venueID;
        this.name = name;
        this.capacity = capacity;
    }

    public String getVenueID() {
        return venueID;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setVenueID(String venueID) {
        this.venueID = venueID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
     return "Venue ID: " + venueID + 
     "\nName: " + name + 
     "\nCapacity: " + capacity;

    }

}