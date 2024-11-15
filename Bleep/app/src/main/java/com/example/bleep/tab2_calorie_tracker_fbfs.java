package com.example.bleep;

public class tab2_calorie_tracker_fbfs
{
    private String cal_tot , ideal_cal_tot , date;

    public tab2_calorie_tracker_fbfs(String t, String it, String d)
    {
        this.cal_tot = t;
        this.ideal_cal_tot = it;
        this.date = d;
    }

    public String getCal_tot() {
        return cal_tot;
    }

    public void setCal_tot(String cal_tot) {
        this.cal_tot = cal_tot;
    }

    public String getIdeal_cal_tot() {
        return ideal_cal_tot;
    }

    public void setIdeal_cal_tot(String ideal_cal_tot) {
        this.ideal_cal_tot = ideal_cal_tot;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
