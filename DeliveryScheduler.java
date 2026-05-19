import java.util.PriorityQueue;
import java.util.Comparator;

public class DeliveryScheduler {

    // Priority Queue using Min Heap
    private PriorityQueue<Rider> riderQueue;

    // Constructor
    public DeliveryScheduler() {
        // Primary Sort: Shortest Distance (Ascending)
        // Secondary Sort (Tie-breaker): Highest Rating (Descending)
        this.riderQueue = new PriorityQueue<>(
            Comparator.comparingDouble(Rider::getCurrentDistance)
                      .thenComparing((r1, r2) -> Double.compare(r2.getRating(), r1.getRating()))
        );
    }

    // Register Rider into Priority Queue
    public void registerRider(Rider rider) {
        riderQueue.offer(rider);
        System.out.println("Rider Registered Successfully: " + rider.getRiderName());
    }

    // Display All Riders in true Priority Order (Corrected for-each loop bug)
    public void displayAllRiders() {
        if (riderQueue.isEmpty()) {
            System.out.println("No Riders Available.\n");
            return;
        }

        System.out.println("===== Available Riders (Sorted by Priority) =====");
        
        // Create a temporary copy to drain elements in order
        PriorityQueue<Rider> tempQueue = new PriorityQueue<>(riderQueue.comparator());
        tempQueue.addAll(riderQueue);

        while (!tempQueue.isEmpty()) {
            System.out.println(tempQueue.poll());
        }
        System.out.println();
    }

    // View Best Rider Without Removing
    public void peekBestRider() {
        if (riderQueue.isEmpty()) {
            System.out.println("No Riders Available.\n");
            return;
        }
        System.out.println("===== Best Rider (Nearest Rider) =====");
        System.out.println(riderQueue.peek());
        System.out.println();
    }

    // Assign Best Rider
    public void assignBestRider() {
        if (riderQueue.isEmpty()) {
            System.out.println("No Riders Available For Assignment.\n");
            return;
        }

        Rider assignedRider = riderQueue.poll(); // Removes the top of the Min-Heap
        System.out.println("===== Rider Assigned Successfully =====");
        System.out.println(assignedRider);
        System.out.println();
    }
}