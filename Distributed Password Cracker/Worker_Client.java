import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.net.*;
import java.io.*;

public class Worker_Client {
	
	public static DatagramSocket WorkerSocket;
	public static DatagramSocket WorkerSocket1;
	static byte[] receiveData = new byte[1024];
	static  byte[] sendData = new byte[1024];
	static String Hash = new String();
	static boolean WorkTime = false;
	static InetAddress IPAddress;
	static String ToCrack = new String();
	static String Start_Key = new String();
	static String End_Key = new String();
	static String Processed_Key = new String();
	static Integer port = 0;
	public static boolean Connection = false;
	public static boolean Cancel = false;
	public static boolean Change_Key = false;
	public static void main (String args[])
	{

		System.out.println("Enter Port:");

	    Scanner PortScan = new Scanner(System.in);
		port = PortScan.nextInt();
		PortScan.close();
		

		new Thread(new Runnable() {
			public void run() {
		
		
		try 
		{
			WorkerSocket= new DatagramSocket(port);
		    IPAddress = InetAddress.getByName("localhost");
		    while (Connection == false)
		    {	
			    String tmp = "1.Request to Join";
			    sendData = tmp.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,IPAddress,17760);
			WorkerSocket.send(sendPacket);
			long start = System.currentTimeMillis();
	    	long end = start + 3*1000; 
			while (System.currentTimeMillis() < end)
			{
				if (Connection == true)
					break;
	
		    }
		
		    }
		    System.out.println("Connection Established!");
	    while (true)
        {
	   // 	System.out.println(WorkTime);
	if (WorkTime == true)
		WorkTime = true;
        if (WorkTime==true)
        {
        Start_Key = ToCrack.substring(0, 5);
       // Start_Key = "a9988";
		String Character = "abcd*e=fghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
		String Match = new String();
		StringBuilder toHash = new StringBuilder(Start_Key);
		int r = 4;
		int i = 0; 
		int c = 0;
		int d = 0;
		int n = 0;
		int e = 0;
		int l = 0;
		for (int t = 0; t < Character.length();t++)
			if (Character.charAt(t)==toHash.charAt(0))
				l=t;
		
		for (int t = 0; t < Character.length();t++)
			if (Character.charAt(t)==toHash.charAt(1))
				e=t;
		for (int t = 0; t < Character.length();t++)
			if (Character.charAt(t)==toHash.charAt(2))
				n=t;
		d=n;
		
		boolean Found = false;
		while (!toHash.toString().equals(End_Key))
		{
// if (toHash.toString().equals ("ahabc"))
	//break;
//	System.out.println(Cancel);
		
			if (Change_Key==true)
			{
				toHash = new StringBuilder(Start_Key);
				for (int t = 0; t < Character.length();t++)
					if (Character.charAt(t)==toHash.charAt(0))
						l=t;
				
				for (int t = 0; t < Character.length();t++)
					if (Character.charAt(t)==toHash.charAt(1))
						e=t;
				for (int t = 0; t < Character.length();t++)
					if (Character.charAt(t)==toHash.charAt(2))
						n=t;
				d=n;
				Change_Key = false;
			}
		if (Cancel == true)
			Cancel = true;
			if (Cancel == true)
			{
				WorkTime = false;
				Cancel = false;
				break;
			}
			String temp = toHash.toString();
			Processed_Key = temp;
	
			Match = encryptPassword(temp);
			
			if (Match.equals(ToCrack.substring(12, 44)))
					{
						WorkTime = false;
						Found = true;
						temp = "5." + temp;
						sendData = temp.getBytes();
						DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,IPAddress,17760);
						WorkerSocket.send(sendPacket);
						break;
					}
					
	
			if (e == Character.length()-1 && n==Character.length())
			{
				System.out.println(temp);
				l++;
				toHash.setCharAt(0, Character.charAt(l));
				toHash.setCharAt(1, 'a');
				toHash.setCharAt(2, 'a');
				toHash.setCharAt(3, 'a');
				toHash.setCharAt(4, 'a');
				e=-1;
			}
			
			if (n == Character.length()&&d==Character.length()&&r==2)
			{
				System.out.println(toHash);

				e++;
				toHash.setCharAt(1, Character.charAt(e));
				System.out.println(toHash);
				if (toHash.toString().equals(End_Key))
				{
					temp = "4." + ToCrack;
					sendData = temp.getBytes();
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,IPAddress,17760);
					WorkerSocket.send(sendPacket);
					break;
				}
				toHash.setCharAt(2, 'a');
				toHash.setCharAt(3, 'a');
				toHash.setCharAt(4, 'a');
				n=0;
				d=0;
				r=2;
			
			}
			
			if (r==2)
			{ //System.out.println(temp);

				toHash.setCharAt(2, Character.charAt(d));
				toHash.setCharAt(3, 'a');
				toHash.setCharAt(4, 'a');
				d++;
				r=4;
				i=0;
				c=0;
				n++;
			}
			toHash.setCharAt(r, Character.charAt(i));
			i++;
			if (i ==  Character.length())
			{
				if (c == Character.length())
				{
					c = 0;
					if (r!=0)
						r--;
				}
				
				i = 0;
				if (r>2)
				   toHash.setCharAt(r-1, Character.charAt(c));
				c++;
			}
			
		}
		WorkTime = false;
		if (Found == false)
		{
			String temp ="4.";
			sendData = temp.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,IPAddress,17760);
			WorkerSocket.send(sendPacket);
		}
        }
        }
		
	
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
			}}).start();
		
		new Thread(new Runnable() {
			public void run() {
				try
				{
					while (true)
					{
					DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
					if (WorkerSocket!=null)
					WorkerSocket.receive(receivePacket);
					String Command = new String(receivePacket.getData());
					System.out.println(Command);
					if (Command.charAt(0)=='2'&& Command.charAt(1)=='.')
					{
						System.out.println("recieved");
						
		        	ToCrack = Command.substring(2,Command.length());
		        	End_Key = ToCrack.substring(6, 11);
		        	if (WorkTime == true)
		        	{
		        		if (!Start_Key.equals(ToCrack.substring(0, 5)))
		        		{
		        			Start_Key = ToCrack.substring(0,5);
		        			Change_Key = true;
		        			System.out.println("Start Key Updated to " + Start_Key);
		        		}
		        		System.out.println("End Key Updated to " + End_Key);
		        	}
		        	if (WorkTime == false)
		        	{
		        	Command = "3." + ToCrack;
		        	sendData = Command.getBytes();
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,IPAddress,17760);
					WorkerSocket.send(sendPacket);
		        	}
		        	
		        	WorkTime = true;
					}
					else if (Command.charAt(0)=='0')
					{ 
						String progress = "6." + End_Key +" "+Processed_Key;
			        	sendData = progress.getBytes();
						DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,IPAddress,receivePacket.getPort());
						WorkerSocket.send(sendPacket);
					}
					else if (Command.charAt(0)=='7')
					{
						Cancel = true;
					}
		        
					else if (Command.charAt(0)=='A')
					{
						Connection = true;
					}
		        
				}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
				
			}}).start();
		
		
		
	}
	
	private static String encryptPassword(String password)
	{
	    String sha1 = "";
	    try
	    {
	        MessageDigest crypt = MessageDigest.getInstance("MD5");
	        crypt.reset();
	        crypt.update(password.getBytes("UTF-8"));
	        sha1 = byteToHex(crypt.digest());
	    }
	    catch(NoSuchAlgorithmException e)
	    {
	        e.printStackTrace();
	    }
	    catch(UnsupportedEncodingException e)
	    {
	        e.printStackTrace();
	    }
	    return sha1;
	}

	private static String byteToHex(final byte[] hash)
	{
	    Formatter formatter = new Formatter();
	    for (byte b : hash)
	    {
	        formatter.format("%02x", b);
	    }
	    String result = formatter.toString();
	    formatter.close();
	    return result;
	}

}

