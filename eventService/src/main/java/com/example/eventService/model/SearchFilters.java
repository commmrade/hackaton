package com.example.eventService.model;




//data.country = country.value.trim();
//data.region = region.value;
//data.sportType = sportType.value;
//data.startDate = new Date(startDate.value).toISOString();
//data.endDate = new Date(endDate.value).toISOString();
//date.ageAndSex = ageAndSex.value;
//date.compType = compType.value;


import java.util.Date;

public class SearchFilters {
    private String country;
    private String region;
    private String sportType;
    private Date startDate;
    private Date endDate;
    private String ageAndSex;
    private String compType;

    public SearchFilters() {

    }

    public SearchFilters(String country, String region, String sportType, Date startDate, Date endDate, String ageAndSex, String compType) {
        this.country = country;
        this.region = region;
        this.sportType = sportType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ageAndSex = ageAndSex;
        this.compType = compType;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSportType() {
        return sportType;
    }

    public void setSportType(String sportType) {
        this.sportType = sportType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getAgeAndSex() {
        return ageAndSex;
    }

    public void setAgeAndSex(String ageAndSex) {
        this.ageAndSex = ageAndSex;
    }

    public String getCompType() {
        return compType;
    }

    public void setCompType(String compType) {
        this.compType = compType;
    }
}
