package com.example.bleep;

public class User_Information_fbfs
{
    private String name;
    private String gender;
    private String dob;
    private String age;
    private String blood_grp;
    private String mail;
    private String password;

    public User_Information_fbfs(String n , String g , String d , String a , String b ,String m)
    {
        this.name = n;
        this.gender = g;
        this.dob = d;
        this.age = a;
        this.blood_grp = b;
        this.mail = m;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBlood_grp() {
        return blood_grp;
    }

    public void setBlood_grp(String blood_grp) {
        this.blood_grp = blood_grp;
    }
}
