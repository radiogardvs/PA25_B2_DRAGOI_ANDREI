class Drone extends Aircraft {
    private double batteryLife;

    public Drone(String name, String model, String tailNumber, double batteryLife) {
        super(name, model, tailNumber);
        this.batteryLife = batteryLife;
    }
}