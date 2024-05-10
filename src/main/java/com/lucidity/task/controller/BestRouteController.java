package com.lucidity.task.controller;

import com.lucidity.task.service.DeliveryRouting;
import com.lucidity.task.model.Node;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/delivery")
public class BestRouteController {

    private final DeliveryRouting deliveryRouting;

    public BestRouteController(final DeliveryRouting deliveryRouting) {
        this.deliveryRouting = deliveryRouting;
    }

    @PostMapping("/optimize")
    public ResponseEntity<List<String>> optimizeDelivery(@RequestBody final Map<String, Node> nodes) {
        return ResponseEntity.ok(deliveryRouting.computeOptimalPath(nodes));
    }
}
