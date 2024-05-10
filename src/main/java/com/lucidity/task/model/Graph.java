package com.lucidity.task.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class Graph {
    Map<String, Node> nodes;

    public Graph() {
        this.nodes = new HashMap<>();
    }

    public void addNode(Node node) {
        nodes.put(node.name, node);
    }

    void addEdge(String fromNode, String toNode) {
        Node from = nodes.get(fromNode);
        Node to = nodes.get(toNode);
        double distance = from.location.distanceTo(to.location);
        double adjustedTime = travelTime(distance, 20) + to.prepTime;
        from.addEdge(to, adjustedTime);
    }

    public void addAllEdges(Map<String, Node> nodes) {
        for (Node from : nodes.values()) {
            for (Node to : nodes.values()) {
                if (!from.name.equals(to.name)) {
                    double distance = from.location.distanceTo(to.location);
                    double travelTime = travelTime(distance, 20);
                    double adjustedTime = travelTime + to.prepTime;
                    from.addEdge(to, adjustedTime);
                }
            }
        }
    }

    public double calculateCost(List<String> path) {
        double totalCost = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            Node fromNode = nodes.get(path.get(i));
            Node toNode = nodes.get(path.get(i + 1));
            double distance = fromNode.location.distanceTo(toNode.location);
            totalCost += travelTime(distance, 20) + toNode.prepTime;
        }
        return totalCost;
    }

    double travelTime(double distance, double speed) {
        return distance / speed * 60;  // Convert hours to minutes
    }
}
