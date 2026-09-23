package pen.entity;

import pen.enums.PenType;
import pen.strategy.OpeningMechanism;

public class Pen {

    private String brand;
    private String name;
    private double price;
    private PenType type;

    private Refill refill;
    private Ink ink;
    private OpeningMechanism openingMechanism;

    private boolean open;

    public Pen(
            String brand,
            String name,
            double price,
            PenType type,
            Refill refill,
            Ink ink,
            OpeningMechanism openingMechanism
    ) {
        if (type == PenType.FOUNTAIN && refill != null) {
            throw new IllegalArgumentException("Fountain pen cannot have a refill");
        }

        if (type != PenType.FOUNTAIN && refill == null) {
            throw new IllegalArgumentException("Refill is required for this pen type");
        }

        if (type == PenType.FOUNTAIN && ink == null) {
            throw new IllegalArgumentException("Fountain pen requires ink");
        }

        this.brand = brand;
        this.name = name;
        this.price = price;
        this.type = type;
        this.refill = refill;
        this.ink = ink;
        this.openingMechanism = openingMechanism;
        this.open = false;
    }

    public void write() {
        if (!open) {
            throw new IllegalStateException("Pen is closed");
        }

        System.out.println(name + " is writing with " + getInkColor() + " ink");
    }

    public void open() {
        if (open) {
            return;
        }

        openingMechanism.open();
        open = true;
    }

    public void close() {
        if (!open) {
            return;
        }

        openingMechanism.close();
        open = false;
    }

    public void refill(Refill refill) {
        if (type == PenType.FOUNTAIN) {
            throw new IllegalStateException("Fountain pen does not use a refill");
        }

        if (refill == null) {
            throw new IllegalArgumentException("Refill cannot be null");
        }

        this.refill = refill;
        System.out.println("Pen refilled successfully");
    }

    private String getInkColor() {
        if (type == PenType.FOUNTAIN) {
            return ink.getColor();
        }

        return refill.getInk().getColor();
    }
}