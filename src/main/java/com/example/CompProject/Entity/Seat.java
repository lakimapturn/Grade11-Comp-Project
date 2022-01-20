package com.example.CompProject.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    private int seat;

    public Seat(int id, int seat) {
        this.id = id;
        this.seat = seat;
    }

    public Seat() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }
}
