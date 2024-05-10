package com.lucidity.task.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Node {
    String name;
    Location location;
    int prepTime;
    List<Edge> adjacent = new ArrayList<>();

    public Node(final String name, final Location location, final int prepTime) {
        this.name = name;
        this.location = location;
        this.prepTime = prepTime;
    }

    public Node(final String name, final Location location) {
        this(name, location, 0);
    }

    void addEdge(final Node node, final double weight) {
        this.adjacent.add(new Edge(weight, node));
    }
}
