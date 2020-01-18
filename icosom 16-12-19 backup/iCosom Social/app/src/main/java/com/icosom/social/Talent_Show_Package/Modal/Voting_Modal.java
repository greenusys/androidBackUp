package com.icosom.social.Talent_Show_Package.Modal;

public class Voting_Modal
{


    public Boolean getGood_Rank() {
        return Good_Rank;
    }

    public void setGood_Rank(Boolean good_Rank) {
        Good_Rank = good_Rank;
    }

    public Boolean getV_Good_Rank() {
        return V_Good_Rank;
    }

    public void setV_Good_Rank(Boolean v_Good_Rank) {
        V_Good_Rank = v_Good_Rank;
    }

    public Boolean getBad_Ran() {
        return Bad_Rank;
    }

    public void setBad_Rank(Boolean bad_Ran) {
        Bad_Rank = bad_Ran;
    }

    public Boolean getV_Bad_Rank() {
        return V_Bad_Rank;
    }

    public void setV_Bad_Rank(Boolean v_Bad_Rank) {
        V_Bad_Rank = v_Bad_Rank;
    }

    Boolean  Good_Rank=false;
    Boolean V_Good_Rank=false;
    Boolean Bad_Rank=false;
    Boolean V_Bad_Rank=false;

    public String getTootal_Good() {
        return Tootal_Good;
    }

    public void setTootal_Good(String tootal_Good) {
        Tootal_Good = tootal_Good;
    }

    public String getTootal_V_Good() {
        return Tootal_V_Good;
    }

    public void setTootal_V_Good(String tootal_V_Good) {
        Tootal_V_Good = tootal_V_Good;
    }

    public String getTotal_Bad() {
        return Total_Bad;
    }

    public void setTotal_Bad(String total_Bad) {
        Total_Bad = total_Bad;
    }

    public String getTotal_V_Bad() {
        return Total_V_Bad;
    }

    public void setTotal_V_Bad(String total_V_Bad) {
        Total_V_Bad = total_V_Bad;
    }

    String  Tootal_Good;
    String Tootal_V_Good;
    String Total_Bad;
    String Total_V_Bad;




}