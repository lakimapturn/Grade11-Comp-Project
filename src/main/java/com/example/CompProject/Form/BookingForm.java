package com.example.CompProject.Form;

import com.example.CompProject.Entity.Train;
import com.example.CompProject.Service.RailwayService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.HashSet;

public class BookingForm extends VerticalLayout {
    private ComboBox<String> departureStation = new ComboBox<>("Departure Station");
    private ComboBox<String> arrivalStation = new ComboBox<>("Arrival Station");
    private ComboBox<Date> date = new ComboBox<>("Travel Date");
    private ComboBox<String> trainClass = new ComboBox<>("Train Class");
    private Train availableTrain = new Train();
    private H2 message = new H2("Please select departure and arrival station.");
    private Button bookTicket = new Button("Book Ticket");
    private boolean availableSeat = true;

    @Autowired
    private RailwayService railwayService;

    public BookingForm(RailwayService railwayService) {
        this.railwayService = railwayService;

        this.setAlignItems(Alignment.CENTER);

        this.add(new H1("Book a Ticket"));
        this.addStationSelect();
        this.add(message);
    }

    private void addStationSelect() {
        HashSet<String> departure = new HashSet<>(), arrival = new HashSet<>();
        railwayService.getAllTrainInfo().forEach(train -> { // adding values to the arrival and departure lists
            departure.add(train.getDepartureStation());
            arrival.add(train.getArrivalStation());
        });

        departureStation.setItems(departure);
        arrivalStation.setItems(arrival);

        departureStation.addValueChangeListener(e -> getSelectedStations());
        arrivalStation.addValueChangeListener(e -> getSelectedStations());

        this.add(new HorizontalLayout(departureStation, arrivalStation));
    }

    private void getSelectedStations() {
        if(departureStation.getValue() != null && arrivalStation.getValue() != null) {
            if(railwayService.getTrainInfo(departureStation.getValue(), arrivalStation.getValue()).size() > 0) {
                availableTrain = railwayService.getTrainInfo(departureStation.getValue(), arrivalStation.getValue()).stream().findFirst().get();
                message.setText("Available Train: " + availableTrain.getName());
                this.addDateSelect(); // add Date Select Field only if there are available trains
            }
            else {
                availableTrain = null; // setting the available trains to null
                message.setText("No available trains!");
                date.setItems(); //empty the date combo box as there are no available trains
            }

        }
    }

    private void addDateSelect() {
        HashSet<Date> availableDates = new HashSet<>();
        this.railwayService.getTrainInfo(availableTrain.getName()).forEach(train -> {
            availableDates.add(train.getJourneyDate());
        });

        date.setItems(availableDates);

        this.add(date);

        this.addClassSelect();
    }

    private void addClassSelect() {
        trainClass.setItems("First Class - ₹" + availableTrain.getFirstClassFare(), "Second Class - ₹" + availableTrain.getSecondClassFare());
        trainClass.addValueChangeListener(event -> {
            Train train = railwayService.getTrainInfo(availableTrain.getName(), date.getValue());

            if(trainClass.getValue().startsWith("First") && train.getAvailableSeats().get(0).getSeat() >= train.getTotalSeats()/3) {
                this.setSeatAvailability(false);
                Notification msg = new Notification("There are no first class seats available!");
                this.add(msg);
            }
            else if(trainClass.getValue().startsWith("Second") &&
                    train.getAvailableSeats().get(train.getAvailableSeats().size()-1).getSeat() <= train.getTotalSeats()/3) {
                this.setSeatAvailability(false);
                Notification msg = new Notification("There are no second class seats available!");
                this.add(msg);
            }
            else
                this.addBookTicketButton();
        });
        this.add(trainClass);
    }

    private void addBookTicketButton() {
        bookTicket.addClickListener(event -> {
            railwayService.bookTicket(
                    railwayService.getTrainInfo(availableTrain.getName(), date.getValue()),
                    trainClass.getValue());
        });
        this.add(bookTicket);
    }

    private void updateUserInfo() {
        Train selectedTrain = railwayService.getTrainInfo(availableTrain.getName(), date.getValue());
    }

    private void setSeatAvailability(boolean availableSeat) {
        this.availableSeat = availableSeat;
        bookTicket.setEnabled(this.availableSeat);
    }
}
