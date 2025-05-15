package com.user.service.UserService.feignclient;

import com.user.service.UserService.entity.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "HOTELSERVICE")
public interface HotelService {

    @GetMapping("/hotels/hotel-id/{id}")
    Hotel getHotel(@PathVariable String id);
}
