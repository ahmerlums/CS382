import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;


public class Circle_Clients  {

	public static Node Node;
	static Integer in=0;
	static String close=new String();
	public static void main(String args[]) throws Exception,EOFException {

		Scanner b = new Scanner(System.in);
		System.out.println("Enter Port:");
		Integer port = b.nextInt();
		final ServerSocket server = new ServerSocket(port);
		String tohash="localhost"+port;
		System.out.println(tohash);
		String Key = encryptPassword(tohash);
		Node=new Node(Key);
		
		Node.NodePort=port;
		System.out.println("Need to Join First:");
		File Directry = new File("./Node"+port+"/Node"+port+".xml"); 
		Directry.getParentFile().mkdirs();
        
		 b = new Scanner(System.in);
		 String input = b.nextLine();
			if (input.charAt(0)=='J')
			{
			Socket sock = new Socket("localhost", 1218);
			ObjectOutputStream outputStream = new ObjectOutputStream(sock.getOutputStream());
			ObjectInputStream inStream =new ObjectInputStream(sock.getInputStream());
			outputStream.writeObject("JOIN");
			Integer t2=(Integer) inStream.readObject();	
			Node.ComputerHash(Node.NodeKey, t2);
			outputStream.writeObject(Node);
			String message=(String) inStream.readObject();
			if (message!=null)
			{
			if (message.charAt(0)=='T')
				{
			//		outputStream.writeObject("OK");
					Node t = (Node) inStream.readObject();
					Node.Succesor=t;
				}
			else 
			{
				Socket client = server.accept();
				ObjectInputStream inStream1 =new ObjectInputStream(client.getInputStream());
				ObjectOutputStream outputStream1 = new ObjectOutputStream(client.getOutputStream());
				
				outputStream1.writeObject("Join");
				Node t = (Node) inStream1.readObject();
				String temp= (String) inStream1.readObject();
				if (temp.equals("Sending"))
				{
				for (int i=0;i<t.Container.size();i++)
					{
						try
						{
						if (t.VectorofHashes.elementAt(i)<Node.NodeHash)
						{
						FileWriter file = new FileWriter("./Node"+Node.NodePort+"/"+t.VectorofKeys.elementAt(i)+".txt");
						file.write(t.VectorofValues.elementAt(i));
						file.close();
						Node.Container.put(t.VectorofKeys.elementAt(i),t.VectorofValues.elementAt(i));
						Node.VectorofKeys.addElement(t.VectorofKeys.elementAt(i));
						Node.VectorofValues.addElement(t.VectorofValues.elementAt(i));
						Node.VectorofHashes.addElement(t.VectorofHashes.elementAt(i));
						File f=new File("./Node"+t.NodePort+"/"+t.VectorofKeys.elementAt(i)+".txt");
						f.delete();
						}
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}	
				Node.Succesor=t;
				}

				else
				{
					Node.Succesor=t;
					Node.Predecessor=t;
				}
			}
			sock.close();
			if (Node.Succesor!=null)
			{
			System.out.println("Successor set to: " + Node.Succesor.NodePort);
			System.out.println("NodeHash: "+Node.NodeHash);
			}
			
			else
				System.out.println("No Succesor for now!");
			}
			if (Node.Predecessor!=null)
				System.out.println("Predecessor set to: " + Node.Predecessor.NodePort);
			
			}
			
			new Thread(new Runnable() {
				public void run(){
					while (true)
					{
						System.out.println("Put,Get,Leave?");
						Scanner b=new Scanner(System.in);
						String input = b.nextLine();
						if (input.charAt(0)=='P'||input.charAt(0)=='G')
						{
							String Value=new String();
							if (input.charAt(0)=='P')
							{
							System.out.println("Enter Value");
							b= new Scanner(System.in);
							Value = b.nextLine();
							}
							System.out.println("Enter Key");
							b= new Scanner(System.in);
							String key= b.nextLine();
							Node temp=new Node(encryptPassword(key));
							temp.NodeValue=Value;
						
							temp.IsANode=false;
							
							try {
								while(true)
								{
								Socket sock = new Socket("localhost", 1218);
								ObjectOutputStream outputStream = new ObjectOutputStream(sock.getOutputStream());
								ObjectInputStream inStream =new ObjectInputStream(sock.getInputStream());
								outputStream.writeObject("Join");
								Integer a=(Integer) inStream.readObject();
								temp.ComputerHash(temp.NodeKey, a);
								temp.NodeKey=key;
								temp.NodePort=temp.NodeHash;
								outputStream.writeObject(temp);
								String message=(String) inStream.readObject();
								ServerSocket server1 = new ServerSocket(temp.NodeHash);
								if (message.equals("OK"))
								{
									Socket client = server1.accept();
									ObjectInputStream inStream1 =new ObjectInputStream(client.getInputStream());
									ObjectOutputStream outputStream1 = new ObjectOutputStream(client.getOutputStream());
									outputStream1.writeObject("Put");
									Node t = (Node) inStream1.readObject();
									String temp1= (String) inStream1.readObject();
									if (t!=null)
									{
										if (input.charAt(0)=='P')
										{
											t.Container.put(temp.NodeKey, Value);
											FileWriter file = new FileWriter("./Node"+t.NodePort+"/"+key+".txt");
											file.write(Value);
											file.close();
											System.out.println("Value with Hash "+ key+" stored at Node with port "+ t.NodePort);
										}
										else
										{
											File f = new File("./Node"+t.NodePort+"/"+key+".txt");
											if (f.exists())
											{
												FileReader fi = new FileReader("./Node"+t.NodePort+"/"+key+".txt");
												BufferedReader bufferedReader = new BufferedReader(
														fi);
												String line = new String();
												line=bufferedReader.readLine();
												bufferedReader.close();
												fi.close();
												FileWriter file = new FileWriter("./Node"+Node.NodePort+"/"+key+".txt");
												file.write(line);
												file.close();
												Node.Container.put(key, line);
												Node.VectorofKeys.addElement(key);
								            	Node.VectorofValues.addElement(line);
								            	Node.VectorofHashes.addElement(temp.NodeHash);
												System.out.println("File stored and got from Node "+t.NodePort);
											}
												
											
											else
											{
												System.out
														.println("No such file present!");
												f.delete();
											}
												
										}
										server1.close();
									}
									else 
										System.out.println("NULL ");
									sock.close();
									break;
								}
								sock.close();
								}
								
						
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						
						if (input.charAt(0)=='L')
						{
							try{
								String msg=new String();
								ObjectOutputStream outputStream=null;
								ObjectInputStream inStream=null;
								Socket sock=null;
								while (true)
								{
							sock = new Socket("localhost", Node.Succesor.NodePort);
						 outputStream = new ObjectOutputStream(sock.getOutputStream());
							inStream =new ObjectInputStream(sock.getInputStream());
							 msg=(String) inStream.readObject();
							if (msg.equals("OK"))
								break;
							outputStream.writeObject("FlopMSg");
							sock.close();
								}
							outputStream.writeObject("LeaveSuccesor");
							outputStream.writeObject(Node);
							sock.close();
							while (true)
							{
						sock = new Socket("localhost", Node.Predecessor.NodePort);
					 outputStream = new ObjectOutputStream(sock.getOutputStream());
						inStream =new ObjectInputStream(sock.getInputStream());
						 msg=(String) inStream.readObject();
						if (msg.equals("OK"))
							break;
						outputStream.writeObject("FlopMSg");
						sock.close();
							}
							outputStream.writeObject("ReaveSuccesor");
							outputStream.writeObject(Node);
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
							for (int i=0;i<Node.Container.size();i++)
							{
								try
								{
								FileWriter file = new FileWriter("./Node"+Node.Succesor.NodePort+"/"+Node.VectorofKeys.elementAt(i)+".txt");
								file.write(Node.VectorofValues.elementAt(i));
								file.close();
								File f= new File("./Node"+Node.NodePort+"/"+Node.VectorofKeys.elementAt(i)+".txt");
								f.delete();
								}
								catch (Exception e)
								{
									e.printStackTrace();
								}
							}
							File f=new File("./Node"+Node.NodePort);
							f.delete();

							try
							{
						
							Socket sock2 = new Socket("localhost", 1218);
							ObjectOutputStream outputStream2 = new ObjectOutputStream(sock2.getOutputStream());
							ObjectInputStream inStream2 =new ObjectInputStream(sock2.getInputStream());
							outputStream2.writeObject("Leave"+Node.NodePort);
							Node.Succesor=null;
							close="Leave";
							System.out.println("Close this Program now!");
							break;
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
								
						
						}
						
					
					}
				}
			}).start();
			
		
					new Thread(new Runnable() {
						
			
				public void run () {
			while (true)
				try{
			if (close.equals("Leave"))
				Thread.currentThread().interrupt();
			
			Socket client = server.accept();

			ObjectOutputStream outputStream1 = new ObjectOutputStream(client.getOutputStream());
			ObjectInputStream inStream1 =new ObjectInputStream(client.getInputStream());
			outputStream1.writeObject("OK");
			String message=(String) inStream1.readObject();
			
			if (message.charAt(0)=='F')
			{
				Node t = (Node) inStream1.readObject();
				if (Node.NodeHash < t.NodeHash && (Node.Succesor.NodePort == 1218))
				{
					Socket sock1 = new Socket("localhost",t.NodePort);

					ObjectOutputStream outputStream2 = new ObjectOutputStream(sock1.getOutputStream());
					ObjectInputStream inStream2 =new ObjectInputStream(sock1.getInputStream());
					 String temp=(String) inStream2.readObject();

					 if (temp.equals("Join"))
					 {
						Node.Succesor=t;
						Node.Predecessor=t;
						System.out.println("Successor set to: " + Node.Succesor.NodePort);
						System.out.println("Predecessor set to: " + Node.Predecessor.NodePort);
					 }
					outputStream2.writeObject(Node);
					outputStream2.writeObject("No Succesor");
					
					sock1.close();
					
				}
				else if (Node.NodeHash > t.NodeHash )
				{
					Socket sock1 = new Socket("localhost",t.NodePort);
		            ObjectOutputStream outputStream2 = new ObjectOutputStream(sock1.getOutputStream());
		            ObjectInputStream inStream2 =new ObjectInputStream(sock1.getInputStream());
		            String temp=(String) inStream2.readObject();
		            
		            if (temp.equals("Put"))
		            {
		            	Node.Container.put(t.NodeKey, t.NodeKey);
		            	Node.VectorofKeys.addElement(t.NodeKey);
		            	Node.VectorofValues.addElement(t.NodeValue);
		            	Node.VectorofHashes.addElement(t.NodeHash);
		            }
		            
		            if (temp.equals("Join"))
		            {
					Node.Predecessor=t;
					System.out.println("Predecessor set to: " + Node.Predecessor.NodePort);
					if (Node.Succesor.NodePort==1218)
					{
		            	Node.Succesor=t;
		            	System.out.println("Successor set to: " + Node.Succesor.NodePort);
					}
		            }
		            outputStream2.writeObject(Node);
					outputStream2.writeObject("Sending");
					
					sock1.close();
				}
				else
				{
					
				 if (Node.Predecessor!=null&&Node.Predecessor.NodeHash>Node.NodeHash&&message.equals("FindSuccessor:"))
						{
						Socket sock1 = new Socket("localhost",t.NodePort);
			            ObjectOutputStream outputStream2 = new ObjectOutputStream(sock1.getOutputStream());
			            ObjectInputStream inStream2 =new ObjectInputStream(sock1.getInputStream());
			            String temp=(String) inStream2.readObject();
			            Node tosend=new Node(Node.Succesor.NodeKey);
			            if (temp.equals("Join"))
			            {
			            Node.Predecessor=t;
			            System.out.println("Predecessor set to: " + Node.Predecessor.NodePort);
			            }
			            
			            outputStream2.writeObject(Node);
						outputStream2.writeObject("Sending");
						
						sock1.close();
						
						}
					
					else
					{
					while (true)
					{
						
					Socket sock1 = new Socket("localhost",Node.Succesor.NodePort);
				    outputStream1 = new ObjectOutputStream(sock1.getOutputStream());
				    ObjectInputStream inStream3 =new ObjectInputStream(sock1.getInputStream());
					outputStream1.writeObject("FindSuccessor:");
					String temp=(String) inStream3.readObject();
					if(temp.equals("OK"))
					{

					outputStream1.writeObject(t);
					sock1.close();
					break;
					}
					sock1.close();
					}
					}
				}
				
			
			}
			else if (message.charAt(0)=='L')
			{
				Node t = (Node) inStream1.readObject();
			
				for (int i=0;i<t.VectorofHashes.size();i++)
				{	
					Node.Container.put(t.VectorofKeys.elementAt(i),t.VectorofValues.elementAt(i));
					Node.VectorofKeys.addElement(t.VectorofKeys.elementAt(i));
					Node.VectorofValues.addElement(t.VectorofValues.elementAt(i));
					Node.VectorofHashes.addElement(t.VectorofHashes.elementAt(i));
					
				}
				
				
					Node.Predecessor=null;
				
			}
			
			else if (message.charAt(0)=='R')
			{
				Node t = (Node) inStream1.readObject();
					Node.Succesor=t.Succesor;
					
					if (Node.Succesor==null|| (Node.Succesor.Succesor!=null&&Node.Succesor.Succesor.NodePort==Node.NodePort))
					{
						Node.Succesor=new Node("abc");
						Node.Succesor.NodePort=1218;
					}
			}

			else if (message.charAt(0)=='T')
			{
				Node t = (Node) inStream1.readObject();
				Node.Succesor=t;
			}
			client.close();	

			}

				
				
				 catch (IOException | ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
			}).start();
			new Thread(new Runnable() {
				public void run () {
					if (close.equals("Leave"))
						Thread.currentThread().interrupt();
						
					//    System.out.println("Stablizing");
					    Timer timer = new Timer();
					    timer.schedule(new TimerTask() {
					    	@Override
					  
					    	  public void run() {
								    try {
								    	if (close.equals("Leave"))
											Thread.currentThread().interrupt();
								    	if (Node.Succesor!=null&&Node.Succesor.NodePort!=1218)
								    	{
								    	Socket sock1 = new Socket("localhost",Node.Succesor.NodePort);
								    	
								
								    	ObjectOutputStream outputStream1 = new ObjectOutputStream(sock1.getOutputStream());
								    	ObjectInputStream inStream1 =new ObjectInputStream(sock1.getInputStream());
								    	String t=(String) inStream1.readObject();
								    	outputStream1.writeObject("Stablize");
								    	if (t.charAt(0)=='N')
								    	{
								    	String temp =(String) inStream1.readObject();
	
								    	
								    	if (temp.equals("YES"))
								    	{
										outputStream1.writeObject("What is your Predecessor?");
							          
										Node a = (Node) inStream1.readObject();
									   
									      if (a!=null)
										  if (a.NodePort!=Node.NodePort)
										    {
										    	Node.Succesor=a;
										    	System.out
														.println("Successor Stablised to " + Node.Succesor.NodePort);
										    }
										 
											sock1.close();
											while (true)
											{
										 sock1 = new Socket("localhost",Node.Succesor.NodePort);
										  outputStream1 = new ObjectOutputStream(sock1.getOutputStream());
									    	 inStream1 =new ObjectInputStream(sock1.getInputStream());
									    	 String l=(String) inStream1.readObject();
									    	 outputStream1.writeObject("Notify");
									    	 if (l.charAt(0)=='N')
									    	 {
									    	 temp =(String) inStream1.readObject();
									    	 if (temp.equals("YES"))
									    	 {
									    		 outputStream1.writeObject(Node);
									    		 sock1.close();
									    		 break;
									    	 }
									    	 sock1.close();
											}
											}
								    	}
								    	}
								    	}
									} catch (ClassNotFoundException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								  
					    	    // Your database code here
					    	  }
					    	}, 5*1000, 5*1000);

					    	
					    
				}
			}).start();
			
			new Thread(new Runnable() {
				public void run () {
					try{

						if (close.equals("Leave"))
							Thread.currentThread().interrupt();
					while (true)
					{
						if (close.equals("Leave"))
							Thread.currentThread().interrupt();
					Socket client = server.accept();
				    
					ObjectOutputStream outputStream1 = new ObjectOutputStream(client.getOutputStream());
				    ObjectInputStream inStream1 =new ObjectInputStream(client.getInputStream());
				 outputStream1.writeObject("NOTOK");

				    String temp= (String) inStream1.readObject();
				    if (temp.equals("Stablize"))
				    {
				    	outputStream1.writeObject("YES");
				     temp= (String) inStream1.readObject();
				    if (temp.equals("What is your Predecessor?"))
				    {
				    	outputStream1.writeObject(Node.Predecessor);
				    }
				    }

				    else if(temp.equals("Notify"))
				    {
				    	
				    	outputStream1.writeObject("YES");
				    	Node t = (Node) inStream1.readObject();
				    	if (Node.Predecessor==null||t.NodePort!=Node.Predecessor.NodePort)
				    		System.out.println("Predecssor notified to "+ t.NodePort);	
				    	
				    	Node.Predecessor=t;
				    	
				    }
				    
				    else
				    {
				    	outputStream1.writeObject("Wait");
				    }
				    client.close();
						
					}
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}).start();
				

			
	}
	
	
	
//Refernce JAVA DOCS 	
	private static String encryptPassword(String password)
	{
	    String sha1 = "";
	    try
	    {
	        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(password.getBytes("UTF-8"));
	        sha1 = byteToHex(crypt.digest());
	    }
	    catch(Exception e)
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







