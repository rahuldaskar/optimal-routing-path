package com.rda.optimal.route.model;

import lombok.Data;

@Data
public class Route {
    private final WareHouse source;
    private final WareHouse destination;
    private final int transitTime;
}
