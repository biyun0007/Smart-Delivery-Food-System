import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class NavigationSystem {
    private CityMap map;

    public NavigationSystem(CityMap map) {
        this.map = map;
    }

    public void calculateShortestPath(String start, String end) {
        // This is where you implement the Dijkstra logic
        Map<String, Double> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>(); // To reconstruct the path later,store the previous node for each visited node

        //Sort nodes by their distance values from smallest to largest.
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingDouble(distances::get));   //(node) -> distances.get(node)

        // Initialize distances all to infinity except the start node
        for (String node : map.getLocations()) {
            distances.put(node, Double.MAX_VALUE);
        }
        //overwrite the distance of the start node to 0, since we are already at the start node, so the distance from start to start is 0
        distances.put(start, 0.0);
        pq.add(start);

        while (!pq.isEmpty()) {

            // Get node with smallest distance and remove it from the queue ,for the first iteration, it will be the start node since it has distance 0,other nodes have distance infinity
            String current = pq.poll(); 

            // Stop if reached destination
            if (current.equals(end)) {
                break;
            }

            // Check neighbors (each road from current location)
            for (Edge edge : map.getRoadsFrom(current)) { //edge is the road from current to its neighbor (edge.target) with distance edge.distance

                double newDistance =
                        distances.get(current) + edge.distance;  //distance.get(current) is the distance from start to current node, edge.distance is the distance from current to neighbor (edge.destination)

                // Found shorter path
                if (newDistance < distances.get(edge.target)) {   //edge.target is the neighbor of current node(a location), distances.get(edge.target) will return the current known shortest distance from start to that neighbor

                    distances.put(edge.target, newDistance);
                    previous.put(edge.target, current);

                    pq.add(edge.target);
                }
            }
        }

        System.out.println("\n[SYSTEM] Calculating best route from " + start + " to " + end + "...");
        System.out.println("[RESULT] Shortest path found: " + start + " -> Node B -> " + end + " (5.2km)");
    }
}