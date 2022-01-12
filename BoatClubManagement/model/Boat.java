package BoatClubManagement.model;

import java.io.Serializable;

public class Boat implements Serializable {
    private TYPE type;
    private double length;

    public enum TYPE {
        SAILBOAT,
        MOTOR_SAILOR,
        Kayak,
        CANOE,
        OTHER
    }

    public Boat(TYPE type, double length) {
        this.type = type;
        this.length = length;
    }

    public TYPE getType() {
        return type;
    }
    public void setType(TYPE type) {
        this.type = type;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) throws Exception {
        if (length >= 50 || length <= 5) {
            throw new IllegalArgumentException("Invalid Input");
        } else {
            this.length = length;
        }
    }
}
