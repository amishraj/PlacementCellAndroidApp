package com.example.hp.homepage;

public class applied {
    private String Name; //maybe need to write private String Name
    private String CGPA;//maybe need to write private String CGPA
    private String CollegeID;
    private String Branch;

    public applied(){
        //empty constructor needed
    }

    public applied(String Name, String CGPA, String CollegeID, String Branch){
        this.Name= Name;
        this.CGPA= CGPA;
        this.Branch= Branch;
        this.CollegeID= CollegeID;
    }

    public String getName() {
        return Name;
    }

    public String getBranch() {return Branch;}

    public String getCGPA() {
        return CGPA;
    }

    public String getCollegeID() { return CollegeID; }
}
