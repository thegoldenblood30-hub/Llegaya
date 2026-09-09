package com.llegaya.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class LocationUpdate {
    private Long orderId;
    private double latitude;
    private double longitude;
    private double speed;
}