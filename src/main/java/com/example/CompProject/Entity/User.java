package com.example.CompProject.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // generate a new value for the id for every new user
    private long passengerId;

    @GeneratedValue(strategy = GenerationType.AUTO) // generate a new value for the id for every new user
    private long bookingId;

    private int age;
    @ManyToOne
    @JoinColumn(name = "seat_number")
    private Seat seatNumber;

    private int fare;
    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;

    private char gender;

    @NotBlank(message = "Enter your fullname")
    private String fullname;

    @NotBlank(message = "Enter your password")
    private String password;
    private Date travelDate;
    private String seatingType;
    private boolean bookingStatus;

    public User() {
    }

    public User(long passengerId, long bookingId, int age, Seat seatNumber, int fare, Train train, char gender, @NotBlank(message = "Enter your fullname") String fullname, @NotBlank(message = "Enter your password") String password, Date travelDate, String seatingType, boolean bookingStatus) {
        this.passengerId = passengerId;
        this.bookingId = bookingId;
        this.age = age;
        this.seatNumber = seatNumber;
        this.fare = fare;
        this.train = train;
        this.gender = gender;
        this.fullname = fullname;
        this.password = password;
        this.travelDate = travelDate;
        this.seatingType = seatingType;
        this.bookingStatus = bookingStatus;
    }

    public long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(long passengerId) {
        this.passengerId = passengerId;
    }

    public long getBookingId() {
        return bookingId;
    }

    public void setBookingId(long bookingId) {
        this.bookingId = bookingId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Seat getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Seat seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public String getSeatingType() {
        return seatingType;
    }

    public void setSeatingType(String seatingType) {
        this.seatingType = seatingType;
    }

    public boolean isBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(boolean bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}
