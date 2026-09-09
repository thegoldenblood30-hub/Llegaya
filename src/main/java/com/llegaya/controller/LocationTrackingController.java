package com.llegaya.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class LocationTrackingController {

    @MessageMapping("/tracking/{orderId}")
    @SendTo("/topic/order/{orderId}")
    public LocationUpdate sendLocationUpdate(@DestinationVariable Long orderId, LocationUpdate location) {
        return location;
    }
}