package com.example.CompProject.View;

import com.example.CompProject.Form.BookingForm;
import com.example.CompProject.Service.RailwayService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;

@PageTitle("Book A Ticket | Railway Reservation")
@Route(value = "/book", layout = MainLayout.class)
@PermitAll
public class BookTicketView extends VerticalLayout {

    @Autowired
    private RailwayService railwayService;
    private BookingForm form;

    public BookTicketView (RailwayService railwayService) {
        this.railwayService = railwayService;

        // setting style for the page
        this.setSizeFull();
        this.setAlignItems(Alignment.CENTER);


        if(railwayService.getAuthenticatedUser().isBookingStatus()) {
            VerticalLayout msgLayout = new VerticalLayout(
                    new H3("Sorry! You already have a train booked!"),
                    new H3("Would you like to cancel your previous booking?"));
            msgLayout.setAlignItems(Alignment.CENTER);

            Button cancel = new Button("Cancel Booking");
            cancel.addThemeVariants(ButtonVariant.LUMO_ERROR);
            cancel.addClickListener(event -> railwayService.cancelBooking());

            this.add(msgLayout, cancel);
        }
        else {
            this.addBookingForm();
        }
    }

    private void addBookingForm() {
        form = new BookingForm(railwayService);
        this.add(form);
    }

}
