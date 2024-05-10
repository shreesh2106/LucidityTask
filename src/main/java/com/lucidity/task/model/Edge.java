package com.lucidity.task.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Edge {
    double weight;
    Node node;

    public Edge(double weight, Node node) {
        this.weight = weight;
        this.node = node;
    }
}
