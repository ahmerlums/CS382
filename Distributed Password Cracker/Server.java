import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.*;
import java.net.Authenticator.RequestorType;
public class Server {
	
	public static DatagramSocket serverSocket ;
	public static byte[] receiveData = new byte[1024];
	public static byte[] sendData = new byte[1024];
	public static String Hash = new String();
	public static boolean WorkTime = false;
	public static boolean WorkTime1 = false;
	public static Vector<Integer> WorkerPorts = new Vector<Integer>();
	public static Vector<InetAddress> WorkerIPs = new Vector<InetAddress>();
	public static String Current_Start_String = new String();
	public static String Current_Processed_String = new String();
	public static Map<Integer, ArrayList<String> > Process = new HashMap<Integer, ArrayList<String> >();
	public static Vector<Integer> RequesterPorts = new Vector<Integer>();
	public static Vector<InetAddress> RequesterIPs = new Vector<InetAddress>();
	public static Integer RequesterPort;
	public static InetAddress RequesterIP;
    public static InetAddress IPAddress;
    public static Vector<String> HashQueue = new Vector<String>();
    public static Integer is;
    public static boolean fault = false;
    public static boolean reading = false;
    public static long n=0;
    public static Integer JobsDone =0;
    public static File file = new File("a.txt");
    public static Vector<String> Queue1 = new Vector<String>();
    public static Vector<String> Queue2 = new Vector<String>();
    public static void main(String args[])
	{
		try
		{
		
			
			IPAddress = InetAddress.getByName("203.135.63.179");

			serverSocket= new DatagramSocket(17760);
			 is=0;
			 
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
			
		}


		new Thread(new Runnable() {
			public void run() {
		
		try{
			if (file.exists())
			{
			System.out.println("Server Crashed Unexpectedly!");
			InputStreamReader FileReader  = new FileReader(file);
			 BufferedReader br = new BufferedReader(FileReader);
			String data = br.readLine();
			br.close();
			FileReader.close();
			
			String temp = "";
			boolean wtime = false;
			boolean Wtime = false;
			boolean Rtime = false;
			boolean Wlost = false;
			boolean wlost = false;
			boolean Htime = false;
			for (int i=0;i<data.length();i++)
			{
				if (Htime == true)
				{
					temp = temp+data.charAt(i);	
				}
				else
				{
				if (data.charAt(i)!='/'&&data.charAt(i)!= ' '&&data.charAt(i)!='W'&&data.charAt(i)!='w'&&data.charAt(i)!='R'&&data.charAt(i)!='L'&&data.charAt(i)!='H')
					temp = temp+data.charAt(i);	
				}
				
				if (data.charAt(i)==' ')
				{
					if (Wlost == true)
					{
						for (int r = 0;r<WorkerPorts.size();r++)
						{
							if (WorkerPorts.elementAt(r)==Integer.parseInt(temp))
							{
								WorkerPorts.removeElementAt(r);
								break;
							}
						}
						Wlost = false;
						Wtime = false;
					}
					if (wlost == true)
					{

						for (int r = 0;r<WorkerIPs.size();r++)
						{

							if (WorkerIPs.elementAt(r).toString()=="/"+(temp))
							{
								
								WorkerIPs.removeElementAt(r);
								break;
							}
						}
						wlost = false;
						wtime = false;
					}
					else if (Wtime == true)
					{
						WorkerPorts.addElement(Integer.parseInt(temp));
						Wtime = false;
					}
					else if (wtime == true)
					{
						IPAddress = InetAddress.getByName(temp);
						WorkerIPs.addElement((IPAddress ));
						wtime = false;
					}
					else if (Rtime == true)
					{
						RequesterPort = Integer.parseInt(temp);
						Rtime = false;
					}
					
					else if (Htime == true)
					{
						Hash = temp;
						System.out.println(Hash);
						Htime = false;
						
					}
					
					temp = "";
					
					
				}
				if (data.charAt(i)=='w')
				{

			
					if (data.charAt(i+1)=='L')
					{
						wlost = true;
						wtime = true;
					}
					else
						wtime = true;
					temp = "";

				


				}
				if (data.charAt(i)=='W')
				{
					if (data.charAt(i+1)=='T')
					{
						
						if (data.charAt(i+2)=='1')
						{
							if (data.charAt(i+3)=='T')
								WorkTime1 = true;
							else
								WorkTime1 = false;
						}
						
						else
						{
							if (data.charAt(i+2)=='T')
								WorkTime = true;
							else
								WorkTime = false;
							
						}
						
					}
					else if (data.charAt(i+1)=='L')
					{
						Wlost = true;
						Wtime = true;
					}
					else
						Wtime = true;
					temp = "";
					
				}
				
				if (data.charAt(i)=='R')
				{
					temp ="";
					Rtime = true;
				}
				
				if (data.charAt(i)=='H')
				{
					temp ="";
					Htime = true;
				}
				
				
			}

			reading = true;
			}
			else
				reading = true;
	
		while (true)
		{
			WorkTime1 = WorkTime1;
			WorkerPorts.size();
		if (WorkTime1 == true&&WorkerPorts.size()>0)
		{
			String Character = "abcd*efghi=jklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
			double Range = Character.length()/WorkerPorts.size();
			int i1=0;
			int i2=0;

			int c = 0;
		/*	if (fault == true)
			{
				char p = Current_Processed_String.charAt(1);
				char e = Current_Start_String.charAt(0);
				
				for (int i=0;i<Character.length();i++)
				{
					if (p==Character.charAt(i))
						i1 = i;
					if (e==Character.charAt(i))
						i2 = i;
				}
				System.out.println(i1);
				c=i1;
				System.out.println(c);
				Range = (i2-i1)/WorkerPorts.size();
			}*/
			Range = Math.ceil(Range)-1;

			System.out.println(Range);
			String StartKey = new String();
			for (int i = 0 ; i < WorkerPorts.size(); i++ )
			{
			/*	if (fault == true) 
					 StartKey = Character.charAt(c) + Current_Processed_String.substring(2, Current_Processed_String.length());
				else*/
					StartKey = Character.charAt(c)+"aaaa";
				c += Range;
				if (i == WorkerPorts.size()-1 && c < Character.length()-1&&fault==false)
					c = Character.length()-1;
					
				String EndKey = Character.charAt(c) + "9999";
				c++;
				String ToCrack1 = "2."+StartKey + " " + EndKey +" " + Hash;
			    sendData = ToCrack1.getBytes();
			    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, WorkerIPs.elementAt(i) , WorkerPorts.elementAt(i));
			    serverSocket.send(sendPacket);
			    System.out.println(StartKey);
			    System.out.println(EndKey);
			}
			fault = false;
			WorkTime1 = false;
			String l = "WT1F ";
			FileOutputStream object=new FileOutputStream("a.txt",true);
			object.write(l.getBytes());
			object.close();
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


					while (true)
					{
						try
						{
						receiveData = new byte[1024];
						DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
						if (serverSocket!=null)
							serverSocket.receive(receivePacket);
						
						String Command = new String (receivePacket.getData());
						if (Command.charAt(0)=='1' && Command.charAt(1) =='.')
						{
							String tosend ="ACK";
							sendData = tosend.getBytes();
							IPAddress = receivePacket.getAddress();
							DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,IPAddress,receivePacket.getPort());
							serverSocket.send(sendPacket);
							Integer p =  receivePacket.getPort();
							String l = "W"+p.toString()+" ";
							FileOutputStream object=new FileOutputStream("a.txt",true);
							object.write(l.getBytes());
							
							object=new FileOutputStream("a.txt",true);

							l = "w"+IPAddress .toString()+" ";
							object.write(l.getBytes());
							object.close();
							boolean did = false;
							for (int i=0;i<WorkerPorts.size();i++)
								if (WorkerPorts.elementAt(i)==receivePacket.getPort())
									did = true;
							if (did == false)
							{
							System.out.println("Worker Connected!");
							boolean go = true;
							if (WorkTime == true)
							{
								if (Queue1.size()>0)
								{
									go = false;
									String StartKey = Queue1.elementAt(0).substring(1,  Queue1.elementAt(0).length());
									String EndKey = Queue2.elementAt(0);
									StartKey = StartKey.substring(0, 5);
									EndKey = EndKey.substring(0, 5);
									String ToCrack1 = "2."+StartKey + " " + EndKey +" " + Hash;
								    sendData = ToCrack1.getBytes();
								    sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress() , receivePacket.getPort());
								    serverSocket.send(sendPacket);
								    System.out.println(StartKey);
								    System.out.println(EndKey);
								    Queue1.removeElementAt(0);
								    Queue2.removeElementAt(0);
								    WorkerPorts.addElement(receivePacket.getPort());
								    WorkerIPs.addElement(receivePacket.getAddress());
									
								}
								else if (WorkerPorts.size()>1)
								{
								int t = WorkerPorts.elementAt(WorkerPorts.size()-1);
								WorkerPorts.removeElementAt(WorkerPorts.size()-1);
								InetAddress t1 = WorkerIPs.elementAt(WorkerIPs.size()-1);
								WorkerPorts.addElement(receivePacket.getPort());
								WorkerPorts.addElement(t);
								WorkerIPs.addElement(receivePacket.getAddress());
								WorkerIPs.addElement(t1);
								}
								else
								{
								WorkerPorts.addElement(receivePacket.getPort());
								WorkerIPs.addElement(receivePacket.getAddress());
								}
								if (go==true)
								{
								WorkTime1 = true;
								l = "WT1T ";
								object=new FileOutputStream("a.txt",true);
								object.write(l.getBytes());
								object.close();
								}
						   }
							else
								WorkerPorts.addElement(receivePacket.getPort());
							WorkerIPs.addElement(receivePacket.getAddress());
							}
							
						}
						
						else if (Command.charAt(0)=='4' && Command.charAt(1)=='.')
						{
							System.out.println(receivePacket.getPort() +" Found Nothing");
							if (WorkTime==true)
							{
							if (Queue1.size()>0)
							{
							    String StartKey = Queue1.elementAt(0).substring(1,  Queue1.elementAt(0).length());
							    String EndKey = Queue2.elementAt(0);
							    String ToCrack1 = "2."+StartKey + " " + EndKey +" " + Hash;
							    sendData = ToCrack1.getBytes();
							    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress() , receivePacket.getPort());
							    serverSocket.send(sendPacket);
							    System.out.println(StartKey);
							    System.out.println(EndKey);
							    Queue1.removeElementAt(0);
							    Queue2.removeElementAt(0);								
							}
							else
							{
								ArrayList<String> to = new ArrayList<String>();
								Random rand;
								int randomNum;
								while (true)
								{
								rand = new Random();
								 randomNum = rand.nextInt((WorkerPorts.size()-1) + 1);
								if (WorkerPorts.elementAt(randomNum)!=receivePacket.getPort())
									break;
								}
								System.out.println(WorkerPorts.elementAt(randomNum));
								System.out.println(receivePacket.getPort());
								to = Process.get(WorkerPorts.elementAt(randomNum) );
							if (to!=null&&!to.isEmpty())
							{
								
								String Character = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
								Current_Start_String = to.get(0);
								Current_Processed_String = to.get(1);
								char p = Current_Processed_String.charAt(1);
								char e = Current_Start_String.charAt(0);
								int c=0;
								int i1=0;
								int i2=0;
								for (int i=0;i<Character.length();i++)
								{
									if (p==Character.charAt(i))
										i1 = i;
									if (e==Character.charAt(i))
										i2 = i;
								}

								c=i1;

								double Range = (i2-i1)/WorkerPorts.size();
								Range = Math.ceil(Range)-1;
								String StartKey = Character.charAt(c) + Current_Processed_String.substring(2, Current_Processed_String.length());
								c+=Range;
								String EndKey = Character.charAt(c)+Current_Processed_String.substring(2, Current_Processed_String.length());
							 String ToCrack1 = "2."+StartKey + " " + EndKey +" " + Hash;
							    sendData = ToCrack1.getBytes();
							    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, WorkerIPs.elementAt(randomNum) , WorkerPorts.elementAt(randomNum));
							    serverSocket.send(sendPacket);
								 System.out.println(StartKey);
							    System.out.println(EndKey);


								StartKey = Character.charAt(c) + Current_Processed_String.substring(2, Current_Processed_String.length());

								EndKey = Current_Start_String;
							 ToCrack1 = "2."+StartKey + " " + EndKey +" " + Hash;
							    sendData = ToCrack1.getBytes();
							     sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress() ,receivePacket.getPort());
							    serverSocket.send(sendPacket);
							    System.out.println(StartKey);
							    System.out.println(EndKey);

								
							}
							}

						
						}}
						else if (Command.charAt(0)=='6')
						{
							ArrayList<String> toput= new ArrayList<String>();
							toput.add(Command.substring(2, 7));
							toput.add(Command.substring(7, Command.length()));
							System.out.println(Command.substring(7,Command.length()));
							Process.put(receivePacket.getPort(), toput);
							is=1;
						}
						else if (Command.charAt(0)=='5')
						{
						    sendData = Command.getBytes();
						    System.out.println(RequesterPort);
						    DatagramPacket sendPacket;
						    if (RequesterPort>0)
						    {
						     sendPacket = new DatagramPacket(sendData, sendData.length, RequesterIP , RequesterPort);
						    if (serverSocket!=null)
						    serverSocket.send(sendPacket);
						    }
							if (RequesterPorts.size()>0)
							RequesterPorts.removeElementAt(0);
							if (HashQueue.size()>0)
							HashQueue.removeElementAt(0);
							if (RequesterIPs.size()>0)
							RequesterIPs.removeElementAt(0);
							for (int i=0;i<WorkerPorts.size();i++)
							{
								  sendData = "7".getBytes();
								    sendPacket = new DatagramPacket(sendData, sendData.length, WorkerIPs.elementAt(i) , WorkerPorts.elementAt(i));
								    if (serverSocket!=null)
								    serverSocket.send(sendPacket);
								
							}
							if (RequesterPorts.size()>0)
							{

							System.out.println("Sending Job!");
							RequesterPort=RequesterPorts.elementAt(0);
							RequesterIP=RequesterIPs.elementAt(0);
							Hash = HashQueue.elementAt(0);
							fault = false;
							WorkTime = true;
							WorkTime1 = true;
							String l="WTT ";
							FileOutputStream object1=new FileOutputStream("a.txt",true);
							object1.write(l.getBytes());
							l="WT1T ";
							object1.write(l.getBytes());
							l = "H" + HashQueue.elementAt(0) +" ";
							object1.write(l.getBytes());
							object1.close();
							}
						
						}
						else if (Command.charAt(0)=='3')
						{
						    sendData = Command.getBytes();
						    System.out.println(RequesterPort);
						    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, RequesterIP , RequesterPort);
						    if (serverSocket!=null)
						    serverSocket.send(sendPacket);
														
						
						}
						else 
						{
							FileOutputStream object1=new FileOutputStream("a.txt",true);
							Integer p = receivePacket.getPort();
							String l = "R" + p +" ";
							object1.write(l.getBytes());
							System.out.println(Command);
							if (RequesterPorts.size()==0)
							{
							Hash = Command;
							l = "H" + Command +" ";
							object1.write(l.getBytes());
							HashQueue.addElement(Command);
							RequesterPorts.addElement(receivePacket.getPort());
							RequesterIPs.addElement(receivePacket.getAddress());
							RequesterPort = RequesterPorts.elementAt(0);
							RequesterIP = RequesterIPs.elementAt(0);

							System.out.println("added");
							WorkTime = true;
							WorkTime1 = true;
							l="WTT ";
							object1.write(l.getBytes());
							l="WT1T ";
							object1.write(l.getBytes());
							object1.close();
							}
							else
							{
								HashQueue.addElement(Command);
								RequesterPorts.addElement(receivePacket.getPort());
								RequesterIPs.addElement(receivePacket.getAddress());
								RequesterPort = RequesterPorts.elementAt(0);
								RequesterIP = RequesterIPs.elementAt(0);
							}
							//System.out.println("asbb");
						
						}
							
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					
				}
				
				
				
				
			}}).start();
		new Thread(new Runnable() {
			public void run() {
				try
				{
					
					while (true)
					{
						//System.out.println(reading);
					//	System.out.println(WorkerPorts.size());
						if (reading == true)
					for (int i=0; i<WorkerPorts.size();i++)
					{
						
						sendData = new byte[1024];
						sendData = "0".getBytes();
						IPAddress = WorkerIPs.elementAt(i);
						DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,IPAddress,WorkerPorts.elementAt(i));
						if (serverSocket!=null)
						serverSocket.send(sendPacket);
						receiveData = new byte[1024];
						n=1;
						long start = System.currentTimeMillis();
						long end = start + 3*1000; 
						while (System.currentTimeMillis() < end)
						{
				
				
					    }
					
						if (is==0)
						{
							System.out.println(is);
							ArrayList<String> to = new ArrayList<String>();
							to = Process.get(WorkerPorts.elementAt(i));
							if (to!=null&&!to.isEmpty())
							{
							Current_Start_String = to.get(0);
							Current_Processed_String = to.get(1);
							Queue1.addElement(Current_Processed_String);
							Queue2.addElement(Current_Start_String);
							System.out.println("pr "+Current_Processed_String);
							System.out.println("End "+Current_Start_String);
							}
							fault = true;
							System.out.println("Fault with Worker Detected!" );
							String l = "WL" + WorkerPorts.elementAt(i)+ " ";
							FileOutputStream object=new FileOutputStream("a.txt",true);
							object.write(l.getBytes());
							l = "wL" + WorkerIPs.elementAt(i)+ " ";
							object=new FileOutputStream("a.txt",true);
							object.write(l.getBytes());
							object.close();
								
                            WorkerPorts.removeElementAt(i);
                            WorkerIPs.removeElementAt(i);
                              
                            
						}
						else 
							is=0;
					}
					
					}
					
				}
				
				catch (Exception e)
				{
					e.printStackTrace();
				}
			
				
			}}).start();

		
		
	}
	
	

}
