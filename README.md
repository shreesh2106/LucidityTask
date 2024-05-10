# Delivery Routing System

## Project Overview
This project implements a dynamic programming solution to the Traveling Salesman Problem (TSP), tailored to handle specific constraints where certain nodes (pickups) must be visited before others (deliveries). It's designed to find the most cost-effective route that visits all given locations exactly once and returns to the starting point, while respecting the order constraints between paired nodes.

## Assumptions
- **Constant speed.** 
- **Every restaurant Ri and customer Ci should be considered as one order rather than individuals.**
- **Considered only for single Delivery guy.**

## Steps to run the code
- **Clone the repository using git clone.**
- **The main class is LucidityProjectApplication.**
- **The curl which can be used to run the application**: curl --location 'http://localhost:8080/lucidity/delivery/optimize' \
  --header 'Content-Type: application/json' \
  --data '{
  "Aman" : {
  "name" : "Aman",
  "location": {
  "lat":12.933,
  "lon":77.622
  },
  "prepTime": 0
  },
  "R3" : {
  "name" : "R3",
  "location": {
  "lat":12.935,
  "lon":77.624
  },
  "prepTime": 1
  },
  "R1" : {
  "name" : "R1",
  "location": {
  "lat":12.944,
  "lon":77.640
  },
  "prepTime": 10
  },
  "R2" : {
  "name" : "R2",
  "location": {
  "lat":12.935,
  "lon":77.624
  },
  "prepTime": 15
  },
  "C1" : {
  "name" : "C1",
  "location": {
  "lat":12.937,
  "lon":77.629
  },
  "prepTime": 0
  },
  "C1=2" : {
  "name" : "C2",
  "location": {
  "lat":12.940,
  "lon":77.634
  },
  "prepTime": 0
  },
  "C3" : {
  "name" : "C3",
  "location": {
  "lat":8.537,
  "lon":8.629
  },
  "prepTime": 0
  }
  }'
## Features
- **Dynamic Node Parsing**: Parses nodes from a JSON, allowing easy modification of node characteristics and relationships.
- **Automatic Edge Creation**: Automatically creates edges between all nodes with calculated travel times based on geographic distances.
- **Route Optimization**: Uses permutations to explore all possible routes, ensuring that deliveries are made after pickups.

## Technologies Used
- Java
- JSON for node data representation
- Haversine formula for distance calculation

## Setup and Execution
1. Ensure Java is installed on your machine.
2. Include the JSON.org library in your project to handle JSON parsing.
3. Compile and run the `DeliveryRoutingSystem.java` file.

### Dynamic Programming Approach

#### Key Components

1. **Graph Representation**
  - **Nodes**: Represents locations including a starting point, several pickup points, and corresponding delivery points.
  - **Edges**: Represents possible routes between each pair of nodes with associated travel costs that include travel time and any preparation time at the destination node.

2. **State Representation**
  - A state in the dynamic programming table is represented by a bitmask where each bit indicates whether a corresponding node has been visited (`1`) or not (`0`).

3. **Transition Function**
  - The transition between states is governed by the feasibility of moving from one node to another, ensuring that no delivery node is visited before its corresponding pickup node.

4. **Cost Calculation**
  - The cost associated with each transition is calculated based on the distance between nodes, adjusted by the travel speed and any additional time spent at the nodes.

5. **Initialization**
  - The dynamic programming table (`dp`) is initialized such that the cost of starting from the initial node is zero and all other costs are set to infinity.

6. **Table Filling**
  - The DP table is populated by considering all possible states and making transitions only if they are permissible under the problem constraints (e.g., pickup before delivery).

7. **Path Reconstruction**
  - After the table is filled, the optimal path is reconstructed by backtracking from the final state to the starting state using a parent tracking array that records the optimal preceding node for each state.

### Code Structure

1. **Graph Setup**
  - Nodes and edges are defined with distances calculated using the Haversine formula, which estimates the shortest distance over the earth's surface.

2. **Dynamic Programming Table Definition**
  - A 2D array `dp` where `dp[mask][i]` holds the minimum cost to reach state `mask` ending at node `i`.

3. **Recursive State Transitions**
  - For each possible state and ending node, the algorithm explores all feasible transitions from other nodes and updates the DP table accordingly.

4. **Result Extraction**
  - The minimal travel cost and path are extracted from the DP table, ensuring the constraints are met.

## Algorithm/Performance
To enhance the Delivery Routing System using Dynamic Programming (DP), we can employ the Held-Karp algorithm, which is a well-known approach for solving the Traveling Salesman Problem (TSP). The Held-Karp algorithm uses dynamic programming to find the shortest path that visits each vertex once and returns to the origin vertex, achieving an optimal solution. This approach is considerably more efficient than the factorial time complexity of generating all permutations when
even n is small, though it remains exponential and thus best suited for smaller datasets (typically up to 20-25 nodes).
## Example Usage
The solution can be utilized in scenarios such as logistics planning where deliveries must follow pickups, and routes must be optimized for cost and compliance with operational rules.
## Conclusion
This implementation not only solves the TSP under standard conditions but also adapts to scenarios with additional logical constraints, making it a robust tool for route optimization in complex logistical operations.