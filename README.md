# Delivery Routing System

## Project Overview
This Java application dynamically builds a delivery graph based on JSON input and calculates the optimal route using a combinatorial approach. It supports flexible input, automatically adapting to changes in the number of nodes and their specific relationships.

## Assumptions
- **Constant speed.** 
- **Every restaurant has single customer i.e. one to one mapping.**
- **Considered only for single Delivery guy.**

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

## Detailed Code Explanation
### Classes and Their Roles

#### Location Class
- Holds latitude and longitude of a node.
- Utilized by the `Node` class to store geographical data.

#### Node Class
- Represents a graph node, which could be a pickup, delivery, or the starting point ("Aman").
- Attributes include a `name`, a `Location` object, `prepTime`, and a list of `Edge` objects.
- `addEdge` method: Adds an outgoing edge to this node, linking it to another node with specified travel weight.

#### Edge Class
- Represents a directed connection between two nodes.
- Stores the weight of the edge (travel time) and a reference to the destination node.

#### Graph Class
- Contains all nodes and manages connections (edges) between them.
- `addNode`: Adds a node to the graph.
- `addAllEdges`: Automatically creates edges between all pairs of nodes using the haversine formula for distance and calculates travel time.
- `calculateCost`: Computes the total cost (time) of traveling a specified path through the graph.

#### haversineDistance (method)
- Calculates the great-circle distance between two points on a sphere given their longitudes and latitudes.
- Essential for calculating the real-world distance between two nodes.

#### travelTime (method)
- Converts a distance (in kilometers) into travel time based on a preset speed (20 km/h here), converting the result into minutes.

### Main Method and Program Flow

- **JSON Parsing**: Nodes are dynamically created from a JSON string, including their properties such as name, coordinates, and preparation time.
- **Graph Construction**: All nodes are added to a graph, and edges between them are created based on calculated distances.
- **Permutation Generation and Validation**: Generates all possible routes from the node permutations, checks each for validity ensuring deliveries follow pickups.
- **Optimal Path Calculation**: Finds and outputs the path with the minimal total cost.

## Example Usage
The program is executed with a predefined JSON string that includes node data. It outputs the optimal path based on the constraints that deliveries must follow pickups.

## Conclusion
This system is ideal for scenarios where route optimization is needed based on dynamic input. It efficiently handles varying numbers of nodes and their relationships, making it suitable for delivery services and logistics planning.
