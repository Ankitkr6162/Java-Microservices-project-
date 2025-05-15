package com.rating.service.controller;

import com.rating.service.entity.Rating;
import com.rating.service.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping("/create")
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {
        Rating createdRating = ratingService.createRating(rating);
        return new ResponseEntity<>(createdRating, HttpStatus.CREATED);
    }

    @GetMapping("all-ratings")
    public ResponseEntity<List<Rating>> getAllRatings() {
        List<Rating> allRatings = ratingService.getAllRatings();
        return new ResponseEntity<>(allRatings, HttpStatus.OK);
    }

    @GetMapping("/user-id/{userId}")
    public ResponseEntity<List<Rating>> getAllRatingByUserId(@PathVariable String userId) {
        List<Rating> allRatingsByUserId = ratingService.getAllRatingByUserId(userId);
        return new ResponseEntity<>(allRatingsByUserId, HttpStatus.OK);
    }

    @GetMapping("/hotel-id/{hotelId}")
    public ResponseEntity<List<Rating>> getAllRatingsByHotelId(@PathVariable String hotelId){
        List<Rating> allRatingsByHotelId = ratingService.getAllRatingByHotels(hotelId);
        return new ResponseEntity<>(allRatingsByHotelId, HttpStatus.OK);
    }
}
