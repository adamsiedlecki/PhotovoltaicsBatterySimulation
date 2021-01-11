package tools;

import pojo.BatteryState;

import java.util.List;

public class ChartCreator {

    public void createChart(List<BatteryState> batteryStates) {
        LineChart lineChart = new LineChart(batteryStates);
        lineChart.setVisible(true);
    }


}
