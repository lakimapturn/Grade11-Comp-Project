package com.example.CompProject.View;

import com.example.CompProject.Service.RailwayService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Login | Railway Reservation")
@Route("login")
public class LogInView extends VerticalLayout {

    @Autowired
    private RailwayService railwayService;

    public LogInView(RailwayService railwayService) {
        TextField fullName = new TextField("Full Name");
        PasswordField password = new PasswordField("Password");

        H3 invalidMessage = new H3("Invalid Credentials!");
        invalidMessage.getStyle().set("color", "red");

        this.addClassName("login-view");
        this.setSizeFull();
        this.setAlignItems(Alignment.CENTER);
        this.setJustifyContentMode(JustifyContentMode.CENTER);


        this.add(new H1("Railway Reservation | Login"), fullName, password, new Button("Login", event -> {
            try {
                railwayService.authenticate(fullName.getValue().trim(), password.getValue());
                UI.getCurrent().navigate("");
            } catch (Exception e){
                this.add(invalidMessage);
            }

        }));
        this.railwayService = railwayService;
//        this.addUserForm();
    }

//    private void addUserForm() {
//        form = new UserForm(railwayService.getAllTrainInfo(), this.railwayService);
//        form.setWidth("25rem");
//        this.add(form);
//    }

}
