package com.example.hp.homepage;

import android.app.Application;

public class GlobalClass extends Application {

    private String cgpa;
    private String companyid;
    private String branch;
    private String company_cg_criteria;
    private String company_description;

    public String getCgpa() {
        return cgpa;
    }

    public void setCgpa(String cgpa) {
        this.cgpa = cgpa;
    }

    public String getCompanyid() { return companyid; }

    public void setCompanyid(String companyid) { this.companyid = companyid; }

    public String getCompany_cg_criteria(){return company_cg_criteria;}

    public void setCompany_cg_criteria(String company_cg_criteria){ this.company_cg_criteria= company_cg_criteria; }

    public String getCompany_description() {
        return company_description;
    }

    public void setCompany_description(String company_description) { this.company_description = company_description; }

    public String getBranch() {return branch;}

    public void setBranch(String branch) { this.branch= branch; }
}
