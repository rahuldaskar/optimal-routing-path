package com.rda.optimal.route.common;

import com.rda.optimal.route.model.DeliveryNetworkGraph;
import com.rda.optimal.route.model.Route;
import com.rda.optimal.route.model.WareHouse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of Dijkstra Algorithm for shortest path
 */
public class OptimalRoutingPathBuilder {
    private final List<WareHouse> wareHouses;
    private final List<Route> routes;
    private Set<WareHouse> settledNodes;
    private Set<WareHouse> unSettledNodes;
    private Map<WareHouse, WareHouse> predecessors;
    public Map<WareHouse, Integer> distance;

    public OptimalRoutingPathBuilder(DeliveryNetworkGraph graph) {
        // create a copy of the array so that we can operate on this array
        this.wareHouses = new ArrayList<WareHouse>(graph.getWareHouses());
        this.routes = new ArrayList<Route>(graph.getRoutes());
    }

    public void prepareOptimalPaths(WareHouse source) {
        settledNodes = new HashSet<WareHouse>();
        unSettledNodes = new HashSet<WareHouse>();
        distance = new HashMap<WareHouse, Integer>();
        predecessors = new HashMap<WareHouse, WareHouse>();
        distance.put(source, 0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            WareHouse node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(WareHouse node) {
        List<WareHouse> adjacentNodes = getNeighbors(node);
        for (WareHouse target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private int getDistance(WareHouse node, WareHouse target) {
        for (Route route : routes) {
            if (route.getSource().equals(node)
                    && route.getDestination().equals(target)) {
                return route.getTransitTime();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<WareHouse> getNeighbors(WareHouse node) {
        List<WareHouse> neighbors = new ArrayList<WareHouse>();
        for (Route route : routes) {
            if (route.getSource().equals(node)
                    && !isSettled(route.getDestination())) {
                neighbors.add(route.getDestination());
            }
        }
        return neighbors;
    }

    private WareHouse getMinimum(Set<WareHouse> vertexes) {
        WareHouse minimum = null;
        for (WareHouse vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(WareHouse vertex) {
        return settledNodes.contains(vertex);
    }

    private int getShortestDistance(WareHouse destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public LinkedList<WareHouse> getOptimalPathForDestination(WareHouse target) {
        LinkedList<WareHouse> path = new LinkedList<WareHouse>();
        WareHouse step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }
}
