package com.rda.optimal.route.model;

import lombok.Data;

/**
 * Holds package details for which we have to find the least transit time.
 */
@Data
public class Package {
    private final String id;
    private final String sourceWarehouse;
    private final String destinationCity;
}
