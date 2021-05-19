package simulation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.BatteryState;
import pojo.HardwareParams;
import tools.CalcSunriseSunset;
import tools.ChartCreator;
import tools.DecimalTimeToMinutes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private final Logger log = LoggerFactory.getLogger(Simulation.class);
    private final DecimalTimeToMinutes timeToMinutes;
    private final DateTimeFormatter formatter;
    private final List<BatteryState> batteryHistory;

    public Simulation() {
        this.timeToMinutes = new DecimalTimeToMinutes();
        this.formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        batteryHistory = new ArrayList<>();
    }

    public int getNumberOfDaysOnBatteryStartingBy(int year, int month, int day, HardwareParams hp, int timezone, double latitude, double longitude) {
        CalcSunriseSunset calcSunriseSunset = new CalcSunriseSunset(timezone, latitude, longitude);
        double sun = calcSunriseSunset.getSunrise(year, month, day);
        LocalDateTime startTime = LocalDateTime.of(year, month, day, (int) sun, timeToMinutes.calcMinutesWithoutHours(sun));
        LocalDateTime endTime = LocalDateTime.from(startTime);

        log.info("Starting simulation with full battery on " + formatter.format(startTime));

        int dayCounter = 0;
        int hourCounter = 0;
        double batteryWh = hp.getBatteryWh();
        double batteryChargingPower = hp.getBatteryChargingPower();
        double minerPowerConsumption = hp.getPowerConsumptionWatts();

        while (batteryWh > 0) {
            double darkSeconds = calcDarkSeconds(calcSunriseSunset, endTime);
            double lightSeconds = calcLightSeconds(calcSunriseSunset, endTime);

            // minutes of day
            while (lightSeconds > 0) {
                batteryWh = batteryWh + (1 / 60d / 60d) * batteryChargingPower;
                if (batteryWh > hp.getBatteryWh()) {
                    batteryWh = hp.getBatteryWh(); // battery cannot be more than 100% full
                }
                lightSeconds--;
                endTime = endTime.plusSeconds(1);
                if (((long) lightSeconds % (60 * 60)) == 0) {
                    hourCounter++;
                    batteryHistory.add(new BatteryState(hourCounter, batteryWh));
                }

            }
            //System.out.println("Sunset: "+sunset+" time: "+formatter.format(endTime));

            // minutes of night
            while (darkSeconds > 0) {
                batteryWh = batteryWh - (1 / 60d / 60d) * minerPowerConsumption;
                if (batteryWh <= 0) {
                    break; // battery is at 0%
                }
                darkSeconds--;
                endTime = endTime.plusSeconds(1);
                if (((long) darkSeconds % (60 * 60)) == 0) {
                    hourCounter++;
                    batteryHistory.add(new BatteryState(hourCounter, batteryWh));
                }

            }

            dayCounter++;
        }

        log.info("Start time: " + formatter.format(startTime) + " | end time: " + formatter.format(endTime));
        log.info("Total days: " + dayCounter);
        ChartCreator chartCreator = new ChartCreator();
        chartCreator.createChart(batteryHistory, formatter.format(startTime), hp);
        return dayCounter;
    }

    private double calcDarkSeconds(CalcSunriseSunset calcSunriseSunset, LocalDateTime endTime) {
        double sunset = calcSunriseSunset.getSunset(endTime.getYear(), endTime.getMonthValue(), endTime.getDayOfMonth());

        endTime = endTime.plusDays(1);
        double nextSunrise = calcSunriseSunset.getSunrise(endTime.getYear(), endTime.getMonthValue(), endTime.getDayOfMonth());
        endTime = endTime.minusDays(1);

        return ((24d - sunset + nextSunrise) * 60 * 60);
    }

    private double calcLightSeconds(CalcSunriseSunset calcSunriseSunset, LocalDateTime endTime) {
        double sunrise = calcSunriseSunset.getSunrise(endTime.getYear(), endTime.getMonthValue(), endTime.getDayOfMonth());
        double sunset = calcSunriseSunset.getSunset(endTime.getYear(), endTime.getMonthValue(), endTime.getDayOfMonth());

        double timeOfTheLight = sunset - sunrise;
        return (timeOfTheLight * 60 * 60);
    }

}
