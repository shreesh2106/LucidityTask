package com.lucidity.task.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Location {
     public final double lat;
     public final double lon;

    public Location(final double lat, final double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double distanceTo(final Location other) {
        final double R = 6372.8;

        final double dLat = Math.toRadians(other.lat - this.lat);
        final double dLon = Math.toRadians(other.lon - this.lon);
        final double lat1 = Math.toRadians(this.lat);
        final double lat2 = Math.toRadians(other.lat);

        final double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        final double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }

}
