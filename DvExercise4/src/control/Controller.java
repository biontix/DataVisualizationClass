package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JCheckBox;

import org.jfree.chart.ChartPanel;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;

import model.Model;
import view.MainView;

public class Controller {

    public Model model;
    public MainView lineChartA;

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
     *  line charts A
     */
    public void makeRegionSelectButtons(){
    	
        lineChartA.regionSelect.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    lineChartA.currentRegSelection = new ArrayList();
                    for (JCheckBox checkBox : lineChartA.boxes) {
                        if (checkBox.isSelected()){
                            lineChartA.currentRegSelection.add(checkBox.getText());
                        }
                    }
                    model.selectRegionsA(lineChartA.currentRegSelection);
                    lineChartA.chartPanel.repaint();
                }
        });        

     }

     /**
     *  A method for intializing the date selection buttons
     */
    public void makeDateSelectButtons(){
    	
      lineChartA.dateSelect.addMouseListener( new MouseAdapter(){
                public void mouseClicked( MouseEvent e )
                {
                    int a = Integer.parseInt(lineChartA.start.getSelectedItem().toString());
                    int b = Integer.parseInt(lineChartA.end.getSelectedItem().toString());
                    lineChartA.errorLabel.setText("");
                    if(a < b){
                        a -= 1980;
                        b -= 1980;
                        model.selectDateA(a, b);
                        lineChartA.chartPanel.repaint();
                    }
                    else
                        lineChartA.errorLabel.setText("Please select a valid interval");
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
                        lineChartA.f.setVisible(true);
                    }
                    
                }
        });
     }

}

