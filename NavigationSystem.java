import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class NavigationSystem {
    private CityMap map;
    private Map<String, Double> distances = new HashMap<>();
    private Map<String, String> previous = new HashMap<>(); // To reconstruct the path later,store the previous node for each visited node
    NodeLinkedList<String> finalRoute = new NodeLinkedList<>();

    public NavigationSystem(CityMap map) {
        this.map = map;
    }

    public double calculateShortestPath(String start, String end) {
        // This is where you implement the Dijkstra logic
    
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
                //edge.target is the immediate neighbor node, NOT the final destination, distances.get(edge.target) will return the current known shortest distance from start to that neighbor node. 
                //If newDistance is smaller, it means we found a shorter path to reach that neighbor node through current node.
                if (newDistance < distances.get(edge.target)) { 
                        distances.put(edge.target, newDistance);
                        previous.put(edge.target, current);

                        //Remove it first so the queue forces a clean re-sort on re-adding
                        pq.remove(edge.target); 
                        pq.add(edge.target);
                    }
            }
        }

        String step = end; // Start tracing backward from the destination

        // Trace backward using the parent breadcrumbs until we hit the starting node
        while (step != null) {
            finalRoute.add(step); // Add this location node into our Linked List chain
            step = previous.get(step); // Move backward to the node that led here
        }

        System.out.println("\nCalculating best route from " + start + " to " + end + "...");
        System.out.println("Shortest path found: " + start + " -> " + end + " (" + distances.get(end) + "km)");
        return distances.get(end);
    }

        


    public void simulateDeliveryRoute(NodeLinkedList<String> shortestPath, CityMap map) {
        if (shortestPath == null || shortestPath.isEmpty()) {
            System.out.println("Error! Invalid or empty route.");
            return;
        }

        System.out.println("\n[Rider is Out For Delivery!");

        // 1. Grab our custom head node to begin traversing the chain
        Node<String> current = shortestPath.getHead();

        // Loop as long as there is a next node to travel to
        while (current != null && current.next != null) {
            String currentNodeName = current.getDestination();
            String nextNodeName = current.next.getDestination();

            // 2. Find the distance of this specific road segment from your CityMap
            double roadDistance = 0;
            
            // Check if map contains current node in its adjacency list safely
            if (map.getAdjacencyList().containsKey(currentNodeName)) {
                for (Edge edge : map.getAdjacencyList().get(currentNodeName)) {
                    if (edge.target.equals(nextNodeName)) {
                        // Match whatever parameter name Person 5 used (distance or weight)
                        roadDistance = edge.distance; // Assuming Edge has a distance field
                        break;
                    }
                }
            }

            // 3. Countdown simulation for the current road segment
            while (roadDistance > 0) {
                // Note: Changed %d to %.2f since distance is a double (decimal number)
                System.out.printf("Rider is at [%s] -> Heading to [%s] | Remaining: %.2f KM\n", 
                                currentNodeName, nextNodeName, roadDistance);
                
                try {
                    Thread.sleep(1500); // Wait 1.5 seconds to simulate travel time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
                roadDistance--; // Decrease distance unit by 1 each loop step
            }
            current = current.next;
        }

        // 5. Final success message using data pointers
        // Find the very last location name by scanning to the end node
        Node<String> finalNode = shortestPath.getHead();
        while (finalNode.next != null) {
            finalNode = finalNode.next;
        }
        System.out.println("\nYour food has arrived safely at " + finalNode.getDestination() + "!");
    }
}