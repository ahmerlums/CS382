// To run: java Client <IP> <port>
// e.g. java Client localhost 8822

import java.util.*;
import java.net.*;
import java.io.*;

public class Client {
	static Integer port2 = null;
	static String input = null;
	static String t = null;
	static Integer port4=null;

	public static void main(String args[]) {
		// Takes command line arguments
		Scanner b = new Scanner(System.in);
		System.out.println("Enter Port:");
		port2 = b.nextInt();
		final String p2 = port2.toString();
		
		final int port = 8822;
		final String ip = "localhost";
		//Integer i=1;
		new Thread(new Runnable() {
			Socket sock = null;

			public void run() {
				try {
				while (true)
				{
						Scanner a = new Scanner(System.in);
						t = a.nextLine();
						sock = new Socket(ip, port);
						PrintWriter pw = new PrintWriter(sock.getOutputStream(),
								true);
					
						pw.println(p2);
						InputStreamReader isr = new InputStreamReader(sock
								.getInputStream());
						BufferedReader br = new BufferedReader(isr);

						if (t.charAt(0) == 'P' && t.charAt(1) == 'u'
								&& t.charAt(2) == 't') {
							int i = 4;
							String temp = "";
							while (true) {
								if (t.charAt(i) == ' ')
									break;
								temp = temp + t.charAt(i);
								i++;
							}
							FileWriter saveFile = new FileWriter(temp + ".txt");
							String temp1 = "";

							for (i = i + 1; i < t.length(); i++) {
								temp1 = temp1 + t.charAt(i);
							}

							saveFile.write(temp1);
							saveFile.close();

							pw.println(t);
							input = br.readLine();
							System.out.println(input);

						}

						if (t.charAt(0) == 'L' && t.charAt(1) == 'o'
								&& t.charAt(2) == 'o' && t.charAt(3) == 'k'
								&& t.charAt(4) == 'u' && t.charAt(5) == 'p') {
					
							pw.println(t);
							input = br.readLine();
							System.out.println(input);

						}
						
						if (t.charAt(0) == 'G' && t.charAt(1) == 'e'
								&& t.charAt(2) == 't') {
							pw.println(t);
							input = br.readLine();
							System.out.println(input);
							port4=Integer.parseInt(input);
							Socket clientconnection = new Socket("localhost",
									port4);
							PrintWriter pw1 = new PrintWriter(clientconnection
									.getOutputStream(), true);
							InputStreamReader isr1 = new InputStreamReader(
									clientconnection.getInputStream());
							BufferedReader br1 = new BufferedReader(isr1);
							pw1.println("Find" + t.substring(3, t.length()));
							String tostore = br1.readLine();
							System.out.println(tostore);
							FileWriter writer = new FileWriter(t.substring(3, t.length()) + port4+"copy.txt");
							writer.write(tostore);
							writer.close();
						
						}
						sock.close();

					}
				} catch (Exception e) {
					// Exception printed on console in case of error
					e.printStackTrace();
				} finally {
					try {
						sock.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Connection Closed");
				}

			}

		}).start();

		new Thread(new Runnable() {
			public void run() {
				try {
					ServerSocket Waiter = new ServerSocket(port2);
					while(true)
					{
					Socket clientconnection = Waiter.accept();

					PrintWriter pw = new PrintWriter(
							clientconnection.getOutputStream(), true);
					InputStreamReader isr = new InputStreamReader(
							clientconnection.getInputStream());
					BufferedReader br = new BufferedReader(isr);

					String get = br.readLine();
					String toget = "";

					for (int i = 5; i < get.length(); i++) {
						toget = toget + get.charAt(i);
					}
					String fileName = toget + ".txt";

					FileReader fileReader = new FileReader(fileName);

					BufferedReader bufferedReader = new BufferedReader(
							fileReader);
					String line = null;
					while ((line = bufferedReader.readLine()) != null) {
						System.out.println(line);

						pw.println(line);
					}
					bufferedReader.close();

					}
				}

				catch (Exception e) {
					e.printStackTrace();
				}
			}

		}).start();

	}
}
