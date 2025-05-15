package com.user.service.UserService.service;

import com.user.service.UserService.entity.Hotel;
import com.user.service.UserService.entity.Rating;
import com.user.service.UserService.entity.User;
import com.user.service.UserService.exceptions.ResourceNotFoundException;
import com.user.service.UserService.feignclient.HotelService;
import com.user.service.UserService.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        List<User> allUsers = userRepository.findAll();
        return allUsers;
    }


    //Here we are calling the rating service and hotel service  from the user service to fetch the rating and its hotel by given user id
    // We are also using @LoadBalanced at the bean of restTemplate so we are removing the dependency of IP address and port.
     // Hence we are using the name of the service registered at the service registry.
    @Override
    public User getUserByUserId(String userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given user Id :" + userId));
        Rating[] ratingOfUser = restTemplate.getForObject("http://RATINGSERVICE/rating/user-id/" + user.getUserId(), Rating[].class);

        List<Rating> ratings = Arrays.stream(ratingOfUser).toList();
        List<Rating> ratingList = ratings.stream().map(rating -> {
            // api call to hotel service to get the hotel
            // set the hotel to rating
            // return the rating
            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTELSERVICE/hotels/hotel-id/" + rating.getHotelId(), Hotel.class);
            Hotel hotel = forEntity.getBody();
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());
        user.setRatings(ratingList);
        return user;
    }

    // Using Feign Client to call the HotelService 
   /** @Override
    public User getUserByUserId(String userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given user Id :" + userId));
        Rating[] ratingOfUser = restTemplate.getForObject("http://RATINGSERVICE/rating/user-id/" + user.getUserId(), Rating[].class);

        List<Rating> ratings = Arrays.stream(ratingOfUser).toList();
        List<Rating> ratingList = ratings.stream().map(rating -> {
            // api call to hotel service to get the hotel
            // set the hotel to rating
            // return the rating
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());
        user.setRatings(ratingList);
        return user;
    }  **/
}
