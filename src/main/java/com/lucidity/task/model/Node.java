package com.lucidity.task.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Node {
    String name;
    Location location;
    int prepTime;

    public Node(final String name, final double latitude, final double longitude, final int prepTime) {
        this.name = name;
        this.location = new Location(latitude, longitude);
        this.prepTime = prepTime;
    }
}
