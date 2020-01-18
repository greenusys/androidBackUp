package com.icosom.social.model;

/**
 * Created by admin on 30-Mar-18.
 */

public class TalentModel {
     String id;
    String comp_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComp_name() {
        return comp_name;
    }

    public void setComp_name(String comp_name) {
        this.comp_name = comp_name;
    }

    public TalentModel(String id, String comp_name) {

        this.id = id;
        this.comp_name = comp_name;
    }
}
