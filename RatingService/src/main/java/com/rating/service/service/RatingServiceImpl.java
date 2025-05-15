package com.rating.service.service;

import com.rating.service.entity.Rating;
import com.rating.service.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public Rating createRating(Rating rating) {
        String randomId = UUID.randomUUID().toString();
        rating.setRatingId(randomId);
        Rating savedRating = ratingRepository.save(rating);
        return savedRating;
    }

    @Override
    public List<Rating> getAllRatings() {
        List<Rating> allRatings = ratingRepository.findAll();
        return allRatings;
    }

    @Override
    public List<Rating> getAllRatingByUserId(String userId) {
        List<Rating> ratingByUserId = ratingRepository.findByUserId(userId);
        return ratingByUserId;
    }

    @Override
    public List<Rating> getAllRatingByHotels(String hotelId) {
        List<Rating> ratingByHotelid = ratingRepository.findByHotelId(hotelId);
        return ratingByHotelid;
    }
}
