package com.example.CompProject.View;

import com.example.CompProject.Entity.User;
import com.example.CompProject.Service.RailwayService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import java.text.SimpleDateFormat;

@PageTitle("Home | Railway Reservation")
@Route(value = "", layout = MainLayout.class)
@PermitAll
public class HomeView extends VerticalLayout {

    private User user;

    @Autowired
    private RailwayService railwayService;

    public HomeView(RailwayService railwayService) {
        this.setAlignItems(Alignment.CENTER);
        this.railwayService = railwayService;
        this.user = this.railwayService.getAuthenticatedUser();

        this.setSizeFull();
        this.setUpHomePage();
    }

    private void setUpHomePage() {
//        H2 welcomeMessage = new H2("Welcome Back " +
//                ((railwayService.getAuthenticatedUser().getFullname() == null) ?
//                "<User>" :
//                railwayService.getAuthenticatedUser().getFullname()) + "!");
        H2 welcomeMessage = new H2("Welcome Back " + user.getFullname() + "!");
        this.add(welcomeMessage);

        Div bookingStatusColor = new Div();
        bookingStatusColor.getStyle().set("height", "1.75rem");
        bookingStatusColor.getStyle().set("width", "2.75rem");
        if (user.isBookingStatus() && user.getFullname() != null) {
            bookingStatusColor.getStyle().set("background-color", "green");
        }
        else
            bookingStatusColor.getStyle().set("background-color", "red");

        H3 bookingStatusMessage = new H3("Booking Status: ");
        bookingStatusMessage.getStyle().set("margin", "0");
        this.add(new HorizontalLayout(bookingStatusMessage, bookingStatusColor));

        if(user.isBookingStatus() && user.getFullname() != null) this.addDetails();

        //if (bookingStatus == false) { Button redirect = new Button("Book a Ticket"); redirect.onClick(event -> return 'redirect::Ticket') }
        //else showDetails();
    }

    private void addDetails() {
        VerticalLayout gridContainer = new VerticalLayout();
        gridContainer.setHeight("12.25rem");

        Grid<User> detailsGrid = new Grid<>(User.class, false);
        detailsGrid.setWidth("75%");
        detailsGrid.getStyle().set("align-self", "center");
        detailsGrid.getStyle().set("align-items", "center");
        detailsGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);

        detailsGrid.setItems(this.user);
        detailsGrid.addColumn(new ComponentRenderer<>(user -> {
            H4 name = new H4(user.getFullname());
            name.getStyle().set("font-weight", "200");
            name.setClassName("--lumo-font-size-m");
            H5 age = new H5(String.valueOf(user.getAge()));
            age.setClassName("--lumo-font-size-s");
            age.getStyle().set("margin-top", "0.2rem");
            age.getStyle().set("color", "lightslategrey");
            age.getStyle().set("font-weight", "100");
            H5 gender = new H5(user.getGender()=='F' ? "Female" : "Male");
            gender.setClassName("--lumo-font-size-s");
            gender.getStyle().set("margin-top", "0.2rem");
            gender.getStyle().set("color", "lightslategrey");
            gender.getStyle().set("font-weight", "100");

            HorizontalLayout footerDetails = new HorizontalLayout();
            footerDetails.add(age, gender);
            footerDetails.getStyle().set("margin", "0");
            return new VerticalLayout(name, footerDetails);
        })).setHeader("Passenger Details");
        detailsGrid.addColumn(user -> user.getTrain().getName()).setHeader("Train");
        detailsGrid.addColumn(user -> user.getTrain().getDepartureStation()).setHeader("Departure Station");
        detailsGrid.addColumn(user -> user.getTrain().getArrivalStation()).setHeader("Arrival Station");
        detailsGrid.addColumn(user -> new SimpleDateFormat("dd MMMM yyyy").format(user.getTrain().getJourneyDate())).setHeader("Journey Date");
        detailsGrid.addColumn(user -> user.getSeatNumber().getSeat()).setHeader("Seat Number");
        detailsGrid.addColumn("seatingType");

        gridContainer.add(detailsGrid);
        this.add(gridContainer);
    }
}
