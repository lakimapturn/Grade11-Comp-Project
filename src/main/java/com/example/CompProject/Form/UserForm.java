package com.example.CompProject.Form;

import com.example.CompProject.Entity.Train;
import com.example.CompProject.Service.RailwayService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;

public class UserForm extends FormLayout {
    TextField fullname = new TextField("Full Name");
    PasswordField password = new PasswordField("Password");
    NumberField age = new NumberField("Age");
    ComboBox<Train> trainSelect = new ComboBox<>("Train");
    Select<String> genderSelect = new Select<>("Male", "Female", "Other");

    Button save = new Button("Book Ticket");
    Button cancel = new Button("Cancel Ticket");

    private RailwayService railwayService;

    public UserForm(List<Train> trains, RailwayService railwayService) {
        this.railwayService = railwayService;

        genderSelect.setLabel("Gender");

        trainSelect.setItems(trains);
        trainSelect.setItemLabelGenerator(train -> train.getName());
        this.add(
                fullname,
                password,
                age,
                trainSelect,
                genderSelect
        );
        this.addButtons();
    }

    public void addButtons() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_ERROR);

        save.addClickListener(event -> bookTicket());

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(save, cancel);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        this.add(buttonLayout);
    }

    private void bookTicket() {

    }

//    private boolean validate() {
//        for(User user : railwayService.getAllUserInfo()) {
//            if(user.getFullname().equalsIgnoreCase(this.fullname.getValue())
//                            && user.getPassword().equals(this.password.getValue()) )
//            {
//                System.out.println("true!");
//                return true;
//            }
//        }
//        return false;
//    }
}
