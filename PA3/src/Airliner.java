class Airliner extends Aircraft implements PassengerCapable {
    private int passengerCapacity;

    public Airliner(String name, String model, String tailNumber, int passengerCapacity) {
        super(name, model, tailNumber);
        this.passengerCapacity = passengerCapacity;
    }

    @Override
    public int getPassengerCapacity() {
        return passengerCapacity;
    }
}