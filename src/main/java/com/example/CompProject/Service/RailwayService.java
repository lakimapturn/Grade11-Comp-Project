package com.example.CompProject.Service;

import com.example.CompProject.Entity.Seat;
import com.example.CompProject.Entity.Train;
import com.example.CompProject.Entity.User;
import com.example.CompProject.Repository.SeatRepository;
import com.example.CompProject.Repository.TrainRespository;
import com.example.CompProject.Repository.UserRepository;
import com.vaadin.flow.component.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

@Service
public class RailwayService {

    public class AuthException extends Exception {
    }

    private boolean authenticated;
    private User authenticatedUser;

    @Autowired
    private TrainRespository trainRespository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SeatRepository seatRepository;

    public List<Train> getAllTrainInfo() {
        return trainRespository.findAll();
    }

    public HashSet<Train> getTrainInfo(String name) {
        HashSet<Train> list = new HashSet<>();
        this.trainRespository.findAll().forEach(train -> {
            if(name != null && name.equalsIgnoreCase(train.getName())) list.add(train);
        });

        return list;
    }

    public Train getTrainInfo(String name, Date date) {
        for(Train train : this.getTrainInfo(name))
            if(date.equals(train.getJourneyDate())) return train;

        return null;
    }

    public HashSet<Train> getTrainInfo(String departureStation, String arrivalStation) {
        HashSet<Train> list = new HashSet<>();
        this.trainRespository.findAll().forEach(train -> {
                if(departureStation.equalsIgnoreCase(train.getDepartureStation()) &&
                arrivalStation.equalsIgnoreCase(train.getArrivalStation())) list.add(train);
        });

        return list;
    }

    public void updateTrainInfo(Train train, long id) {
        trainRespository.save(train);
    }

    public void addInfo(Train train) {
        trainRespository.save(train);
    }

    public List<User> getAllUserInfo() {
        return userRepository.findAll();
    }

    public void updateUserInfo(User user, long id) {
        userRepository.save(user);
    }

    public List<User> getUserInfo(Train train) { // returns the user information for the details table
        if(train == null) return userRepository.findAll();
        else return filterUserInfo(train);
    }

    public List<User> filterUserInfo(Train train) { // returns the list of users that have booked a particular train
        List<User> list = new ArrayList();
        for(User user : this.getAllUserInfo())
            if(user.isBookingStatus() && user.getTrain().getId() == train.getId())
                list.add(user);

        return list;
    }

    public void addInfo(User user) {
        userRepository.save(user);
    }

    public void authenticate(String fullname, String password) throws AuthException {
        User user = userRepository.getByFullname(fullname);
        if(user != null && user.getPassword().equals(password)) {
            authenticated = true;
            authenticatedUser = user;
            UI.getCurrent().navigate("");
        }
        else throw new AuthException();
    }

    public User getAuthenticatedUser() {
        return this.authenticatedUser;
    }

    public boolean isAuthenticated() {
        return this.authenticated;
    }

    public List<Seat> getAllSeatInfo() {
        return this.seatRepository.findAll();
    }

    public void addInfo(Seat seat) {
        seatRepository.save(seat);
    }

    public void logout() {
        this.authenticated = false;
        this.authenticatedUser = null;
    }

    public void cancelBooking() {
        User user = this.getAuthenticatedUser();
        Train train = user.getTrain();

        List<Seat> seatList= train.getAvailableSeats();
        seatList.add(user.getSeatNumber());
        seatList.sort(Comparator.comparingLong(Seat::getSeat)); //sorting the arrayList
        train.setAvailableSeats(seatList);

        user.setTrain(null);
        user.setBookingStatus(false);
        user.setFare(0);
        user.setSeatNumber(null);
        user.setTravelDate(null);
        user.setSeatingType("");

        trainRespository.save(train); // does not work by calling the update method for some reason
        this.updateUserInfo(user, user.getPassengerId());
    }

    public void bookTicket(Train selectedTrain, String selectedClass) {
        User user = this.getAuthenticatedUser();
        user.setTrain(selectedTrain);
        user.setBookingStatus(true);
        if(selectedClass.startsWith("First") && selectedTrain.getAvailableSeats().get(0).getSeat() <= selectedTrain.getTotalSeats()/3) {
            user.setFare(selectedTrain.getFirstClassFare());
            user.setSeatNumber(selectedTrain.getAvailableSeats().get(0));
            selectedTrain.getAvailableSeats().remove(0);
        }
        else {
            user.setFare(selectedTrain.getSecondClassFare());
            user.setSeatNumber(selectedTrain.getAvailableSeats().get(selectedTrain.getAvailableSeats().size()-1));
            selectedTrain.getAvailableSeats().remove(selectedTrain.getAvailableSeats().size()-1);
        }
        selectedTrain.getAvailableSeats().sort(Comparator.comparingLong(Seat::getSeat)); // sorting the arrayList
        user.setTravelDate(selectedTrain.getJourneyDate());
        user.setSeatingType(
                selectedClass.startsWith("First")? "First Class" : "Second Class"
        );
        this.updateTrainInfo(selectedTrain, selectedTrain.getId());
        this.updateUserInfo(user, user.getPassengerId());
    }

//    public List<Train> getAllSortedUserInfo() {
//        return railwayRepository.findAll();
//    }
}
