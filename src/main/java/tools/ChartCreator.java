package tools;

import pojo.BatteryState;
import pojo.HardwareParams;

import java.util.List;

public class ChartCreator {

    public void createChart(List<BatteryState> batteryStates, String startDate, HardwareParams hardwareParams) {
        LineChart lineChart = new LineChart(batteryStates, startDate, hardwareParams);
        lineChart.setVisible(true);
    }


}
