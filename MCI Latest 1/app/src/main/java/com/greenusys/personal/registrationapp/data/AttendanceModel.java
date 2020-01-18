package com.greenusys.personal.registrationapp.data;



public class AttendanceModel {

    private String atrollno;
    private boolean checked;

    public AttendanceModel(String atrollno){
        this.atrollno = atrollno;
    }

    public String getAtrollno() {
        return atrollno;
    }

    public void setAtrollno(String atrollno) {
        this.atrollno = atrollno;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
