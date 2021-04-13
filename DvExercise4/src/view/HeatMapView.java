package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.LookupPaintScale;
import org.jfree.chart.renderer.xy.XYBlockRenderer;
import org.jfree.chart.title.PaintScaleLegend;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYZDataset;


public class HeatMapView extends MainView{
	
	public JFreeChart chart;
	private static final String title = "Progression of Renewable energy generation over a Time Interval";
	
    public HeatMapView(DefaultXYZDataset heatmapData){    	

        chart = createChart(heatmapData); 
        
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 480));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);

        dateSelectPanel.remove(end);
        dateSelectPanel.remove(start);
        dateSelectPanel.remove(dateSelect);
        dateSelectPanel.remove(startLabel);
        dateSelectPanel.remove(endLabel);
        
        JLabel label1 = new JLabel("<html>Description:<br/> This graph represents the increase "
        		+ "(or decrease) <br/> of generation of renewable energy <br/>"
        		+ "between an year and the previous one. <br/>"
        		+ "Color scale is in million KWh</html>", SwingConstants.CENTER);
         
        dateSelectPanel.add(label1);
        
        zoomPanel.remove(zoomIn);
        zoomPanel.remove(zoomOut);
        zoomPanel.remove(zoom);
        f.remove(menuPanel);
        
        f.setTitle(title);
        f.add(chartPanel,  BorderLayout.CENTER);
        f.pack();
        f.setLocationRelativeTo(null);
        
    }

    private static JFreeChart createChart(XYZDataset dataset)
    {
        // x-axis for time
        NumberAxis xAxis = new NumberAxis("Year");
        xAxis.setRange(1980,2009);
        //xAxis.setDateFormatOverride(new SimpleDateFormat("yyyy")); 
        //xAxis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());
        xAxis.setLowerMargin(0);
        xAxis.setUpperMargin(0);

        // visible y-axis with symbols
        String[] regions = new String[] { 
        		"China",
        		"Africa",
        		"India",
        		"North America",
        		"Central & South America",
        		"Europe",
        		"United States"
                };
        SymbolAxis yAxis = new SymbolAxis("Region", regions);
        yAxis.setRange(-0.5,6.5);
        yAxis.setTickUnit(new NumberTickUnit(1));


        // another invisible y-axis for scaling
        // (this is not necessary if your y-values are suitable) 
        NumberAxis valueAxis1 = new NumberAxis("Marker");        
        valueAxis1.setRange(-100, 120);
        valueAxis1.setLowerMargin(0);
        valueAxis1.setUpperMargin(0);
        valueAxis1.setVisible(false);
        
        // create a paint-scale and a legend showing it
        LookupPaintScale paintScale = new LookupPaintScale(-75, 120, Color.black);
        Color c = Color.green;
        paintScale.add(-75.0, c);
        paintScale.add(-50.0, c = c.darker());
        paintScale.add(-25.0, c.darker());
        paintScale.add(0.0, c = Color.blue);
        paintScale.add(25.0, c = c.darker());
        paintScale.add(50.0, c.darker());
        paintScale.add(75.0, c = Color.red.darker().darker());
        paintScale.add(100.0, c = c.brighter());
        paintScale.add(125.0, c.brighter());

        PaintScaleLegend psl = new PaintScaleLegend(paintScale, new NumberAxis());
        psl.setPosition(RectangleEdge.RIGHT);
        psl.setAxisLocation(AxisLocation.TOP_OR_RIGHT);
        psl.setMargin(50.0, 20.0, 80.0, 0.0);

        // finally a renderer and a plot
        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, new XYBlockRenderer());
        ((XYBlockRenderer)plot.getRenderer()).setPaintScale(paintScale);

        JFreeChart chart = new JFreeChart(null, null, plot, false);
        chart.addSubtitle(psl);
        return chart;
    }

}
