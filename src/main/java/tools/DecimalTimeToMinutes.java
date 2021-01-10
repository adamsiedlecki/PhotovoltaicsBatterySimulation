package tools;

public class DecimalTimeToMinutes {

    public String getDecToString(double time){
        int hours = (int) time;
        double fractionPart = time - hours ;
        double minutes = fractionPart*60;
        return (hours+"h "+ minutes+"min");
    }

    public int calcMinutesWithoutHours(double time){
        int hours = (int) time;
        double fractionPart = time - hours ;
        double minutes = fractionPart*60;
        return (int) minutes;
    }
}
