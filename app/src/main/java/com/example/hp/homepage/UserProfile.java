package com.example.hp.homepage;

public class UserProfile {

    public String Age;
    public String CollegeID;
    public String Name;
    public String CGPA;
    public String Phone;
    public String Email;
    public String Gender;
    public String Branch;
    public String tenScore;
    public String twelveScore;
    public String PeopleWithDissabilities;
    public String GapYear;
    public String Category;

    public UserProfile(){

    }

    public UserProfile(String userAge, String usercolid, String usernm, String usercgpa, String userphone, String editTextEmail, String mGenderOption, String userBranch, String userTenscore, String userTwelvescore, String mPwdOption, String mGYOption, String mCategoryOption) {
        this.Age = userAge;
        this.CollegeID = usercolid;
        this.Name = usernm;
        this.CGPA = usercgpa;
        this.Phone = userphone;
        this.Email = editTextEmail;
        this.Gender = mGenderOption;
        this.Branch = userBranch;
        this.tenScore= userTenscore;
        this.twelveScore= userTwelvescore;
        this.PeopleWithDissabilities= mPwdOption;
        this.GapYear=mGYOption;
        this.Category=mCategoryOption;
    }

}
