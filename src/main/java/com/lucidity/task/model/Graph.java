package com.lucidity.task.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Graph {
    static final double AMAN_SPEED = 20.0;
    List<Node> nodes = new ArrayList<>();
    double[][] distanceMatrix;
    Map<String, Integer> nameToIndex = new HashMap<>();

    public void addNode(final Node node) {
        nodes.add(node);
        nameToIndex.put(node.name, nodes.size() - 1);
    }

    public void setupDistanceMatrix() {
        final int n = nodes.size();
        distanceMatrix = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    distanceMatrix[i][j] = 0;
                } else {
                    final double distance = nodes.get(i).getLocation().distanceTo(nodes.get(j).getLocation());
                    final double speed = (nodes.get(i).name.equals("Aman") || nodes.get(j).name.equals("Aman")) ? AMAN_SPEED : 20; // Use Aman's speed if either node is Aman
                    distanceMatrix[i][j] = (distance / speed) * 60 + nodes.get(j).prepTime;  // Convert hours to minutes
                }
            }
        }
    }
}
