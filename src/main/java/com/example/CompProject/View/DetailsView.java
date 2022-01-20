package com.example.CompProject.View;

import com.example.CompProject.Entity.Train;
import com.example.CompProject.Entity.User;
import com.example.CompProject.Service.RailwayService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Details | Railway Reservation")
@Route(value = "/details", layout = MainLayout.class)
@PermitAll
public class DetailsView extends VerticalLayout {
    private Grid<User> grid = new Grid<>(User.class);
    private ComboBox<Train> trainSelect = new ComboBox<>("Select Train");
    private H1 title;
    private H4 seats = new H4();


    @Autowired
    private RailwayService railwayService;

    public DetailsView(RailwayService railwayService) {
        this.railwayService = railwayService;
        this.setAlignItems(Alignment.CENTER);

        this.setSizeFull();
        this.addTitle();
        this.addTrainComboBox();
        this.addPassengerDetailsGrid();
        this.updateSearch();
        this.addSeatAvailabilityStatus();
//        this.addToolbar();
    }

    private void addTrainComboBox() {
        trainSelect.getStyle().set("--vaadin-text-field-default-width", "18rem");
        trainSelect.setItems(railwayService.getAllTrainInfo());
        trainSelect.setItemLabelGenerator(train -> train.getName() + " - " + new SimpleDateFormat("dd MMMM yyyy").format(train.getJourneyDate()));
        trainSelect.addValueChangeListener(e -> updateSearch());
        this.add(this.trainSelect);
    }

    public void updateSearch() {
        if (trainSelect.getValue() != null) {
            title.setText(
                    trainSelect.getValue().getName() +
                            " : " +
                            new SimpleDateFormat("dd MMMM yyyy").format(trainSelect.getValue().getJourneyDate()));
            this.updateSeatAvailabilityStatus();
        }
        grid.setItems(railwayService.getUserInfo(trainSelect.getValue()));
    }

    private void addTitle() {
        HorizontalLayout titleLayout = new HorizontalLayout();
        titleLayout.setWidthFull();
        titleLayout.setSpacing(false);
        title = new H1("Details");
        title.getStyle().set("fontWeight", "bold");
        titleLayout.add(title);
        this.add(title);
    }

    private void addPassengerDetailsGrid() {
        grid.addClassName("passenger-grid");
//        grid.setSizeFull();
        grid.setHeight("5rem");
        grid.setColumns("passengerId", "fullname", "age", "gender");
        grid.addColumn(seat -> seat.getSeatNumber() == null? null : seat.getSeatNumber().getSeat()).setHeader("Seat Number");
        grid.addColumns("seatingType", "fare", "bookingStatus");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        this.updateSearch();
        this.add(grid);
    }

    private void addSeatAvailabilityStatus() {
        this.add(new H3("Available seats: "), seats);
    }

    private void updateSeatAvailabilityStatus() {
        if (trainSelect.getValue().getAvailableSeats().isEmpty()) seats.setText("No Seats Available!");
        else {
            List<Integer> seatList = new ArrayList();
            trainSelect.getValue().getAvailableSeats().forEach(seat -> seatList.add(seat.getSeat()));
            seats.setText(seatList.toString());
        }
    }
}

