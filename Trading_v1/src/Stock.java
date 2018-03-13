import java.net.*;
import java.io.*;
import java.util.Date;
import java.util.Scanner;
import java.text.*;
import org.json.*;
import com.google.gson.*;
public class Stock extends Instrument{
	//a stock is just an instrument with technicals-- Momentum
	private double SMA;//Simple Moving Average
	private double EMA;//Exponential Moving Average
	private double MACD;//Moving Average Convergence/Divergence
	private double STOCHsK;//Stochastic Oscillator SlowK
	private double STOCHsD;//Stochastic Oscillator SlowD
	private String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:").format(new Date()); 	
	//constant URLs to access each function
	//the base URL is defined in the superclass Instrument, it has an accessor
	//how to use: call getBaseURL(), then concatenate the desired function's URL onto the end
	//paste the link starting with the name of the function (not function=...) and ending with the APIKey 
	//the problem's with the getter for symbol
	private final String smaFunction = "SMA&symbol="+this.getSymbol()+"&interval=60min&time_period=10&series_type=close&apikey=OM2N5Q91WJ8MLF51";
	private final String emaFunction = "EMA&symbol="+this.getSymbol()+"&interval=60min&time_period=10&series_type=close&apikey="+ "OM2N5Q91WJ8MLF51";
	private final String macdFunction = "MACD&symbol="+this.getSymbol()+"&interval=60min&series_type=close&fastperiod=10&apikey=" + "OM2N5Q91WJ8MLF51";
	private final String stochFunction = "STOCH&symbol="+this.getSymbol()+"&interval=60min&slowkmatype=1&slowdmatype=1&apikey="+"OM2N5Q91WJ8MLF51";
	public double getSMA() {
		return this.SMA;
	}
	public double getEMA() {
		return EMA;
	}
	public double getMACD() {
		return MACD;
	}
	public double getSTOCHsK() {
		return STOCHsK;
	}
	public double getSTOCHsD() {
		return STOCHsD;
	}
	//there's a lot of repeated code between this, setEMA1, setMACD and setSTOCH, which makes me think I should write a method... but what parameters would I feed it?
	//how would I account for the different URLs to call for the different functions? 
	//JJ's answer: Regular Expressions (regex's). Use regexes to write a general method that can parse out the header automatically, look for info from JSON using
	//the patterns shared between Strings (via regex), as opposed to how I did it at first, which was based on the Strings themselves
	public void setSMA1()
	{
		try
		{
			//make API calls hourly, cache the data 
			URL sma = new URL(this.getBaseURL()+smaFunction);
			Scanner scan = new Scanner(new InputStreamReader(sma.openStream()));
			String str = "";
			//String timeStamp= new SimpleDateFormat("yyyy-MM-dd HH:").format(new Date());
			if(Integer.parseInt(timeStamp.substring(11,13)) >16)
			{
				timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " 16:";
			}
			while(scan.hasNextLine())
			{
				str+=scan.nextLine() +"\n";
			}
			str = "{"+str.substring(str.indexOf("A\": {")+5); //the header is str between 0 and the first occurrence of "},". this removes the header
			JSONObject parser = new JSONObject(str);
			JSONObject jsonObject = parser.getJSONObject(timeStamp+"00"); //the program only works after 11am, or whatever hour is hardcoded 
			this.SMA = jsonObject.getDouble("SMA");
			scan.close();
		}
		catch(Exception e)
		{
			//System.out.println(e);// It seems like it's spinning its wheels and returning a JSONException/ NullPointerException until str contains some JSON 
			//from which to pull the values from
		}
	}
	/*public void setSMA()
	{
		JSONObject parser = new JSONObject();
		try 
		{
			URL sma = new URL(this.getBaseURL()+smaFunction);
			Scanner scan = new Scanner(new InputStreamReader(sma.openStream()));
			String str = "";
			while(scan.hasNextLine())
			{
				str+= scan.nextLine() +"\n";
			}
			int headerLengthSMA = ("\"Meta Data\": {\r\n" + 
					"        \"1: Symbol\": \"\",\r\n" + 
					"        \"2: Indicator\": \"Simple Moving Average (SMA)\",\r\n" + 
					"        \"3: Last Refreshed\": \"2017-12-04 10:30:00\",\r\n" + 
					"        \"4: Interval\": \"1hr\",\r\n" + 
					"        \"5: Time Period\": 10,\r\n" + 
					"        \"6: Series Type\": \"close\",\r\n" + 
					"        \"7: Time Zone\": \"US/Eastern\"\r\n" + 
					"    },\r\n" + 
					"    \"Technical Analysis: SMA\": ").length() + this.getSymbol().length();
			str = "{"+str.substring(headerLengthSMA-1);
			System.out.println(str);
			parser = new JSONObject(str);
			String timeStamp;//idea: use a for loop where i=100 to import tne entire series of sma's to an array, so that data can be used in algorithms
			int hour = 11;
			int minute = 45;
			timeStamp= new SimpleDateFormat("yyyy-MM-dd HH:"+00).format(new Date());
			JSONObject jsonObject = parser.getJSONObject(timeStamp);//object name is timestamp, not SMA
			this.SMA = jsonObject.getDouble("SMA");
			scan.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}*/
	public void setEMA1()
	{
		try
		{
			URL ema = new URL(this.getBaseURL()+emaFunction);
			Scanner scan = new Scanner(new InputStreamReader(ema.openStream()));
			String str = "";
			//String timeStamp= new SimpleDateFormat("yyyy-MM-dd HH:").format(new Date());
			if(Integer.parseInt(timeStamp.substring(11,13)) >16)
			{
				timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " 16:";
			}
			while(scan.hasNextLine())
			{
				str+=scan.nextLine() +"\n";
			}
			str = "{"+str.substring(str.indexOf("A\": {")+5);//the header is str between 0 and the first occurrence of "},". this removes the header
			JSONObject parser = new JSONObject(str);
			JSONObject jsonObject = parser.getJSONObject(timeStamp+"00");
			this.EMA = jsonObject.getDouble("EMA");
			scan.close();
		}
		catch(Exception e)
		{
			//System.out.println(e);
		}
	}
	/*public void setEMA()
	{ 	
		try
		{
		String str = "";
		//EMA
		URL ema = new URL(this.getBaseURL()+emaFunction);
		Scanner scan  = new Scanner(new InputStreamReader(ema.openStream()));
		str = "";
		while (scan.hasNextLine())
		{
			str+= scan.nextLine() + "\n";
		}
		String timeStamp= new SimpleDateFormat("yyyy-MM-dd HH:"+00).format(new Date());
		int headerLengthEMA = (" \"Meta Data\": {\r\n"+
	       " \"1: Symbol\": \"\",\r\n"+
	       " \"2: Indicator\": \"Exponential Moving Average (EMA)\",\r\n"+
	       " \"3: Last Refreshed\": \"\",\r\n"+
	       " \"4: Interval\": \"15min\",\r\n"+
	       " \"5: Time Period\": 10,\r\n"+
	       " \"6: Series Type\": \"close\",\r\n"+
	       " \"7: Time Zone\": \"US/Eastern\"+\r\n"+
	       " },\r\n"+
	       "	\"Technical Analysis: EMA\":").length()+ this.getSymbol().length()+ timeStamp.length();
		str = "{"+str.substring(headerLengthEMA+55);
		System.out.println(str);
		JSONObject parser = new JSONObject(str);
		JSONObject jsonObject = parser.getJSONObject(timeStamp);
		this.EMA = jsonObject.getDouble("EMA");
		scan.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}*/
	
	public void setMACD()
	{
		try
		{
			URL macd = new URL(this.getBaseURL()+macdFunction);
			Scanner scan = new Scanner(new InputStreamReader(macd.openStream()));
			String str = "";
			//String timeStamp= new SimpleDateFormat("yyyy-MM-dd HH:").format(new Date());
			if(Integer.parseInt(timeStamp.substring(11,13)) >16)
			{
				timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " 16:";
			}
			else if(Integer.parseInt(timeStamp.substring(11,13)) >16) 
			{
				timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " 09:";//program can't run between hours of midnight and 9am...
			}
			while(scan.hasNextLine())
			{
				str+=scan.nextLine() +"\n";
			}
			//I need to make this more general, but how?
			str = "{"+str.substring(str.indexOf("D\": {")+5);// this removes the header
			//System.out.println(str);
			JSONObject parser = new JSONObject(str);
			JSONObject jsonObject = parser.getJSONObject(timeStamp+"00");
			this.MACD = jsonObject.getDouble("MACD");
			scan.close();
		}
		catch(Exception e)
		{
			//System.out.println(e);
		}
	}
	public void setSTOCH()
	{
		try
		{
			URL stoch = new URL(this.getBaseURL()+stochFunction);
			Scanner scan = new Scanner(new InputStreamReader(stoch.openStream()));
			String str = "";
			
			//String timeStamp= new SimpleDateFormat("yyyy-MM-dd HH:").format(new Date());
			if(Integer.parseInt(timeStamp.substring(11,13)) >16)
			{
				timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " 16:";
			}
			
			while(scan.hasNextLine())
			{
				str+=scan.nextLine() +"\n";
			}
			//I need to make this more general, but how?
			str = "{"+str.substring(str.indexOf("H\": {")+5);// this removes the header
			//System.out.println(str);
			JSONObject parser = new JSONObject(str);
			JSONObject jsonObject = parser.getJSONObject(timeStamp+"00");
			this.STOCHsD = jsonObject.getDouble("SlowD");
			this.STOCHsK = jsonObject.getDouble("SlowK");
			scan.close();
		}
		catch(Exception e)
		{
			//System.out.println(e);
		}
	}
	public void setValues()
	{
		super.setValues();
		//To take advantage of bulk quotes, move the set values functionality from each stock to each fund... add capability to call perform one bulk request from the portfolio level, the fund that contains all the funds, stocks and instruments the per
		/*this.setSMA1();
		this.setEMA1();
		this.setMACD();
		this.setSTOCH();*/
		
		/*try {
			//System.out.println(this.SMA);
			
			//MACD... this should really be a method of its own...
			URL macd = new URL(this.getBaseURL()+macdFunction);
			Scanner scan = new Scanner(new InputStreamReader(macd.openStream()));
			String timeStamp= new SimpleDateFormat("yyyy-MM-dd HH:"+00).format(new Date());
			String str = "";
			while(scan.hasNextLine())
			{
				str+= scan.nextLine() +"\n";
			}
			String headerMACD = 
			"	    \"Meta Data\": {\r\n"+
		    "    \"1: Symbol\": \"\",\r\n"+
		    "    \"2: Indicator\": \"Moving Average Convergence/Divergence (MACD)\",\r\n"+
		    "    \"3: Last Refreshed\": \"\",\r\n"+
		    "    \"4: Interval\": \"15min\",\r\n"+
		    "    \"5.1: Fast Period\": 10,\r\n"+
		    "    \"5.2: Slow Period\": 26,\r\n"+
		    "    \"5.3: Signal Period\": 9,\r\n"+
		    "    \"6: Series Type\": \"close\",\r\n"+
		    "    \"7: Time Zone\": \"US/Eastern\"\r\n"+
		    "},\r\n"+
		    "Technical Analysis: MACD\":";
			int headerLengthMACD = headerMACD.length()+this.getSymbol().length()+timeStamp.length();
			str = "{"+ str.substring(headerLengthMACD-1+40);
			System.out.println(str);
			
			JSONObject parser = new JSONObject(str);
			this.MACD = parser.getDouble("MACD");
			scan.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}	*/
	}
	public Stock()
	{
		super();
		this.setValues();
	}
	
	public Stock(String aSymbol)
	{
		super(aSymbol);
		this.setValues();
		//super.setSymbol(aSymbol);
		//this.setSymbol(aSymbol);
	}
	public String toString()
	{
		//maybe use an object of type Technical with the fields name, value1, value2? Then have an array of objects of type technical
		//build a string using += or .concat(String newStuff) to add to a string with a for loop 
		return super.toString() + "\nSMA: "+ this.getSMA()+ "\n EMA: " +this.getEMA() +"\nMACD: " +this.getMACD() + "\nSTOCHsD: "+this.getSTOCHsD() + "\nSTOCHsK: " + this.getSTOCHsK()+"\n";
	}
	public String signalSimpleSMA()//here's where we implement the algorithms-- a new method for each algorithm, call the next one "signalSimpleEMA" or something
	{
		if(this.getPrice()<this.getSMA())
		{
			return "buy";
		}
		else
		{
			return "sell";
		}
	}
	
}
