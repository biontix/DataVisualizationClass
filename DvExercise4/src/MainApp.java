import org.jfree.chart.ChartPanel;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;

import control.Controller;
import view.LineView;
import view.MainView;

public class MainApp {
	
    /**
     *  A Main method that lauches the Dashboard and intilializes the
     *  Dashboard's interactive buttons
    */
	public static void main(String[] args) {
		
		MainView mv = new MainView();      
 
        Controller c = new Controller();
        c.lineChartA = new LineView(c.model.dataA);
        
        // make zoom buttons:
        c.lineChartA.zoomIn.setActionCommand(ChartPanel.ZOOM_IN_DOMAIN_COMMAND);
        c.lineChartA.zoomIn.addActionListener(c.lineChartA.chartPanel);
        c.lineChartA.zoomOut.setActionCommand(ChartPanel.ZOOM_OUT_DOMAIN_COMMAND);
        c.lineChartA.zoomOut.addActionListener(c.lineChartA.chartPanel);
        c.lineChartA.zoom.setActionCommand(ChartPanel.ZOOM_RESET_DOMAIN_COMMAND);
        c.lineChartA.zoom.addActionListener(c.lineChartA.chartPanel);
        
        //make select region button:
        c.makeRegionSelectButtons();

        // make date selection buttons:
        c.makeDateSelectButtons();

        // make chart selection buttons:
        c.makeChartSelectButton(c.lineChartA);    

	}

}
