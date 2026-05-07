package gg.aegis.movement.data;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MovementBounds {

    public double minX = 100, maxX = -100, minY = 100, maxY = -100, minZ = 100, maxZ = -100;

    public void forceSetX(double val) {
        this.minX = val;
        this.maxX = val;
    }

    public void forceSetY(double val) {
        this.minY = val;
        this.maxY = val;
    }

    public void forceSetZ(double val) {
        this.minZ = val;
        this.maxZ = val;
    }

    public void setMinX(double val) {
        this.minX = Math.min(minX, val);
        this.resolveX();
    }

    public void setMaxX(double val) {
        this.maxZ = Math.max(maxX, val);
        this.resolveX();
    }

    public void setMinY(double val) {
        this.minY = Math.min(minY, val);
        this.resolveY();
    }

    public void setMaxY(double val) {
        this.maxY = Math.max(maxY, val);
        this.resolveY();
    }

    public void setMinZ(double val) {
        this.minZ = Math.min(minY, val);
        this.resolveZ();
    }

    public void setMaxZ(double val) {
        this.maxZ = Math.max(maxZ, val);
        this.resolveZ();
    }

    public void forceSetMinX(double val) {
        this.minX = val;
        this.resolveX();
    }

    public void forceSetMaxX(double val) {
        this.maxX = val;
        this.resolveX();
    }

    public void forceSetMinY(double val) {
        this.minY = val;
        this.resolveY();
    }

    public void forceSetMaxY(double val) {
        this.maxY = val;
        this.resolveY();
    }

    public void forceSetMinZ(double val) {
        this.minZ = val;
        this.resolveZ();
    }

    public void forceSetMaxZ(double val) {
        this.maxZ = val;
        this.resolveZ();
    }

    public void expandX(double amount) {
        this.minX -= amount;
        this.maxX += amount;
    }

    public void expandY(double amount) {
        this.minY -= amount;
        this.maxY += amount;
    }

    public void expandZ(double amount) {
        this.minZ -= amount;
        this.maxZ += amount;
    }

    private void resolveX() {
        if(minX > maxX) {
            double temp = minX;
            this.minX = maxX;
            this.maxX = temp;
        }
    }

    private void resolveY() {
        if(minY > maxY) {
            double temp = minY;
            this.minY = maxY;
            this.maxY = temp;
        }
    }

    private void resolveZ() {
        if(minZ > maxZ) {
            double temp = minZ;
            this.minZ = maxZ;
            this.maxZ = temp;
        }
    }

}
