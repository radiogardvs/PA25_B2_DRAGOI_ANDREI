abstract class Aircraft {
    protected String name, model, tailNumber;

    public Aircraft(String name, String model, String tailNumber) {
        this.name = name;
        this.model = model;
        this.tailNumber = tailNumber;
    }

    public String getName() {
        return name;
    }
}