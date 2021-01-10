package pojo;

public class HardwareParams {

    private double batteryCapacity; // Ah
    private double batteryVoltage;
    private double batteryWh;
    private double minerPowerConsumption; // W
    private double panelsPowerProduction; // W
    private double batteryChargingPower;

    // my default values
    public HardwareParams() {
        this.batteryCapacity = 600;
        this.batteryVoltage = 24;
        this.batteryWh = batteryCapacity * batteryVoltage;
        this.minerPowerConsumption = 600;
        this.panelsPowerProduction = 1400;
        this.batteryChargingPower = panelsPowerProduction - minerPowerConsumption;
    }

    public HardwareParams(float batteryCapacity, float batteryVoltage, float minerPowerConsumption, float panelsPowerProduction) {
        this.batteryCapacity = batteryCapacity;
        this.batteryVoltage = batteryVoltage;
        this.batteryWh = batteryCapacity * batteryVoltage;
        this.minerPowerConsumption = minerPowerConsumption;
        this.panelsPowerProduction = panelsPowerProduction;
        this.batteryChargingPower = panelsPowerProduction - minerPowerConsumption;
    }

    public double getBatteryCapacity() {
        return batteryCapacity;
    }

    public double getBatteryVoltage() {
        return batteryVoltage;
    }

    public double getBatteryWh() {
        return batteryWh;
    }

    public double getMinerPowerConsumption() {
        return minerPowerConsumption;
    }

    public double getPanelsPowerProduction() {
        return panelsPowerProduction;
    }

    public double getBatteryChargingPower() {
        return batteryChargingPower;
    }
}
