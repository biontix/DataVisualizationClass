package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.DefaultXYZDataset;

public class Model {

    HashMap map;
    HashMap reducedMap;
    public TimeSeriesCollection data;
    public DefaultCategoryDataset barData = new DefaultCategoryDataset();
	public DefaultPieDataset pieData = new DefaultPieDataset();
	public DefaultXYZDataset heatmapData = new DefaultXYZDataset(); 
	
    final ArrayList<String> cacheData = new ArrayList();
    final ArrayList<String> countries = new ArrayList();
    public String[] years;
    
    
    /**
     * Constructor for objects of class Model. Initializes the Dashboard's 
     * opening chart, Line Chart A.
     */
    public Model() throws IOException{

        countries.add("China");
        countries.add("Africa");
        countries.add("India");
        countries.add("North America");
        countries.add("Central & South America");
        countries.add("Europe");
        countries.add("United States"); 

        map = new HashMap<String, TimeSeries>();
        for( String c : countries){
            map.put( c, new TimeSeries(c));
        }

        data = new TimeSeriesCollection();
        BufferedReader reader = new BufferedReader(new FileReader("total-renewable-electricity-generation.csv"));

        String row = reader.readLine();
        years = row.split(",");
        
        while ((row = reader.readLine()) != null){
        	
            String[] data = row.split(",");
            cacheData.add(row); // save selected data in cache
            
			//create dataset            
			if(map.keySet().contains(data[0])){
			    TimeSeries tm = (TimeSeries)map.get(data[0]);			    
			    for(int i=0; i < years.length ; i++) {
				    tm.add(new Day(1, 1 , Integer.parseInt(years[i])), Double.parseDouble(data[i+1]));				    
			    }
			}              
            
        }    

        for (Object tm : map.values()){
            data.addSeries((TimeSeries) tm);
        }

        reducedMap = (HashMap)map.clone();
    }

    

    /**
     * A method for selecting the regions to display in LineChart.
     */
    public void selectRegions(ArrayList<String> regions){
        data.removeAllSeries();
        if (regions.contains("All")){
            for (Object tm : map.values()){
                data.addSeries((TimeSeries) tm);
            }
            reducedMap = (HashMap)map.clone();
        }
        else{
            reducedMap.clear();
            for(String reg : regions){
                TimeSeries tm = (TimeSeries) map.get(reg);
                data.addSeries(tm);
                reducedMap.put(reg, tm);
            }
        }
    }    
    
    /**
     * A method for selecting a date interval in LineChart.
     */
    public void selectDate(int start, int end){
        data.removeAllSeries();
        for (Object tm : reducedMap.values()){
            TimeSeries ts= (TimeSeries) tm;

            try{
                ts = ts.createCopy(start, end);
            }catch(Exception e){
                System.out.println("Selection error!");
            }
            data.addSeries(ts);
        }
    }
    
    /**
     * A method for intializing the bar chart 
     */
    public void barData(int start, int end){
    	
        //avg growth rate over time= (present/past)^(1/N) - 1
        barData.clear();
        double temp = 0;

        for(String region : countries){
            int N = 0;
            
            //for each row
            for(int i = 0; i < cacheData.size(); i++){
            	
                String[] row_local = cacheData.get(i).split(",");
                double energy = 0;
                
                //% of one row
                for(int j = 0; j < years.length; j++) {
                	
                	try{
                		energy = Double.parseDouble(row_local[j+1]);
                    }catch(Exception e){
                        //System.out.println("Wrong number format: " + row_local[j+1].toString());
                    }

                    int year = Integer.parseInt(years[j]);

                    if(energy != 0 && year == start && region.equals(row_local[0]))
                        temp = energy;
                    
                    else if(energy != 0 && year == end && region.equals(row_local[0]))
                        temp = energy/temp;
                    
                    else if(energy != 0 && year > start && year < end && region.equals(row_local[0]))
                        N++;
                    
                }                
                            
            }

            String interval = "" + start + "-" + end;
            double n = 1.0/N;
            double percentage = (Math.pow(temp, n) - 1);
            
            barData.addValue(percentage*100, region, interval);
            N = 1;
        }
    }

    /**
     * A method for intializing the pie chart
    */
    public void pieData(int year){
    	
        for(int i = 0; i < cacheData.size(); i++){
        	
        	String[] row_local = cacheData.get(i).split(","); 
        	double electricityGeneration = 0; // electricity generation
        	
        	//row cursor
            for(int j = 0; j < years.length; j++) {	        	           
	            
	            try{
	            	electricityGeneration = Double.parseDouble(row_local[j+1]);
	            }catch(Exception e){
	                
	            }
	
	            int yr = Integer.parseInt(years[j]);
	            
	            if( yr == year && countries.contains(row_local[0])){
	                pieData.setValue(row_local[0], electricityGeneration);
	            }
            }
        }

    }
    
    /**
     * A method for intializing the Heatmap chart 
     */
    public void heatMapData(int start, int end){
    	
    
    	double[] xvalues = new double[countries.size()*(end-start+1)]; // date
        double[] yvalues = new double[countries.size()*(end-start+1)]; // numeric (1-7)
        double[] zvalues = new double[countries.size()*(end-start+1)]; // energy
        int N = 0;
        int RegionCount = -1;
        
        for(String region : countries){
        	
        	RegionCount++;
        	
            //for each row
            for(int i = 0; i < cacheData.size(); i++){
            	
                String[] row_local = cacheData.get(i).split(",");
                double progression = 0;
                
                //% of one row
                for(int j = 0; j < years.length; j++) {
                	
                	try{
                		progression = Double.parseDouble(row_local[j+2]) - Double.parseDouble(row_local[j+1]);
                    }catch(Exception e){
                        //System.out.println("Wrong number format: " + row_local[j+1].toString());
                    }

                    int year = Integer.parseInt(years[j]);

                    if(progression != 0 && year >= start && year <= end && region.equals(row_local[0])) {
                    	
        	        	xvalues[N] = year; 
    	        		yvalues[N] = RegionCount;
    	        		zvalues[N] = progression;
    	        		N++;  
                    }
                }                
                            
            }                 
        }  
        
        heatmapData.addSeries("Just one Series", new double[][] {xvalues, yvalues, zvalues,});  
    }
}
    
