package gg.aegis.util.math;

import lombok.experimental.UtilityClass;

import java.util.*;

@UtilityClass
public class MathUtil {

    public static final float DEGREES_TO_RADIANS = 0.017453292F;
    public static final double UNCERTAIN_VALUE = 0.03D;

    public double getAverage(Collection<Number> values) {
        if (values == null || values.isEmpty()) {
            return 0.0D;
        }

        double sum = 0.0D;

        for (Number v : values) {
            sum += v.doubleValue();
        }

        return sum / values.size();
    }

    /**
     * LOW = high consistency (very sus amongus)
     */
    public double getVariance(Collection<Number> values) {
        if (values == null || values.size() < 2) {
            return 0.0D;
        }

        double mean = getAverage(values);
        double temp = 0.0D;

        for (Number v : values) {
            double diff = v.doubleValue() - mean;

            temp += diff * diff;
        }

        return temp / values.size();
    }

    public double getDeviation(Collection<Number> values) {
        return Math.sqrt(getVariance(values));
    }

    public double getDeviationFast(Collection<Number> values) {
        return MinecraftMath.sqrt_double(getVariance(values));
    }

    public double getDistinctRatio(Collection<Number> values) {
        if (values == null || values.isEmpty()) {
            return 0.0;
        }

        long distinct = values.stream()
                .map(v -> round(v.doubleValue(), 5))
                .distinct()
                .count();

        return (double) distinct / values.size();
    }

    public int getRepeatedValueCount(Collection<Number> values) {
        if (values == null || values.isEmpty()) {
            return 0;
        }

        Map<Double, Integer> map = new HashMap<>();

        for (Number number : values) {
            double rounded = round(number.doubleValue(), 5);

            map.put(rounded, map.getOrDefault(rounded, 0) + 1);
        }

        int repeated = 0;

        for (int count : map.values()) {
            if (count > 1) {
                repeated++;
            }
        }

        return repeated;
    }

    public double getAverageDelta(List<Number> values) {
        if (values == null || values.size() < 2) {
            return 0.0D;
        }

        double total = 0.0D;

        for (int i = 1; i < values.size(); i++) {
            total += Math.abs(values.get(i).doubleValue() - values.get(i - 1).doubleValue());
        }

        return total / (values.size() - 1);
    }

    /**
     * Rounds a double to prevent precision abuse.
     */
    public double round(double value, int places) {
        if (places < 0) {
            return value;
        }

        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

}