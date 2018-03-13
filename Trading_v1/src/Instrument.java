import java.net.*;
import org.json.*;
import java.io.*;
import java.util.Scanner;
public class Instrument {
	//Lesson Learned: Cannot return constants using accessors
	private final String baseUrl = "https://www.alphavantage.co/query?function=";
	private static final String DELIM = ",";
	private final String APIKey = "OM2N5Q91WJ8MLF51"; 
	
	private double price;
	private int volume;
	private String symbol;
	private double return1YR;
	private double return5YR;
	private double return10YR;
	
	//accessors and mutators 
	public String getAPIKey() {return this.APIKey;}
	public String getBaseURL() {return this.baseUrl;}
	public double getPrice(){ return this.price; }
	public int getVolume(){ return this.volume; }
	public String getSymbol(){ return this.symbol;}
	public double getReturn1YR(){return this.return1YR;}
	public double getReturn5YR(){return this.return5YR;}
	public double getReturn10YR(){return this.return10YR;}
	public void setReturn1YR(double aReturn1YR) 
	{
		
	}
	public void setReturn5YR(double aReturn5YR)
	{
		
	}
	public void setReturn10YR(double aReturn10YR)
	{ 
		
	}
	public void setSymbol(String aSymbol)
	{
		if(aSymbol!=null)
		{
			this.symbol = aSymbol;
		}
	}
	public void setPrice(double aPrice)
	{
		if(this.price>=0)
		{
			this.price = aPrice;
		}
	}
	//a single set values method would probably be more efficient...
	//I wouldn't have to set up the URL and Scanner every time I wanted to set a value
	//I would lose the ability to update values independently... but why would I ever want to do that? 
	public void setValues()
	{
		//need a new URL for each function I want to call since each function is mapped to a particular URL
		//to increase speed, make a local database (file) for what you'd get from the api usually
		//update it by the hour. If minute != 00, get it from the database
		//figure out how to make the program update automatically at the start of each hour
		/*try
		{
			URL intraDaySeries = new URL(baseUrl+"TIME_SERIES_INTRADAY&symbol="+this.getSymbol()+"&interval=60min&apikey="+this.APIKey+"&datatype=csv");
			Scanner intraDaySeriesStream = new Scanner(new InputStreamReader(intraDaySeries.openStream()));
			intraDaySeriesStream.nextLine();
			String[] splitStrings = intraDaySeriesStream.nextLine().split(DELIM);
			if(splitStrings.length == 6)
			{
				this.price = Double.parseDouble(splitStrings[2]);
				this.volume = Integer.parseInt(splitStrings[5]);
			}
			intraDaySeriesStream.close();
		}
		catch(Exception e)
		{
			//System.out.println(e);
		}*/
		
	}
	//Constructors
	public Instrument()
	{
		//Make sure all of your Instruments have been initialized like you thought they were
		this.setSymbol("aaaa");//FIXED the code only works for aapl because aapl is hard coded here. Need to figure out how to have mutator set symbol and accessor retrieve symbol and concatenate with the URL.
		this.setValues();
	}
	//lesson learned: can't declare a subclass constructor without declaring a superclass constructor of the same signature
	public Instrument(String aSymbol)
	{
		this.setSymbol(aSymbol);
		this.setValues();
	}
	public String toString()
	{
		/*returns 7 lines, in format:
		 * Symbol: 
		 * Name: ABCD
		 * Price: 0.0
		 * Volume: 0
		 * 1YR Return: 0.0
		 * 5YR Return: 0.0
		 * 10YR Return: 0.0
		 */
		return "Symbol: "+this.getSymbol()+"\nPrice: " +this.getPrice()+ "\nVolume: "+ this.getVolume();
	}
}
