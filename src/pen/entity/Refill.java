package pen.entity;

public class Refill {

    private Ink ink;
    private double nibRadius;

    public Refill(Ink ink, double nibRadius) {
        this.ink = ink;
        this.nibRadius = nibRadius;
    }

    public Ink getInk() {
        return ink;
    }

    public double getNibRadius() {
        return nibRadius;
    }
}