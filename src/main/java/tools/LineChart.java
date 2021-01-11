package tools;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import pojo.BatteryState;
import pojo.HardwareParams;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// sources: http://zetcode.com/java/jfreechart/
public class LineChart extends JFrame {

    private final List<BatteryState> batteryStateList;
    private final String startDate;
    private final HardwareParams hardwareParams;

    public LineChart(List<BatteryState> batteryStateList, String startDate, HardwareParams hardwareParams) {
        this.batteryStateList = batteryStateList;
        this.startDate = startDate;
        this.hardwareParams = hardwareParams;
        initUI();
    }

    private void initUI() {

        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Battery state chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private XYDataset createDataset() {

        XYSeries series = new XYSeries("Battery state");
        for (BatteryState bs : batteryStateList) {
            series.add(bs.getHourOfWork(), bs.getBatteryWh());
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Battery state chart starting by " + startDate + ", power consumption: " + hardwareParams.getMinerPowerConsumption()
                        + "W, panels power prod: " + hardwareParams.getPanelsPowerProduction() + "W",
                "Hour of work",
                "State (Wh) - max is " + hardwareParams.getBatteryWh() + "Wh",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        return chart;
    }
}
