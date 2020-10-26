package com.rda.optimal.route.model;

import lombok.Data;

import java.util.List;

@Data
public class DeliveryNetworkGraph {
    private final List<WareHouse> wareHouses;
    private final List<Route> routes;
    private final List<Package> packages;
}
