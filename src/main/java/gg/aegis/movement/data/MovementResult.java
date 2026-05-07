package gg.aegis.movement.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter @RequiredArgsConstructor
public class MovementResult {

    private final MovementBounds bounds;
    private final List<String> tags;

    public boolean isWithinBounds(double x, double y, double z) {
        double maxAllowedDifference = 1E-4;

        return y >= this.bounds.getMinY() - maxAllowedDifference
                && y <= this.bounds.getMaxY() + maxAllowedDifference;
    }

}
