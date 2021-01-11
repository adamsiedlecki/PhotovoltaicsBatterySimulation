package pojo;

public class BatteryState {

    private final long hourOfWork;
    private final double batteryWh;

    public BatteryState(long hourOfWork, double batteryWh) {
        this.hourOfWork = hourOfWork;
        this.batteryWh = batteryWh;
    }

    public long getHourOfWork() {
        return hourOfWork;
    }

    public double getBatteryWh() {
        return batteryWh;
    }
}
