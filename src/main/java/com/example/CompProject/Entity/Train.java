package com.example.CompProject.Entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // generate a new value for the id for every new user
    private long id;
    private String name;
    private int totalSeats;

    @OneToMany(fetch= FetchType.EAGER)
    @JoinColumn(name = "available_seats")
    private List<Seat> availableSeats;

    private String departureStation, arrivalStation;
    private Date journeyDate;
    private int firstClassFare, secondClassFare;

//    @ManyToOne
//    @JoinColumn(name = "passenger_id")
//    private User passengers;


    public Train() {
    }

    public Train(long id, String name, int totalSeats, List<Seat> availableSeats, String departureStation, String arrivalStation, Date journeyDate, int firstClassFare, int secondClassFare) {
        this.id = id;
        this.name = name;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.journeyDate = journeyDate;
        this.firstClassFare = firstClassFare;
        this.secondClassFare = secondClassFare;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(List<Seat> availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public Date getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(Date journeyDate) {
        this.journeyDate = journeyDate;
    }

    public int getFirstClassFare() {
        return firstClassFare;
    }

    public void setFirstClassFare(int firstClassFare) {
        this.firstClassFare = firstClassFare;
    }

    public int getSecondClassFare() {
        return secondClassFare;
    }

    public void setSecondClassFare(int secondClassFare) {
        this.secondClassFare = secondClassFare;
    }
}