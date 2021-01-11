import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.HardwareParams;
import simulation.Simulation;

public class SimulationMain {

    private static final Logger log = LoggerFactory.getLogger(SimulationMain.class);

    public static void main(String[] args) {
        log.info("Starting app");

        Simulation simulation = new Simulation();
        simulation.getNumberOfDaysOnBatteryStartingBy(2021, 2, 13, new HardwareParams(), 1, 52, 21);

    }


}
