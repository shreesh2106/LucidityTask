package com.lucidity.task.service;

import com.lucidity.task.model.Graph;
import com.lucidity.task.model.Node;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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

        final Graph graph = new Graph();
        final Map<String, String> deliveryPredecessors = new HashMap<>();
        final List<Node> nodeList = new ArrayList<>();
        for (final Map.Entry<String, Node> node : nodes.entrySet()) {
            graph.addNode(node.getValue());
            nodeList.add(node.getValue());
            if (node.getValue().getName().startsWith("C")) {
                final String correspondingPickup = "R" + node.getValue().getName().substring(1);
                deliveryPredecessors.put(node.getValue().getName(), correspondingPickup);
            }
        }

        graph.setupDistanceMatrix();
        final List<String> path = new ArrayList<>();
        final String result = findMinCostDP(graph, deliveryPredecessors, path);

        log.info("Optimal Path: " + String.join(" -> ", path));
        log.info("Total Cost: " + result);

        return path;
    }

    private String findMinCostDP(final Graph graph, final Map<String, String> deliveryPredecessors, final List<String> path) {
        final int n = graph.getNodes().size();
        final double[][] costTable = new double[1 << n][n];
        final int[][] parent = new int[1 << n][n];
        for (final double[] row : costTable) Arrays.fill(row, Double.MAX_VALUE);
        for (final int[] row : parent) Arrays.fill(row, -1);
        costTable[1][0] = 0;  // start at node 0 (Aman)

        for (int nodeVisitMask = 1; nodeVisitMask < (1 << n); nodeVisitMask++) {
            for (int currentNodeIndex = 0; currentNodeIndex < n; currentNodeIndex++) {
                if ((nodeVisitMask & (1 << currentNodeIndex)) != 0) {
                    for (int previousNodeIndex = 0; previousNodeIndex < n; previousNodeIndex++) {
                        if ((nodeVisitMask & (1 << previousNodeIndex)) != 0 && currentNodeIndex != previousNodeIndex) {
                            final int prevMask = nodeVisitMask ^ (1 << currentNodeIndex);
                            if (isValidTransition(graph.getNodes().get(currentNodeIndex).getName(), nodeVisitMask, deliveryPredecessors, graph)) {
                                if (costTable[prevMask][previousNodeIndex] + graph.getDistanceMatrix()[previousNodeIndex][currentNodeIndex] < costTable[nodeVisitMask][currentNodeIndex]) {
                                    costTable[nodeVisitMask][currentNodeIndex] = costTable[prevMask][previousNodeIndex] + graph.getDistanceMatrix()[previousNodeIndex][currentNodeIndex];
                                    parent[nodeVisitMask][currentNodeIndex] = previousNodeIndex;
                                }
                            }
                        }
                    }
                }
            }
        }

        double minCost = Double.MAX_VALUE;
        int lastVisitedNodeIndex = 0;
        for (int currentNodeIndex = 1; currentNodeIndex < n; currentNodeIndex++) {
            if (costTable[(1 << n) - 1][currentNodeIndex] < minCost) {
                minCost = costTable[(1 << n) - 1][currentNodeIndex];
                lastVisitedNodeIndex = currentNodeIndex;
            }
        }

        int currentStateMask = (1 << n) - 1;
        if (lastVisitedNodeIndex == 0) return "Invalid path";

        while (lastVisitedNodeIndex != -1) {
            path.add(graph.getNodes().get(lastVisitedNodeIndex).getName());
            final int temp = lastVisitedNodeIndex;
            lastVisitedNodeIndex = parent[currentStateMask][lastVisitedNodeIndex];
            currentStateMask ^= (1 << temp);
        }
        Collections.reverse(path);
        return "Minimum Cost: " + minCost;
    }

    private boolean isValidTransition(final String to, final int nodeVisitMask, final Map<String, String> deliveryPredecessors, final Graph graph) {
        if (to.startsWith("C")) {
            final String requiredPickup = deliveryPredecessors.get(to);
            if (requiredPickup == null) return true;
            final int pickupIndex = graph.getNameToIndex().get(requiredPickup);
            return (nodeVisitMask & (1 << pickupIndex)) != 0;
        }
        return true;
    }

}