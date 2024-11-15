package com.example.bleep;

public class Login_Info
{
    private String usr_nm , pass;

    public Login_Info(String us , String pw)
    {
        this.usr_nm = us;
        this.pass = pw;
    }

    public String getUsr_nm() {
        return usr_nm;
    }

    public void setUsr_nm(String usr_nm) {
        this.usr_nm = usr_nm;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
