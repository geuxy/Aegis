package gg.aegis.util.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class Vector3 {

    private double x, y, z;

    public void add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void multiply(double amount) {
        this.multiply(amount, amount, amount);
    }

    public void multiply(double x, double y, double z) {
        this.multiplyX(x);
        this.multiplyY(y);
        this.multiplyZ(z);
    }

    public void epsilon(double epsilon) {
        if(x < epsilon) this.x = 0;
        if(y < epsilon) this.y = 0;
        if(z < epsilon) this.z = 0;
    }

    public void addX(double amount) {
        this.x += amount;
    }

    public void addY(double amount) {
        this.y += amount;
    }

    public void addZ(double amount) {
        this.z += amount;
    }

    public void multiplyX(double multiplier) {
        this.x *= multiplier;
    }

    public void multiplyY(double multiplier) {
        this.y *= multiplier;
    }

    public void multiplyZ(double multiplier) {
        this.z *= multiplier;
    }

}
