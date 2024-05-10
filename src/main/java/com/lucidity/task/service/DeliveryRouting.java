package com.lucidity.task.service;

import com.lucidity.task.model.Graph;
import com.lucidity.task.model.Node;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DeliveryRouting {

    public List<String> computeOptimalPath(final Map<String, Node> nodes) {

        if (nodes.isEmpty()) {
            return Collections.singletonList("The input is empty.");
        }

        final List<String> locations = new ArrayList<>();
        final Map<String, String> deliveryMap = new HashMap<>();
        final Graph graph = new Graph();

        for (final Map.Entry<String, Node> node : nodes.entrySet()) {
            graph.addNode(node.getValue());
            if (!node.getValue().getName().equals("Aman")) {
                locations.add(node.getValue().getName());
                if (node.getValue().getName().startsWith("C")) {
                    final String correspondingPickup = "R" + node.getValue().getName().substring(1);
                    deliveryMap.put(correspondingPickup, node.getValue().getName());
                }
            }
        }
        graph.addAllEdges(nodes);
        List<String> optimalPath = new ArrayList<>();
        double minCost = Double.MAX_VALUE;

        final List<List<String>> allPermutations = generatePermutations(new ArrayList<>(locations));
        for (final List<String> permutation : allPermutations) {
            if (isValidRoute(permutation, deliveryMap)) {
                final List<String> fullPath = new ArrayList<>();
                fullPath.add("Aman");
                fullPath.addAll(permutation);
                double cost = graph.calculateCost(fullPath);
                if (cost < minCost) {
                    minCost = cost;
                    optimalPath = new ArrayList<>(fullPath);
                }
            }
        }

        System.out.println("Optimal Path: " + String.join(" -> ", optimalPath));
        System.out.println("Total Cost: " + minCost);

        return optimalPath;
    }

    private static List<List<String>> generatePermutations(final List<String> original) {
        if (original.isEmpty()) {
            final List<List<String>> result = new ArrayList<>();
            result.add(new ArrayList<>());
            return result;
        }
        final String firstElement = original.remove(0);
        final List<List<String>> returnValue = new ArrayList<>();
        final List<List<String>> permutations = generatePermutations(original);
        for (final List<String> smallerPermutated : permutations) {
            for (int index = 0; index <= smallerPermutated.size(); index++) {
                final List<String> temp = new ArrayList<>(smallerPermutated);
                temp.add(index, firstElement);
                returnValue.add(temp);
            }
        }
        original.add(0, firstElement);
        return returnValue;
    }

    private static boolean isValidRoute(final List<String> route, final Map<String, String> deliveryMap) {
        for (final Map.Entry<String, String> entry : deliveryMap.entrySet()) {
            if (route.indexOf(entry.getValue()) <= route.indexOf(entry.getKey())) {
                return false;
            }
        }
        return true;
    }

}