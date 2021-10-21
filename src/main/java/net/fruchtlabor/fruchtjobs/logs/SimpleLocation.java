package net.fruchtlabor.fruchtjobs.logs;

import java.sql.Timestamp;
import java.util.UUID;

public class SimpleLocation {
    private double x;
    private double y;
    private double z;
    private String world;
    private UUID uuid;
    private String material;
    private String rawlocation;
    private Timestamp timestamp;


    public SimpleLocation(double x, double y, double z, String world, UUID uuid, String material, String rawlocation, Timestamp timestamp) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
        this.uuid = uuid;
        this.material = material;
        this.rawlocation = rawlocation;
        this.timestamp = timestamp;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public String getWorld() {
        return world;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getMaterial() {
        return material;
    }

    public String getRawlocation() {
        return rawlocation;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
