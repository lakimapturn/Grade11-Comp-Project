package com.example.CompProject.View;

import com.example.CompProject.Service.RailwayService;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;

//@Theme(value = Lumo.class, variant = Lumo.DARK)
public class MainLayout extends AppLayout {

    VaadinSession session = VaadinSession.getCurrent();
    private RailwayService railwayService;

    public MainLayout(RailwayService railwayService) {
        this.railwayService = railwayService;
        createHeader();
    }

    private void createHeader() {
        H1 title = new H1("Railway Reservation");

//        System.out.println(new ArrayList<>().add(session.getSession().getAttributeNames().toArray()));

        title.setWidth("50%");
        title.getStyle().set("margin", "0");
        title.getStyle().set("text-align", "center");

        RouterLink homeView = new RouterLink("Home", HomeView.class);
        homeView.setHighlightCondition(HighlightConditions.sameLocation());
        homeView.setTabIndex(-1);
        RouterLink detailsView = new RouterLink("Details", DetailsView.class);
        detailsView.setHighlightCondition(HighlightConditions.sameLocation());
        detailsView.setTabIndex(-1);
        RouterLink bookingView = new RouterLink("Book Ticket", BookTicketView.class);
        bookingView.setHighlightCondition(HighlightConditions.sameLocation());
        bookingView.setTabIndex(-1);
        RouterLink ticketView = new RouterLink("Ticket", TicketView.class);
        ticketView.setHighlightCondition(HighlightConditions.sameLocation());
        ticketView.setTabIndex(-1);
        RouterLink authenticationView = new RouterLink("Logout", LogInView.class); // call function to log user out
        authenticationView.addFocusListener(event -> railwayService.logout());
        authenticationView.setHighlightCondition(HighlightConditions.sameLocation());
        authenticationView.setTabIndex(-1);

        Tabs tabs = new Tabs();
        tabs.getStyle().set("margin", "auto");
        if(railwayService.getAuthenticatedUser().isBookingStatus()) {
            tabs.add(
                    new Tab(homeView),
                    new Tab(detailsView),
                    new Tab(bookingView),
                    new Tab(ticketView),
                    new Tab(authenticationView)
            );
        } else {
            tabs.add(
                    new Tab(homeView),
                    new Tab(detailsView),
                    new Tab(bookingView),
                    new Tab(authenticationView)
            );
        }

        HorizontalLayout linksSection = new HorizontalLayout(tabs);
        linksSection.setWidth("40%");
        linksSection.getStyle().set("display", "flex");
        linksSection.getStyle().set("justify-content", "space-evenly");
        linksSection.getStyle().set("margin", "auto");

        HorizontalLayout header = new HorizontalLayout(title, linksSection);

//        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.addClassNames("py-0", "px-m");

        this.addToNavbar(title, tabs);
    }

    private Tabs getTabs() {
        Tabs tabs = new Tabs();
        tabs.getStyle().set("margin", "auto");
        tabs.add(
                createTab("Dashboard"),
                createTab("Orders"),
                createTab("Customers"),
                createTab("Products")
        );
        return tabs;
    }

    private Tab createTab(String viewName) {
        RouterLink link = new RouterLink();
        link.add(viewName);
        // Demo has no routes
        // link.setRoute(viewClass.java);
        link.setTabIndex(-1);

        return new Tab(link);
    }

    // Calls function as soon as the DOM Content loads
    @Override
    public void onAttach(AttachEvent event) {
        if(!railwayService.isAuthenticated())
            UI.getCurrent().navigate("/login");
    }
}
