package model;

public class Route {
    private Location from;
    private Location to;
    private int time; // in minutes
    private double safetyProbability; // between 0 and 1

    public Route(Location from, Location to, int time, double safetyProbability) {
        this.from = from;
        this.to = to;
        this.time = time;
        this.safetyProbability = safetyProbability;
    }

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }

    public int getTime() {
        return time;
    }

    public double getSafetyProbability() {
        return safetyProbability;
    }

    @Override
    public String toString() {
        return from.getName() + " -> " + to.getName() + " (time: " + time + " mins, safety: " + safetyProbability + ")";
    }
}
