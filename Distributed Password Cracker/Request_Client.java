import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.net.*;
import java.io.*;

public class Request_Client {

	public static void main(String args[]) {
		try{
	System.out.println("Enter your Hash:");
	Scanner scan = new Scanner(System.in);
	String hash = scan.nextLine();

	hash = encryptPassword(hash);
	System.out.println(hash);
	System.out.println("Enter port:");
	scan = new Scanner(System.in);
	int port = scan.nextInt();
	DatagramSocket clientSocket = new DatagramSocket(port);
	byte[] sendData = new byte[1024];
    byte[] receiveData = new byte[1024];
    sendData = hash.getBytes();
    InetAddress IPAddress = InetAddress.getByName("localhost");
    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress , 17760);
    clientSocket.send(sendPacket);
    String Answer = new String();
    int x = 0;
    while (true)
    {
    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
    clientSocket.receive(receivePacket);
    Answer = new String (receivePacket.getData());
    
    if (Answer.charAt(0)=='3'&&Answer.charAt(1)=='.')
    {
    	x++;
    	System.out.println(x +" Workers Working!");
    }
   
    else
    	System.out.println(Answer);
    	
   
    }
    
	}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
	
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

