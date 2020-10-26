package com.rda.optimal.route.common;

import com.rda.optimal.route.exception.BaseException;
import com.rda.optimal.route.model.DeliveryNetworkGraph;
import com.rda.optimal.route.model.Package;
import com.rda.optimal.route.model.Route;
import com.rda.optimal.route.model.WareHouse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is util class that reads input file and
 * transform it into the DeliveryNetworkGraph.
 */
public final class Utils {

    /**
     * Hiding default constructor.
     */
    private Utils() {
    }

    /**
     * Reads input file and builds graph.
     *
     * @param input
     * @return graph
     */
    public static DeliveryNetworkGraph buildDeliveryNetworkGraph(File input) {
        List<WareHouse> wareHouses = new ArrayList<>();
        wareHouses.add(Constants.RETURN_WARE_HOUSE);
        List<Route> routes = new ArrayList<>();
        List<Package> packages = new ArrayList<>();
        DeliveryNetworkGraph deliveryNetworkGraph = new DeliveryNetworkGraph(wareHouses, routes, packages);
        try {
            List<String> lines = Files.readAllLines(input.toPath());
            Iterator<String> iterator = lines.iterator();

            if (iterator.hasNext()) {
                String firstLine = iterator.next();
                if (firstLine.trim().isEmpty() || !firstLine.equalsIgnoreCase(Constants.INPUT_SECTION_1)) {
                    throw new BaseException("Input file should start with " + Constants.INPUT_SECTION_1);
                }
                String currentLine = iterator.next();
                while (!currentLine.trim().isEmpty()) {
                    //process section 1
                    String[] tokens = currentLine.split(Constants.INPUT_SPLIT_CHAR);
                    wareHouses.add(new WareHouse(tokens[0], tokens[1]));
                    currentLine = iterator.next();
                }

                //skip comment
                currentLine = iterator.next();
                currentLine = iterator.next();
                while (!currentLine.trim().isEmpty()) {
                    //process section 2
                    String[] tokens = currentLine.split(Constants.INPUT_SPLIT_CHAR);
                    List<WareHouse> matchedWarehouse = wareHouses.stream().filter(w -> w.getId().equalsIgnoreCase(tokens[0].trim())).collect(Collectors.toList());
                    if (matchedWarehouse == null || matchedWarehouse.isEmpty()) {
                        throw new BaseException("Error while processing delivery networks. No warehouse found with ID " + tokens[0].trim());
                    }
                    WareHouse source = matchedWarehouse.get(0);

                    matchedWarehouse = wareHouses.stream().filter(w -> w.getId().equalsIgnoreCase(tokens[1].trim())).collect(Collectors.toList());
                    if (matchedWarehouse == null || matchedWarehouse.isEmpty()) {
                        throw new BaseException("Error while processing delivery networks. No warehouse found with ID " + tokens[1].trim());
                    }
                    WareHouse destination = matchedWarehouse.get(0);
                    Route route = new Route(source, destination, Integer.valueOf(tokens[2]));
                    if (route.getTransitTime() < 0) {
                        throw new BaseException("Error while processing route: " + route.toString() + "Transit time can not be -ve, please fix input and provide again!");
                    }
                    Route reverse = new Route(destination, source, Integer.valueOf(tokens[2]));
                    routes.add(route);
                    routes.add(reverse);

                    currentLine = iterator.next();
                }

                //skip comment
                currentLine = iterator.next();
                while (iterator.hasNext()) {
                    currentLine = iterator.next();
                    //process section 3
                    String[] tokens = currentLine.split(Constants.INPUT_SPLIT_CHAR);
                    Package aPackage = new Package(tokens[0].trim(), tokens[1].trim(), tokens[2].trim());
                    packages.add(aPackage);
                }

            } else {
                throw new BaseException(Constants.EMPTY_FILE_ERROR);
            }
        } catch (IOException ex) {
            throw new BaseException(Constants.CANT_READ_INPUT_ERROR, ex);
        }

        return deliveryNetworkGraph;
    }

}
