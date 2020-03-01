package frc.robot.util;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {

    private final String id;
    private final NetworkTable table;
    private final NetworkTableEntry tv, tx, ty, ta, ts, tl, tshort, tlong, thor, tvert, getpipe, ledMode; 

    public Limelight(String id) {
        this.id = id;
        table = NetworkTableInstance.getDefault().getTable("limelight-" + this.id);
        tv = table.getEntry("tv");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        ts = table.getEntry("ts");
        tl = table.getEntry("tl");
        tshort = table.getEntry("tshort");
        tlong = table.getEntry("tlong");
        thor = table.getEntry("thor");
        tvert = table.getEntry("tvert");
        getpipe = table.getEntry("getpipe");

        ledMode = table.getEntry("ledMode");
    }

    public Limelight() {
        this.id = "";
        table = NetworkTableInstance.getDefault().getTable("limelight");
        tv = table.getEntry("tv");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        ts = table.getEntry("ts");
        tl = table.getEntry("tl");
        tshort = table.getEntry("tshort");
        tlong = table.getEntry("tlong");
        thor = table.getEntry("thor");
        tvert = table.getEntry("tvert");
        getpipe = table.getEntry("getpipe");

        ledMode = table.getEntry("ledMode");
    }

    public boolean getValidTarget() {
        if(tv.getDouble(0.0) == 1.0) {
            return true;
        }
        return false;
    }
    public double getX() {
        return tx.getDouble(0.0);
    }
    public double getY() {
        return ty.getDouble(0.0);
    }
    public double getArea() {
        return ta.getDouble(0.0);   
    }
    public double getSkew() {
        return ts.getDouble(0.0);
    }
    public double getLatency() {
        return tl.getDouble(0.0);
    }
    public double getShort() {
        return tshort.getDouble(0.0);
    }
    public double getLong() {
        return tlong.getDouble(0.0);
    }
    public double getHorizontal() {
        return thor.getDouble(0.0);
    }
    public double getVertical() {
        return tvert.getDouble(0.0);
    }
    public double getPipeline() {
        return getpipe.getDouble(0.0);
    }

    public void setLED(boolean on) {
        ledMode.setNumber(on ? 3 : 1);
    }
}

