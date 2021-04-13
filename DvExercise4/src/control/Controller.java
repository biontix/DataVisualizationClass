package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JCheckBox;

import org.jfree.chart.ChartPanel;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;

import model.Model;
import view.MainView;

public class Controller {

    public Model model;
    public MainView lineChart;
    public MainView barChart;
    public MainView pieChart;
    public MainView heatMapChart;

    /**
     *  A Constructor for objects of class Controller
     */
    public Controller(){
        try{
            model = new Model();
        }catch(Exception io){
        	System.out.println("model created successfully");
        }
    }
    

    /**
     *  A method for intializing the region select buttons of 
     *  line charts 
     */
    public void makeRegionSelectButtons(){
    	
		lineChart.regionSelect.addActionListener(new ActionListener(){
		        public void actionPerformed(ActionEvent e) {
		            lineChart.currentRegSelection = new ArrayList();
		            for (JCheckBox checkBox : lineChart.boxes) {
		                if (checkBox.isSelected()){
		                    lineChart.currentRegSelection.add(checkBox.getText());
		                }
		            }
		            model.selectRegions(lineChart.currentRegSelection);
		            lineChart.chartPanel.repaint();
		        }
		});        
     }

     /**
     *  A method for intializing the date selection buttons
     */
    public void makeDateSelectButtons(){
    	
    	lineChart.dateSelect.addMouseListener( new MouseAdapter(){
                public void mouseClicked( MouseEvent e )
                {
                    int a = Integer.parseInt(lineChart.start.getSelectedItem().toString());
                    int b = Integer.parseInt(lineChart.end.getSelectedItem().toString());
                    lineChart.errorLabel.setText("");
                    if(a < b){
                        a -= 1980;
                        b -= 1980;
                        model.selectDate(a, b);
                        lineChart.chartPanel.repaint();
                    }
                    else
                        lineChart.errorLabel.setText("Please select a valid interval");
                     }
        });
      
      	pieChart.dateSelect.addMouseListener( new MouseAdapter(){
      			public void mouseClicked( MouseEvent e ){
      				
		  			int a = Integer.parseInt(pieChart.start.getSelectedItem().toString());
		  			model.pieData(a);
		  			pieChart.pieChart.setTitle("Global Renewable El. Generation in " + a);

                }
      	});

      	barChart.dateSelect.addMouseListener( new MouseAdapter(){
      		
      			public void mouseClicked( MouseEvent e ){
      				int a = Integer.parseInt(barChart.start.getSelectedItem().toString());
      				int b = Integer.parseInt(barChart.end.getSelectedItem().toString());;
      				barChart.errorLabel.setText("");
      				if(a < b)
      					model.barData(a, b);
      				else
      					barChart.errorLabel.setText("Please select a valid interval");
      			}
      	});	
      
    }

     /**
     *  A method for intializing the chart select buttons of the
     *  Dashboard
     */
     public void makeChartSelectButton(MainView v){
    	 
        v.viewSelect.addMouseListener( new MouseAdapter(){
        	
                public void mouseClicked( MouseEvent e ){
                	
                    String chart = v.chartList.getSelectedItem().toString();
                    
                    if(chart.equals("Line Chart")){
                        lineChart.f.setVisible(true);
                        barChart.f.hide();
                        pieChart.f.hide();
                        heatMapChart.f.hide();
                        
                    }
                    else if(chart.equals("Bar Chart")){
                        barChart.f.setVisible(true);
                        lineChart.f.hide();
                        pieChart.f.hide(); 
                        heatMapChart.f.hide();
                    }
                    else if(chart.equals("Pie Chart")){
                        pieChart.f.setVisible(true);
                        lineChart.f.hide();
                        barChart.f.hide();
                        heatMapChart.f.hide();
                    } 
                    else if(chart.equals("Heat map")){                    	
                        heatMapChart.f.setVisible(true);
                        pieChart.f.hide();
                        lineChart.f.hide();
                        barChart.f.hide();                        
                    } 
                  
                }
        });
     }

}

