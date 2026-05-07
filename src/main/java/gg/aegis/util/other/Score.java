package gg.aegis.util.other;

import lombok.Getter;
import gg.aegis.util.math.MinecraftMath;

@Getter
public class Score {

    private double value;
    private int min, max;

    public Score(int max) {
        this(0, 0, max);
    }

    public Score() {
        this(0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public Score(int min, int max) {
        this(0, min, max);
    }

    public Score(double value, int min, int max) {
        int trueMin = Math.min(min, max);
        int trueMax = Math.max(min, max);

        this.setBounds(trueMin, trueMax);
        this.set(value);
    }

    public Score setBounds(int min, int max) {
        this.min = min;
        this.max = max;
        this.set(this.value);
        return this;
    }

    public Score set(double newValue) {
        this.value = MinecraftMath.clamp_double(newValue, this.min, this.max);
        return this;
    }

    public Score decline(double amount) {
        this.set(this.value - amount);
        return this;
    }

    public Score rise(double amount) {
        this.set(this.value + amount);
        return this;
    }

    public void reset() {
        this.value = 0;
    }

    public boolean isMaximum() {
        return this.value >= this.max;
    }

    public boolean isMinimum() {
        return this.value <= this.min;
    }

    public boolean isAbove(double amount) {
        return this.value >= amount;
    }

    public boolean isBelow(double amount) {
        return this.value < amount;
    }

    public boolean isAboveOrEqual(double amount) {
        return this.value >= amount;
    }

    public boolean isBelowOrEqual(double amount) {
        return this.value <= amount;
    }

}
