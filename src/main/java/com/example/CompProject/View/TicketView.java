package com.example.CompProject.View;

import com.example.CompProject.Entity.User;
import com.example.CompProject.Service.RailwayService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import java.text.SimpleDateFormat;

@PageTitle("Ticket | Railway Reservation")
@Route(value = "/ticket", layout = MainLayout.class)
@PermitAll
public class TicketView extends VerticalLayout {

    private User user;

    @Autowired
    private RailwayService railwayService;

    public TicketView(RailwayService railwayService) {
        this.setAlignItems(Alignment.CENTER);
        this.railwayService = railwayService;
        this.user = this.railwayService.getAuthenticatedUser();

        this.setSizeFull();
        this.addTitle();
        if(this.railwayService.isAuthenticated()) this.addTicket();
        else this.addMessage();
    }

    private void addTitle() {
        H1 title = new H1("Ticket");
        title.getStyle().set("font-weight", "bold");
        this.add(title);
    }

    private void addMessage() {
        Button bookTicketButton = new Button("Book Ticket");
        bookTicketButton.addClickListener(event -> UI.getCurrent().navigate("/book"));
        this.add(
                new H3("Oops!, looks like you do not have a train booked!"),
                new H3("You can book one here: "),
                bookTicketButton
        );
    }

    private void addTicket() {
        Div ticketContainer = new Div();
        ticketContainer.getStyle().set("text-align", "center");
        ticketContainer.getStyle().set("border-radius", "1rem");
        ticketContainer.getStyle().set("background-color", "firebrick");
        ticketContainer.getStyle().set("padding", "0rem 0rem 2rem");

        H3 ticketHeader = new H3(this.user.getTrain().getName() + " - " + this.user.getTrain().getId());
        ticketHeader.getStyle().set("color", "white");
        ticketContainer.add(ticketHeader);

        Div ticket = new Div();
        ticket.getStyle().set("padding", "0rem");
        ticket.getStyle().set("background-color", "white");
        ticket.setWidthFull();

        ticket.add(
            alignHorizontalLayout(
                new HorizontalLayout(
                        new HorizontalLayout(alignText("Passenger ID: "), createReadOnlyTextField(String.valueOf(user.getPassengerId()))),
                        new HorizontalLayout(alignText("Name: "), createReadOnlyTextField(user.getFullname())),
                        new HorizontalLayout(alignText("Age: "), createReadOnlyTextField(String.valueOf(user.getAge()))),
                        new HorizontalLayout(alignText("Gender: "), createReadOnlyTextField(user.getGender()=='F'?"Female":"Male"))
                )
            )
        );
        ticket.add(
            alignHorizontalLayout(
                new HorizontalLayout(
                        new HorizontalLayout(alignText("From: "), createReadOnlyTextField(user.getTrain().getDepartureStation())),
                        new HorizontalLayout(alignText("To: "), createReadOnlyTextField(user.getTrain().getArrivalStation()))
                )
            )
        );
        ticket.add(
            alignHorizontalLayout(
                new HorizontalLayout(
                    new HorizontalLayout(alignText("Travel Date: "),
                            createReadOnlyTextField(new SimpleDateFormat("dd MMMM yyyy").format(user.getTravelDate()))
                    )
                )
            )
        );
        ticket.add(
            alignHorizontalLayout(
                new HorizontalLayout(
                    new HorizontalLayout(alignText("Seating Type: "), createReadOnlyTextField(user.getSeatingType())),
                    new HorizontalLayout(alignText("Seat Number: "), createReadOnlyTextField(String.valueOf(user.getSeatNumber().getSeat())))
                )
            )
        );
        ticketContainer.add(ticket);
        this.add(ticketContainer);
    }

    private TextField createReadOnlyTextField(String value) {
        TextField textField = new TextField();
        textField.setReadOnly(true);
        textField.setValue(value);
        textField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.getStyle().set("--vaadin-text-field-default-width", "9rem");
        return textField;
    }

    private HorizontalLayout alignHorizontalLayout(HorizontalLayout h) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        horizontalLayout.getStyle().set("margin", "1.5rem 0rem");
        h.getChildren().forEach(component -> {
            component.getElement().getStyle().set("padding", "0rem 2rem");
            horizontalLayout.add(component);
        });
        return horizontalLayout;
    }

    private H4 alignText(String text) {
        H4 h4 = new H4(text);
        h4.getStyle().set("margin", "auto");
        h4.getStyle().set("color", "black");
        return h4;
    }
}
