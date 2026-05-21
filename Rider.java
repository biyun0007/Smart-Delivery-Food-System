public class Rider {

    private int riderID;
    private String riderName;
    private double currentDistance;
    private double rating;

    // Constructor
    public Rider(int riderID, String riderName, double currentDistance, double rating) {
        this.riderID = riderID;
        this.riderName = riderName;
        this.currentDistance = currentDistance;
        this.rating = rating;
    }

    // Getters
    public int getRiderID() {
        return riderID;
    }

    public String getRiderName() {
        return riderName;
    }

    public double getCurrentDistance() {
        return currentDistance;
    }

    public double getRating() {
        return rating;
    }

    // Display Rider Information
    @Override
    public String toString() {
        return "Rider ID: " + riderID +
                " | Name: " + riderName +
                " | Distance: " + currentDistance + " km" +
                " | Rating: " + rating;
    }
}