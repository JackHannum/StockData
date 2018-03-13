import java.net.*;
import java.text.SimpleDateFormat;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;
public class Networking {

	private static final String DELIM = ",";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Zero Intelligence Market API: 
		//Zero intelligence market- no humans- purely driven by robots. Allow robots to trade, this allows us to study how design of an instrument (i.e able to short, unable) 
		//a lot of real algo trading isn't based on technical indicators, they're played out because everyone already knows them
		//opportunity comes from knowledge that other people don't have yet... this allows you to buy early... wisdom of the crowd
		//this code takes a very long time to run. It's been 10 minutes and it still hasn't iterated through just 20 of the loops, let alone the full 3000 on NYSE
		//there has to be a more efficient way to do this, or I'm gonna need some more firepower
		String[] NYSE;
		String[] lines;
		String[] symbols;
		Fund NYSEFund;
		//TODO Update to take advantage of bulk stock quotes
		//long startTime = System.currentTimeMillis();
		try 
		{
			//String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
			
			//do the web stuff only hourly, and update the database
			URL nyseList = new URL("http://www.nasdaq.com/screening/companies-by-industry.aspx?exchange=NYSE&render=download");//as long as NASDAQ.com updates this csv, we'll have the most up to date list of stocks on the NYSE
			Scanner nyseListStream = new Scanner(new InputStreamReader(nyseList.openStream()));
			int counter = 0;
			int NUM_STOCKS = 10;	//defined as a variable because eventually, it's gonna be set to the changing value of counter
			System.out.println("Counting...");
			while(nyseListStream.hasNextLine())
			{
				nyseListStream.nextLine();
				counter++;//first pass to count how many stocks are on the exchange				
			}
			nyseListStream.close();
			System.out.println("There are "+counter+" stocks on the NYSE");
			nyseListStream = new Scanner(new InputStreamReader(nyseList.openStream()));//reconstruct to go back to the top
			lines = new String[NUM_STOCKS];//no magic numbers 
			symbols = new String[NUM_STOCKS];//once I optimize the object assignment code, this will be set to counter (all 3000+ stocks on the NYSE, for example)
			NYSEFund = new Fund(NUM_STOCKS);

			//while(nyseListStream.hasNextLine())
			//{
			nyseListStream.nextLine();//advance one line, so we don't include the label "symbol" as a stock 
			for(int i = 0; i<lines.length;i++)
			{
				lines[i] = nyseListStream.nextLine();
				String[] splitStrings = lines[i].split(DELIM);
				symbols[i] = splitStrings[0].substring(1, splitStrings[0].length()-1);
			}
			//}
			for(int i = 0; i<NYSEFund.getFundLength();i++)
			{
				//this code takes a really long time to execute... why?
				//more than 5 minutes 
				System.out.println("adding instruments to NYSEFund..." + i);
				NYSEFund.addInstrument(new Stock(symbols[i]));
			}			
			NYSEFund.setPrices();
			System.out.println("NYSEFund:");
			System.out.print(NYSEFund.toString()+"  ");
			for(int i = 0; i<NYSEFund.getFundLength();i++)
			{
				//getInstrument works  
				System.out.println("Signal for " + NYSEFund.getInstrument(i).getSymbol()+": "+((Stock)NYSEFund.getInstrument(i)).signalSimpleSMA());	
			}
			nyseListStream.close();
			Fund simpleSMABased = new Fund();
			for(int i = 0; i< simpleSMABased.getFundLength();i++)
			{
				if(i<NYSEFund.getFundLength())
				{
					if( ((Stock)(NYSEFund.getInstrument(i))).signalSimpleSMA().equals("buy") )
					{
						simpleSMABased.addInstrument(NYSEFund.getInstrument(i));
					}
				}
			}
		}
	catch(Exception e)
	{
		System.out.println(e);
	}
	}
}
