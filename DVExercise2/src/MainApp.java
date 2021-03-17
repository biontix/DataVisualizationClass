import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;


public class MainApp  extends ApplicationFrame {

    public MainApp( String applicationTitle , String chartTitle, CategoryDataset catData ) {
		  super( applicationTitle );        
		  JFreeChart barChart = ChartFactory.createBarChart(
									chartTitle,           
									"Category",            
									"Score",            
									catData,          
									PlotOrientation.VERTICAL,           
									true, true, false);
		     
		  ChartPanel chartPanel = new ChartPanel( barChart );        
		  chartPanel.setPreferredSize(new Dimension( 560 , 367 ) );        
		  setContentPane( chartPanel ); 
    }

	public static void main(String[] args) {
		
		// 'br' va dichiarata fuori altrimenti il finally non la vede
		String csvFile = "CC.csv";
		BufferedReader br = null;                                     
		String line = "";
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );    
		
		// prova ad aprire il Buffer, e controlla che ad ogni lettura 
		// il file è ancora correttamente aperto
		try {
			
			br = new BufferedReader(new FileReader(csvFile));				
			while ((line = br.readLine()) != null) {	
				
				// controlla eventuali errori nei campi del csv
				try {
			
			        // usa virgola e spazio come separatore
			        String[] AziendaCorrente = line.split(",");	
			        
			        //carica dataset
			        dataset.addValue( Float.parseFloat(AziendaCorrente[2]),
			        				  AziendaCorrente[0], 
			        				  AziendaCorrente[1]);    
	
			        
				} catch(ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
				} catch(NumberFormatException e) {
					e.printStackTrace();
				} 
				
			} // fine del while
			
			// se il Buffer è ancora aperto		    
	        try {
				br.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        MainApp chart = new MainApp(
	        							"Car Usage Statistics", 
	                					"Which car do you like?",
	        							dataset);
	        chart.pack( );               
	        chart.setVisible( true ); 
	        
			
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}finally {
			System.out.println("Terminated");
		}
		    
	}
}
