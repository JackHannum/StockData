//algorithm concept: buy when stock is below its 200 day moving average, holds until it falls below its 200 day moving average and then sell
//make it able to automatically place orders
//stage 1: collect, map data to objects 
//stage 2: write alogrithms to make decisions based on that data, determine what data would be useful to write algorithms, then modify stage 1
//stage 3: make able to automatically place buy and sell orders 
//research finance professors who could help me write algorithms
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;
public class Fund extends Instrument {
	private static final String DELIM = ",";
	private static final int DEFAULT_SIZE = 5;
	private Instrument[] instruments = new Instrument[DEFAULT_SIZE];
	
	public Fund()
	{
		
	}
	public Fund (int size)
	{
		instruments = new Instrument[size];
	}
	public String toString()
	{
		String output = "";
		for(int i = 0; i<instruments.length; i++)
		{
			if(instruments[i] != null)
			{
				output += instruments[i].toString()+ "\n";
			}
			else
				output += "null";
		}
		return output;
	}
	public String getSymbols()
	{
		String output = "";
		for(Instrument i:instruments)
		{
			if(i!= null)
			{
				output += i.getSymbol()+ "\n";
			}
			else
				output+="null";
		}
		return output;
	}
	public int getFundLength()
	{
		return instruments.length;
	}
	public Instrument getInstrument(int i)
	{
		return instruments[i];
	}
	public void addInstrument(Instrument anI)
	{
		if(instruments[instruments.length-1] != null)
		{
			System.out.println("The Array is full");
			Instrument[] copy = new Instrument[instruments.length+1];//and you said arrays aren't resizable in memory
			for(int i = 0; i<instruments.length; i++)
			{
				copy[i] = instruments[i];
			}
			instruments = copy;
		}
		for(int i = 0; i<instruments.length; i++)
		{
			if(instruments[i] == null)
			{
				instruments[i] = anI;
				break;
			}
		}
	}
	public void setPrices()
	{
		System.out.println("setPrices Entered");
		for(int i = 0;i < instruments.length; i++)
		{
			System.out.println(instruments[i].getPrice());
		}
		try
		{
			//Build the string with the first hundred stock symbols
			String stocks = "";
			for(Instrument i: instruments)
			{
				stocks+= (i.getSymbol() + DELIM);
			}
			URL intraDaySeries = new URL(this.getBaseURL() + "BATCH_STOCK_QUOTES&symbols="+stocks+"&apikey="+this.getAPIKey());
			Scanner intraDaySeriesStream = new Scanner(new InputStreamReader(intraDaySeries.openStream()));
			intraDaySeriesStream.nextLine();//skip the csv header
			while(intraDaySeriesStream.hasNextLine())
			{
			String[] splitStrings = intraDaySeriesStream.nextLine().split(DELIM);
			System.out.println(splitStrings.length);
			if(splitStrings.length == 3)//correctly formatted
			{
				System.out.println("Correct Formatting!");
				for(int i = 0; i< instruments.length;i++)
				{
					System.out.println(splitStrings[0]);
					System.out.println(splitStrings[1]);
					if(instruments[i].getSymbol().equalsIgnoreCase(splitStrings[0]));
					{
						System.out.println(splitStrings[1]);
						instruments[i].setPrice(Double.parseDouble(splitStrings[1]));
					}
				}
			}
			}
			intraDaySeriesStream.close();
		}
		catch(Exception e)
		{
			//System.out.println(e);
		}
	}
	public void removeInstrument(String symbol)
	{
		int removeIndex = -1;
		//System.out.println(symbol);
		for(int i = 0; i<instruments.length; i++)
		{
			if(instruments[i] != null && instruments[i].getSymbol().equals(symbol))
			{
				removeIndex = i;
				break;
			}
		}
		if(removeIndex == -1)
		{
			System.out.println("Instrument not found.");
		}
		else
		{
			for(int i =0; i<instruments.length-1; i++)
			{
				instruments[i] = instruments[i+1];
			}
			instruments[instruments.length-1] = null;
		}
		Instrument[] copy = new Instrument[instruments.length-1];
		for(int i = 0; i<instruments.length-1;i++)
		{
			copy[i] = instruments[i];
		}
		instruments = copy; 
	}
	
}
