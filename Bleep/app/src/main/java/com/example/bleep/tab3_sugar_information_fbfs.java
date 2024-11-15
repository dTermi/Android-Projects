package com.example.bleep;

public class tab3_sugar_information_fbfs
{
    private String fast , after_fast , var_fast , var_after_fast;

    public tab3_sugar_information_fbfs(String f, String af, String vf, String vaf)
    {
        this.fast = f;
        this.after_fast = af;
        this.var_fast = vf;
        this.var_after_fast = vaf;
    }

    public String getFast() {
        return fast;
    }

    public void setFast(String fast) {
        this.fast = fast;
    }

    public String getAfter_fast() {
        return after_fast;
    }

    public void setAfter_fast(String after_fast) {
        this.after_fast = after_fast;
    }

    public String getVar_fast() {
        return var_fast;
    }

    public void setVar_fast(String var_fast) {
        this.var_fast = var_fast;
    }

    public String getVar_after_fast() {
        return var_after_fast;
    }

    public void setVar_after_fast(String var_after_fast) {
        this.var_after_fast = var_after_fast;
    }
}
