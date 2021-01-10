package simulation;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.CalcSunriseSunset;
import pojo.HardwareParams;
import tools.DecimalTimeToMinutes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Simulation {

    private final Logger log = LoggerFactory.getLogger(Simulation.class);
    private double sunTimeError;
    private DecimalTimeToMinutes timeToMinutes;
    private DateTimeFormatter formatter;

    public Simulation() {
        this.sunTimeError = 0.5;
        this.timeToMinutes = new DecimalTimeToMinutes();
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    public int getNumberOfDaysOnBatteryStartingBy(int year, int month, int day, HardwareParams hp, int timezone, double latitude, double longitude ){
        CalcSunriseSunset calcSunriseSunset = new CalcSunriseSunset(timezone, latitude, longitude);
        double sunrise = calcSunriseSunset.getSunrise(year, month, day);
        LocalDateTime startTime = LocalDateTime.of(year, month, day,(int)sunrise, timeToMinutes.calcMinutesWithoutHours(sunrise));
        LocalDateTime endTime = LocalDateTime.from(startTime);

        log.info("Starting simulation with full battery on "+formatter.format(startTime));

        int dayCounter = 0;
        double batteryWh = hp.getBatteryWh();
        double batteryChargingPower = hp.getBatteryChargingPower();
        double minerPowerConsumption = hp.getMinerPowerConsumption();

        while(batteryWh>0){
            sunrise = calcSunriseSunset.getSunrise(endTime.getYear(), endTime.getMonthValue(), endTime.getDayOfMonth());
            double sunset = calcSunriseSunset.getSunset(endTime.getYear(), endTime.getMonthValue(), endTime.getDayOfMonth());
            double timeOfTheLight = sunset-sunrise;
            timeOfTheLight -= 2 * sunTimeError; // it is value set by me to include inefficiency of sunlight just after sunrise and before sunset
            double timeOfTheDark = 24-timeOfTheLight;

            System.out.println("Sunrise: "+sunrise+" vs time: "+endTime);
            //System.out.println("light: "+timeOfTheLight+" night:"+timeOfTheDark);
            // hours of day
            batteryWh = batteryWh + timeOfTheLight * batteryChargingPower;

            if(batteryWh>hp.getBatteryWh()){
                batteryWh = hp.getBatteryWh(); // battery cannot be more than 100% full
            }
            // hours of night
            batteryWh = batteryWh - timeOfTheDark * minerPowerConsumption;


            endTime = endTime.plusDays(1);
            dayCounter++;
        }

        log.info("Start time: "+formatter.format(startTime)+" | end time: "+formatter.format(endTime));
        log.info("Total days: "+dayCounter);
        return dayCounter;
    }
}
