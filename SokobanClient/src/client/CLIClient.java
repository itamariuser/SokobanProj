package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class CLIClient {
	private BufferedReader reader;
	private PrintWriter writer;
	private String exitString;

		
	public CLIClient(BufferedReader reader, PrintWriter writer, String exitString) {
		this.reader = reader;
		this.writer = writer;
		this.exitString = exitString;
	}
	
		public static void start(String ip, int port){
			try {
				Socket theServer=new Socket(ip, port);
				System.out.println("Connecting to server....");
				Scanner userInput=new Scanner(new InputStreamReader(System.in));
				Scanner serverInput=new Scanner(new InputStreamReader(theServer.getInputStream()));
				PrintStream outToServer=new PrintStream(theServer.getOutputStream());
				Thread t=new Thread(new Runnable() {
					public void run() { 
						String line = null;
						if(theServer.isConnected())
						{
							System.out.println("...");
						}
						try {
							line = serverInput.nextLine();
							while(theServer.isConnected())
							{
								System.out.println(line);
								System.out.print("Please enter command to send: ");
								line = userInput.nextLine();
								outToServer.println("Command sent -> " + line);
								System.out.println("The command has been sent");
								line = serverInput.nextLine();
								if(serverInput.equals(""))
								{
									System.out.println("Server response:\n" + serverInput.nextLine());
								}
								outToServer.flush();
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				t.start();
				t.join();
				userInput.close();
				outToServer.close();
				theServer.close();
				} catch (UnknownHostException e) {}
				catch (IOException e) {/*...*/}
				catch (InterruptedException e) {/*...*/}
			}

}
