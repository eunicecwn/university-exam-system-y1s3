package UserController;

public abstract class Person {
    private String id;
    private String name;
    private String password;
    private String gender;
    private String email;
    private String icNo;
    private String phoneNo;
    

    //Constuctor
    protected Person(String id, String name, String gender, String icNo, String phoneNo) {
        this.id = id;
        this.name = name;
        this.password = name.charAt(0) + String.valueOf(id ) + "@";
        this.gender = gender;
        this.email = name.toLowerCase().replace(" ", "") + "-" + icNo.substring(0,4) + "@lccn.edu.my"; //replace(" ", "") --> ("old value" , "new value") --> remove the space between the name
        this.icNo = icNo;
        this.phoneNo = phoneNo;
    }

    // Getter 
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }
    
    public String getEmail() {
        return email;
    }

    public String getIcNo() {
        return icNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    // Setter
    public void setName (String name){
        this.name = name ;
    }

    public void setPassword (String password){
        this.password = password ;
    }

    public void setGender(String gender){
        this.gender = gender ;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setIcNo(String icNo){
        this.icNo=icNo;
    }

    public void setPhoneNo(String phoneNo){
        this.phoneNo = phoneNo;
    }

    public void setID(String id){
        this.id = id;
    }

    public abstract String generateId();

    public void displayInfo() {
        System.out.println("Name\t\t\t: " + getName());
        System.out.println("Gender (M/F)\t\t: " + getGender());
        System.out.println("IC\t\t\t: " + getIcNo());
        System.out.println("Email\t\t\t: " + getEmail());
        System.out.println("Contact\t\t\t: + 60" + getPhoneNo());
        System.out.println("Password\t\t: " + getPassword());
    }
}
