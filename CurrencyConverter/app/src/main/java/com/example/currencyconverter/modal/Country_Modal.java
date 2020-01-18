package com.example.currencyconverter.modal;

public class Country_Modal {
    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String  getCountry_image() {
        return country_image;
    }

    public void setCountry_image(String  country_image) {
        this.country_image = country_image;
    }

    String country_name;
    String  country_image;
    String country_code;

    public String getCountry_code_2lett() {
        return country_code_2lett;
    }

    public void setCountry_code_2lett(String country_code_2lett) {
        this.country_code_2lett = country_code_2lett;
    }

    String country_code_2lett;


    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public Country_Modal(String country_name, String country_image,String country_code_3lett,String country_code_2lett) {
        this.country_name = country_name;
        this.country_image = country_image;
        this.country_code = country_code_3lett;
        this.country_code_2lett = country_code_2lett;
    }
}
