package com.example.bleep;

public class tab1_blood_pressure_details
{
    String sis_bp , dia_bp , sis_var_bp , dia_var_bp , tab1_date;

    public tab1_blood_pressure_details(String s, String d, String sv, String dv , String t1d)
    {
        this.sis_bp = s;
        this.dia_bp = d;
        this.sis_var_bp = sv;
        this.dia_var_bp = dv;
        this.tab1_date = t1d;
    }

    public String getTab1_date() {
        return tab1_date;
    }

    public void setTab1_date(String tab1_date) {
        this.tab1_date = tab1_date;
    }

    public String getSis_bp() {
        return sis_bp;
    }

    public void setSis_bp(String sis_bp) {
        this.sis_bp = sis_bp;
    }

    public String getDia_bp() {
        return dia_bp;
    }

    public void setDia_bp(String dia_bp) {
        this.dia_bp = dia_bp;
    }

    public String getSis_var_bp() {
        return sis_var_bp;
    }

    public void setSis_var_bp(String sis_var_bp) {
        this.sis_var_bp = sis_var_bp;
    }

    public String getDia_var_bp() {
        return dia_var_bp;
    }

    public void setDia_var_bp(String dia_var_bp) {
        this.dia_var_bp = dia_var_bp;
    }
}
