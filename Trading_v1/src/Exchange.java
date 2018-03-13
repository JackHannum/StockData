import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class Exchange {
	private String name;
	private String listAddress;
	public void setName(String aName)
	{
		if(aName !=null)
		{
			name = aName;
		}
	}
	public void setURL(String aURL)
	{
		if(aURL!=null)
		{
			listAddress = aURL;
		}
	}
	public String getName()
	{
		return this.name;
	}
	public String getURL()
	{
		return this.listAddress;
	}
	public Scanner getScanner()
	{
		try
		{
			return new Scanner(new InputStreamReader(new URL(this.listAddress).openStream()));
		}
		catch (Exception e)
		{
			System.out.println(e);
			return null;
		}
	}
	public Exchange()
	{
		
	}
	public Exchange(String aName, String aURL)
	{
		this.setName(aName);
		this.setURL(aURL);
	}
}
