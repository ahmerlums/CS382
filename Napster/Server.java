import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.net.*;
import java.io.*;
import java.net.*;
import java.io.*;


public class Server  {


	public static Map<String, String> Container = new HashMap<String,String>();
	public static Vector<String> PortHistory = new Vector<String>();

	public static void main(String args[]) throws Exception {

		int port = 8822;
		String port2=null;

		ServerSocket server = new ServerSocket(port);
		System.out.println("Starting server.");
		while (true)
		{
			

			while (true) {			
			Socket client = server.accept();
			PrintWriter pw = new PrintWriter(client.getOutputStream(), true);
			InputStreamReader isr = new InputStreamReader(client.getInputStream());
			BufferedReader br = new BufferedReader(isr);

			port2=br.readLine();

			if (!PortHistory.contains(port2))
			{
			System.out.println(port2+" connected");
			PortHistory.addElement(port2);
			}
				String message = br.readLine();
				System.out.println(message);

				if (message.charAt(0)=='L')
				{
					String temp ="";
					for (int i=7;i<message.length();i++)
					{
						temp=temp+message.charAt(i);
					}


					int test = 0;
					if (Container.containsKey(temp))
					{
						test=1;
						pw.println(Container.get(temp));
					}

					else
						pw.println("-1");
				}

				if (message.charAt(0)=='G')
				{

					String temp="";
					for (int i = 4;i<message.length();i++)
					{

						temp=temp+message.charAt(i);
					
					}

					if (Container.containsKey(temp))
					{
					pw.println(Container.get(temp));			 
					}
				}
				
				if (message.charAt(0)=='P')
				{
					int i=4;
					String temp="";
					while(true)
					{
						if (message.charAt(i)==' ')
							break;
						temp=temp+message.charAt(i);
						i++;
					}
					Container.put(temp, port2);

				
					pw.println("+1");
			
				}
			client.close();
		}
		}
	}
	}