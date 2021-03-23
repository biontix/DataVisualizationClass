package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class Model {

    HashMap mapA;
    HashMap reducedMapA;
    public TimeSeriesCollection dataA;
    final ArrayList<String> cacheData = new ArrayList();
    final ArrayList<String> countries = new ArrayList();

    /**
     * Constructor for objects of class Model. Initializes the Dashboard's 
     * opening chart, Line Chart A.
     */
    public Model() throws IOException
    {

        countries.add("China");
        countries.add("Africa");
        countries.add("India");
        countries.add("North America");
        countries.add("Central & South America");
        countries.add("Europe");
        countries.add("United States"); 

        mapA = new HashMap<String, TimeSeries>();
        for( String c : countries){
            mapA.put( c, new TimeSeries(c));
        }

        dataA = new TimeSeriesCollection();
        BufferedReader reader = new BufferedReader(new FileReader("total-renewable-electricity-generation.csv"));

        String row = reader.readLine();
        String[] year = row.split(",");
        
        while ((row = reader.readLine()) != null){
        	
            String[] data = row.split(",");
            
			//crea dataset da data e inserisci in dataA
            
			if(mapA.keySet().contains(data[0])){
			    TimeSeries tm = (TimeSeries)mapA.get(data[0]);			    
			    for(int i=0; i < year.length ; i++) {
				    tm.add(new Day(1, 1 , Integer.parseInt(year[i])), Double.parseDouble(data[i+1]));
				    cacheData.add(row); // save selected data in cache
			    }
			}              
            
        }    

        for (Object tm : mapA.values()){
            dataA.addSeries((TimeSeries) tm);
        }

        reducedMapA = (HashMap)mapA.clone();
    }

   
    /**
     * A method for selecting a date interval in LineChartA.
     */
    public void selectDateA(int start, int end){
        dataA.removeAllSeries();
        for (Object tm : reducedMapA.values()){
            TimeSeries ts= (TimeSeries) tm;

            try{
                ts = ts.createCopy(start, end);
            }catch(Exception e){
                System.out.println("Selection error!");
            }
            dataA.addSeries(ts);
        }
    }
   

    /**
     * A method for selecting the regions to display in LineChartA.
     * @param regions - the user-selected regions to display
      */
    public void selectRegionsA(ArrayList<String> regions){
        dataA.removeAllSeries();
        if (regions.contains("All")){
            for (Object tm : mapA.values()){
                dataA.addSeries((TimeSeries) tm);
            }
            reducedMapA = (HashMap)mapA.clone();
        }
        else{
            reducedMapA.clear();
            for(String reg : regions){
                TimeSeries tm = (TimeSeries) mapA.get(reg);
                dataA.addSeries(tm);
                reducedMapA.put(reg, tm);
            }
        }
    }    
       
}
