package com.rda.optimal.route;

import com.rda.optimal.route.common.OptimalRoutingPathBuilder;
import com.rda.optimal.route.common.Utils;
import com.rda.optimal.route.exception.BaseException;
import com.rda.optimal.route.model.DeliveryNetworkGraph;
import com.rda.optimal.route.model.Package;
import com.rda.optimal.route.model.WareHouse;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/*
 * Coding problem, Add your testcase
 * Provide implementation for method processAndStorePackageRoutes()
 * */
public class CodingContestMain {

    Map<String, String> packageRoutes = new TreeMap<>();


    /**
     * Process input file and provide the packageRoutes;
     *
     * @param inputFile
     * @return packageRoutes
     */
    public Map<String, String> processAndStorePackageRoutes(File inputFile) {
        //Read and Process input file and store optimized route and time for each package in packageRoutes <packageid, route_and_time>
        //Format for route_and_time is:  <wh_1> <wh_2> [<wh_3>, …] : <total_travel_time>
        // e.g. A5 A1 A2 A3 A4 : 9
        //Please update final output in packageRoutes

        //Build entire network graph
        DeliveryNetworkGraph deliveryNetworkGraph = Utils.buildDeliveryNetworkGraph(inputFile);

        //Feed network to the processing algorithm
        OptimalRoutingPathBuilder optimalRoutingPathBuilder = new OptimalRoutingPathBuilder(deliveryNetworkGraph);

        //process packages one by one
        for (Package p : deliveryNetworkGraph.getPackages()) {
            //build SPG (shortest path graph) for current package's source
            List<WareHouse> wareHouseInCity = deliveryNetworkGraph.getWareHouses().stream().filter(w -> w.getId().equalsIgnoreCase(p.getSourceWarehouse())).collect(Collectors.toList());
            if (wareHouseInCity == null || wareHouseInCity.isEmpty()) {
                throw new BaseException("Couldn't find the warehouse in the city: " + p.getDestinationCity());
            }
            optimalRoutingPathBuilder.prepareOptimalPaths(wareHouseInCity.get(0));

            //get destination warehouse from current package
            wareHouseInCity = deliveryNetworkGraph.getWareHouses().stream().filter(w -> w.getCity().equalsIgnoreCase(p.getDestinationCity())).collect(Collectors.toList());
            if (wareHouseInCity == null || wareHouseInCity.isEmpty()) {
                throw new BaseException("Couldn't find the warehouse in the city: " + p.getDestinationCity());
            }
            WareHouse dest = wareHouseInCity.get(0);

            //get optimal path from source warehouse to destination warehouse for current package
            LinkedList<WareHouse> optimalPath = optimalRoutingPathBuilder.getOptimalPathForDestination(dest);

            //create final path string as per required output
            String finalPath = "";

            if (optimalPath == null) {
                finalPath = "Can't deliver package with ID: " + p.getId() + ".Because no connected warehouses found to reach from " +
                        p.getSourceWarehouse() + " to " + p.getDestinationCity();
            } else {
                for (WareHouse w : optimalPath) {
                    finalPath += w.getId() + " ";
                }
                finalPath += ": " + optimalRoutingPathBuilder.distance.get(dest);
            }
            packageRoutes.put(p.getId(), finalPath);
        }

        return packageRoutes;
    }

}
