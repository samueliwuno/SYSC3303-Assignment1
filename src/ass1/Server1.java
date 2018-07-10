/*
 *  @Author: Caleb & Samuel
 * 
 * 
 * 
 */

import java.net.*;
import java.io.*;


public class Server1 {
	
	DatagramPacket sendPacket, receivePacket;
	static DatagramSocket sendSocket;
	static DatagramSocket receiveSocket;
	
	public Server1()
	{
	      try {
	         // Construct a datagram socket and bind it to any available 
	         // port on the local server machine. This socket will be used to
	         // send UDP Datagram packets.
	         receiveSocket = new DatagramSocket(69);
	         sendSocket = new DatagramSocket(67);
	         // to test socket timeout (2 seconds)
	         //receiveSocket.setSoTimeout(2000);
	      } catch (SocketException se) {
	         se.printStackTrace();
	         System.exit(1);
	      } 
	   }
	
	public void receiveAndEcho () {
				//while loop enables server to always receive, process and send the packet until there is no packet left
				while(true) {         
		      
				byte[] data = new byte[1024];
				DatagramPacket receivePacket, sendPacket;
				receivePacket = new DatagramPacket(data,data.length);
				System.out.println("Server1: Waiting for Packet.\n");
				try {
					receiveSocket.receive(receivePacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.exit(1);
				}
				// removes all the vowels in the data received
				String str = new String (receivePacket.getData());
				System.out.println("Server1: Received -  "+ str);
				str = str.replaceAll("[AEIOUaeiou]", "");
				System.out.println("Server1: Performing Operation........done");
				System.out.println("Server1: Result - "+ str);
				
				
				System.out.println("new Values "+ str);
				byte[] senddata = new byte[1024];
				senddata = str.getBytes();
				InetAddress ia = null;
				try {
					ia = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.exit(1);
				}
				
				  
				sendPacket = new DatagramPacket(senddata,senddata.length,ia,5100);
				
				  System.out.println( "Server: Sending packet:");
			      System.out.println("To host: " + sendPacket.getAddress());
			      System.out.println("Destination host port: " + sendPacket.getPort());
			      int len = sendPacket.getLength();
			      System.out.println("Length: " + len);
			      System.out.print("Containing: ");
			      System.out.println(new String(sendPacket.getData(),0,len));
			      System.out.println("");
				try {
					sendSocket.send(sendPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.exit(1);
				}
				System.out.println("Process Complete");
				System.out.println("");
				
				}
	}
	
	public static void main (String [] args) throws Exception
	{
		Server1 s = new Server1();
		s.receiveAndEcho();
		sendSocket.close();
		receiveSocket.close();
	}

}