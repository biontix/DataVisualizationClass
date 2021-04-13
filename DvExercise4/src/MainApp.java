import org.jfree.chart.ChartPanel;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;

import control.Controller;
import view.BarView;
import view.LineView;
import view.MainView;
import view.PieView;
import view.HeatMapView;

public class MainApp {
	
    /**
     *  A Main method that lauches the Dashboard and intilializes the
     *  Dashboard's interactive buttons
    */
	public static void main(String[] args) {
		
		MainView mv = new MainView();      
 
		// Create chart with dataset
        Controller c = new Controller();
        c.lineChart = new LineView(c.model.data);
  
        c.model.barData(1980, 2009);
        c.barChart = new BarView((CategoryDataset) c.model.barData);

        c.model.pieData(2009);
        c.pieChart = new PieView((PieDataset) c.model.pieData);
        
        c.model.heatMapData(1980, 2009);
        c.heatMapChart = new HeatMapView(c.model.heatmapData);
        
        // make zoom buttons:
        c.lineChart.zoomIn.setActionCommand(ChartPanel.ZOOM_IN_DOMAIN_COMMAND);
        c.lineChart.zoomIn.addActionListener(c.lineChart.chartPanel);
        c.lineChart.zoomOut.setActionCommand(ChartPanel.ZOOM_OUT_DOMAIN_COMMAND);
        c.lineChart.zoomOut.addActionListener(c.lineChart.chartPanel);
        c.lineChart.zoom.setActionCommand(ChartPanel.ZOOM_RESET_DOMAIN_COMMAND);
        c.lineChart.zoom.addActionListener(c.lineChart.chartPanel);
        
        //make select region button:
        c.makeRegionSelectButtons();

        // make date selection buttons:
        c.makeDateSelectButtons();

        // make chart selection buttons:
        c.makeChartSelectButton(c.lineChart);    
        c.makeChartSelectButton(c.barChart);
        c.makeChartSelectButton(c.pieChart);
        c.makeChartSelectButton(c.heatMapChart);

	}

}
