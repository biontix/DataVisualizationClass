package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;

public class LineView extends MainView{

    private static final String title = "Renewable Electricity Generation";

    /**
     *  A Constructor for objects of class LineViewA. Intializes the GUI
     *  line Chart A.
     */
    public LineView(XYDataset dataset) {
    	
        JFreeChart chart = ChartFactory.createTimeSeriesChart(title, // Chart
                "Year", // X-Axis Label
                "kWh (million)", // Y-Axis Label
                dataset);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(255, 228, 196));
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 480));
        chartPanel.setDisplayToolTips( true );

        f.add(chartPanel,  BorderLayout.CENTER);
        f.pack();
        f.setTitle(title);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

}
