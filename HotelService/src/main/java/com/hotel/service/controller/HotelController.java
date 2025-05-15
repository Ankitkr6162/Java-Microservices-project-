package com.hotel.service.controller;

import com.hotel.service.entity.Hotel;
import com.hotel.service.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping("/create")
    public ResponseEntity<Hotel> createHotels(@RequestBody Hotel hotel) {
        Hotel createdHotel = this.hotelService.createHotel(hotel);
        return new ResponseEntity<Hotel>(createdHotel, HttpStatus.CREATED);
    }

    @GetMapping("/all-hotels")
    public ResponseEntity<List<Hotel>> getAllHotels() {
        List<Hotel> allHotels = this.hotelService.getAllHotels();
        return new ResponseEntity<>(allHotels, HttpStatus.OK);
    }

    @GetMapping("/hotel-id/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable String id) {
        Hotel hotel = this.hotelService.getHotelById(id);
        return ResponseEntity.ok(hotel);
    }
}
