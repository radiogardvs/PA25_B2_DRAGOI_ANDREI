class Freighter extends Aircraft implements CargoCapable {
    private double maxPayload;

    public Freighter(String name, String model, String tailNumber, double maxPayload) {
        super(name, model, tailNumber);
        this.maxPayload = maxPayload;
    }

    @Override
    public double getMaxPayload() {
        return maxPayload;
    }
}
