package org.example.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "events")
public class Event implements Cloneable {

    @Id
    private long id;

    @Column(name = "category_id")
    private long categoryId;

    private String type;
    private String sexAndAge;


    private Date startDate;

    private Date endDate;

    private String country;
    private String region;
    private int participants;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSexAndAge() {
        return sexAndAge;
    }

    public void setSexAndAge(String sexAndAge) {
        this.sexAndAge = sexAndAge;
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

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", type='" + type + '\'' +
                ", sexAndAge='" + sexAndAge + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", country='" + country + '\'' +
                ", region='" + region + '\'' +
                ", participants=" + participants +
                '}';
    }

    @Override
    public Event clone() throws CloneNotSupportedException {
        return (Event) super.clone();
    }
}
