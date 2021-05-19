package pojo;

public class HardwareParams {

    private final double batteryCapacityAh; // Ah
    private final double batteryVoltage;
    private final double batteryWh;
    private final double powerConsumptionWatts; // W
    private final double panelsPowerProductionWatts; // W
    private final double batteryChargingPower;

    // my default values
    public HardwareParams() {
        this.batteryCapacityAh = 600;
        this.batteryVoltage = 24;
        this.batteryWh = batteryCapacityAh * batteryVoltage;
        this.powerConsumptionWatts = 600;
        this.panelsPowerProductionWatts = 1400;
        this.batteryChargingPower = panelsPowerProductionWatts - powerConsumptionWatts;
    }

    public HardwareParams(double batteryCapacityAh, double batteryVoltage, double powerConsumptionWatts, double panelsPowerProductionWatts) {
        this.batteryCapacityAh = batteryCapacityAh;
        this.batteryVoltage = batteryVoltage;
        this.batteryWh = batteryCapacityAh * batteryVoltage;
        this.powerConsumptionWatts = powerConsumptionWatts;
        this.panelsPowerProductionWatts = panelsPowerProductionWatts;
        this.batteryChargingPower = panelsPowerProductionWatts - powerConsumptionWatts;
    }

    public double getBatteryCapacityAh() {
        return batteryCapacityAh;
    }

    public double getBatteryVoltage() {
        return batteryVoltage;
    }

    public double getBatteryWh() {
        return batteryWh;
    }

    public double getPowerConsumptionWatts() {
        return powerConsumptionWatts;
    }

    public double getPanelsPowerProductionWatts() {
        return panelsPowerProductionWatts;
    }

    public double getBatteryChargingPower() {
        return batteryChargingPower;
    }


    public static final class HardwareParamsBuilder {
        private double batteryCapacityAh; // Ah
        private double batteryVoltage;
        private double powerConsumptionWatts; // W
        private double panelsPowerProductionWatts; // W

        private HardwareParamsBuilder() {
        }

        public static HardwareParamsBuilder aHardwareParams() {
            return new HardwareParamsBuilder();
        }

        public HardwareParamsBuilder withBatteryCapacityAh(double batteryCapacityAh) {
            this.batteryCapacityAh = batteryCapacityAh;
            return this;
        }

        public HardwareParamsBuilder withBatteryVoltage(double batteryVoltage) {
            this.batteryVoltage = batteryVoltage;
            return this;
        }

        public HardwareParamsBuilder withPowerConsumptionWatts(double powerConsumptionWatts) {
            this.powerConsumptionWatts = powerConsumptionWatts;
            return this;
        }

        public HardwareParamsBuilder withPanelsPowerProductionWatts(double panelsPowerProductionWatts) {
            this.panelsPowerProductionWatts = panelsPowerProductionWatts;
            return this;
        }

        public HardwareParams build() {
            return new HardwareParams(this.batteryCapacityAh, this.batteryVoltage, this.powerConsumptionWatts, this.panelsPowerProductionWatts);
        }
    }
}
