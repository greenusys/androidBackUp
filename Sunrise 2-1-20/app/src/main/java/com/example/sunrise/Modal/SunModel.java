package com.example.sunrise.Modal;

public class SunModel {


 String sunrise;
 String sunset;
 String city;
 String country;
 String lat;
 String lon;

 public String getCountry() {
  return country;
 }

 public void setCountry(String country) {
  this.country = country;
 }



 public String getLat() {
  return lat;
 }

 public void setLat(String lat) {
  this.lat = lat;
 }

 public String getLon() {
  return lon;
 }

 public void setLon(String lon) {
  this.lon = lon;
 }

 public String getUtcTime() {
  return utcTime;
 }

 public void setUtcTime(String utcTime) {
  this.utcTime = utcTime;
 }

 String utcTime;


  public String getSunrise() {
   return sunrise;
  }

  public void setSunrise(String sunrise) {
   this.sunrise = sunrise;
  }

  public String getSunset() {
   return sunset;
  }

  public void setSunset(String sunset) {
   this.sunset = sunset;
  }

  public String getCity() {
   return city;
  }

  public void setCity(String city) {
   this.city = city;
  }




}
