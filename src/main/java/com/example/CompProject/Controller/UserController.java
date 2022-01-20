package com.example.CompProject.Controller;

import com.example.CompProject.Entity.Seat;
import com.example.CompProject.Service.RailwayService;
import com.example.CompProject.Entity.Train;
import com.example.CompProject.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class UserController {

    @Autowired
    private RailwayService railwayService;

    /** Train Mapping */
    @GetMapping("/train")
    public ResponseEntity<List<Train>> getTrainInformation() {
        try {
            return new ResponseEntity(railwayService.getAllTrainInfo(), HttpStatus.OK);
        }
        catch (Error error) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/train")
    public ResponseEntity<List<Train>> postTrainInformation(@RequestBody Train train) {
        try {
            railwayService.addInfo(train);
            return new ResponseEntity(railwayService.getAllTrainInfo(), HttpStatus.CREATED);
        }
        catch(Error error) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /** User Mapping */
    @GetMapping("/user")
    public ResponseEntity<List<User>> getUserInformation() {
        try {
            return new ResponseEntity(railwayService.getAllUserInfo(), HttpStatus.OK);
        }
        catch (Error error) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/user")
    public ResponseEntity<List<User>> postUserInformation(@RequestBody User user) {
        try {
            railwayService.addInfo(user);
            return new ResponseEntity(railwayService.getAllUserInfo(), HttpStatus.CREATED);
        }
        catch(Error error) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/user-info/{id}")
    public ResponseEntity<List<User>> updateInformation(@RequestBody User user, @PathVariable long id) {
        try {
            user.setPassengerId(id);
            railwayService.updateUserInfo(user, id);
            return new ResponseEntity(railwayService.getAllUserInfo(), HttpStatus.ACCEPTED);
        }
        catch (Error error) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /** Seat Mapping */
    @GetMapping("/seat")
    public ResponseEntity<List<Seat>> getSeatInformation() {
        try {
            return new ResponseEntity(railwayService.getAllSeatInfo(), HttpStatus.OK);
        }
        catch (Error error) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/seat")
    public ResponseEntity<List<Seat>> postSeatInformation(@RequestBody Seat seat) {
        try {
            railwayService.addInfo(seat);
            return new ResponseEntity(railwayService.getAllSeatInfo(), HttpStatus.CREATED);
        }
        catch(Error error) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
