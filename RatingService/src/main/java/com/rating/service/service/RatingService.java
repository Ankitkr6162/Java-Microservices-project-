package com.rating.service.service;

import com.rating.service.entity.Rating;

import java.util.List;

public interface RatingService {

    Rating createRating(Rating rating);

    List<Rating> getAllRatings();

    List<Rating> getAllRatingByUserId(String userId);

    List<Rating> getAllRatingByHotels(String hotelId);
}
