import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.HardwareParams;
import simulation.Simulation;

import java.time.LocalDateTime;

public class SimulationMain {

    private static final Logger log = LoggerFactory.getLogger(SimulationMain.class);

    public static void main(String[] args) {
        log.info("Starting app");

        HardwareParams hp = HardwareParams.HardwareParamsBuilder
                .aHardwareParams()
                .withBatteryCapacityAh(6)
                .withBatteryVoltage(3.7)
                .withPowerConsumptionWatts(0.36)
                .withPanelsPowerProductionWatts(0.5)
                .build();

        LocalDateTime now = LocalDateTime.now();

        Simulation simulation = new Simulation();
        simulation.getNumberOfDaysOnBatteryStartingBy(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), hp, 1, 52, 21);

    }


}
