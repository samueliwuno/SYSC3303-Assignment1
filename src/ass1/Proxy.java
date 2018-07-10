/*
 *  @Author: Caleb & Samuel
 * 
 * 
 * 
 */

import java.net.*;
import java.io.*;
import java.util.*;

public class Proxy {
	
	static DatagramSocket sendReceiveSocket;
	static DatagramSocket receiveSocket;
	DatagramPacket receivePacket, sendPacket;

	public Proxy()
	   {
	      try {
	         // Construct a datagram socket and bind it to any available 
	         // port on the local host machine. This socket will be used to
	         // send UDP Datagram packets.
	         receiveSocket = new DatagramSocket(5100);
	         sendReceiveSocket = new DatagramSocket();

	         // to test socket timeout (2 seconds)
	         //receiveSocket.setSoTimeout(2000);
	      } catch (SocketException se) {
	         se.printStackTrace();
	         System.out.println("Host-SocketException");
	        // System.exit(1);
	      } 
	   }
	
	public void RelayPacket() 
	{
		int clientport = 0;
	while(true) {
	byte[] data = new byte[2048]; 
	
	receivePacket = new DatagramPacket(data,data.length);
	System.out.println("Proxy: Waiting for Packet.\n");
	
	try {
		System.out.println("waiting...");
		receiveSocket.receive(receivePacket);
	} catch (IOException e) {
		System.out.print("IO Exception: Likely:");
		System.out.println("Received Socket Timed Out.\n" + e);
		e.printStackTrace();
		System.exit(1);
	}
	//processing the packet received
	System.out.println("Proxy: Packet received.");
	System.out.println("From host - "+receivePacket.getAddress());
	System.out.println("At port - "+receivePacket.getPort());
	int len = receivePacket.getLength();
	System.out.println("Length: " + len);
    System.out.print("Containing: " );
    String received = new String(data,0,len);   
    System.out.println(received );
    
   
 
	
    /* client packets are no more that 512 bytes, and server packets are no more than 1024
     * if statement below is used to differentiate whether a packet is coming from the server or client
     */
  //prepare packet to either send to server or client
	if(receivePacket.getLength() <= 520)  
	{
		int serverPort;
		boolean TFport;			// boolean to decide on which server to  send the packet
		Random rand = new Random();
		TFport = rand.nextBoolean();
		if(TFport==true){
			serverPort = 68;
			System.out.println("Proxy: relaying Packet to Server 2");
		}else{
			serverPort = 69;
			System.out.println("Proxy: relaying Packet to Server 1");
		}
		sendPacket = new DatagramPacket(data,receivePacket.getLength(),receivePacket.getAddress(),serverPort);
		clientport = receivePacket.getPort();
		
		
	} 
	else {
		sendPacket = new DatagramPacket(data,receivePacket.getLength(),receivePacket.getAddress(),clientport);
		System.out.println("Proxy: relaying Packet to Client");
		
	}
	
	//giving details of Packets being sent
	System.out.println("at address: "+sendPacket.getAddress());
	System.out.println("and port: "+sendPacket.getPort());
	len = sendPacket.getLength();
	System.out.println("Length: " + len);
    System.out.print("Containing: ");
    System.out.println(new String(sendPacket.getData()));
	
    try {
		sendReceiveSocket.send(sendPacket);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
    System.out.println("Proxy: Process Complete");
   
	}
	
	
	}
	
	public static void main(String args[]) throws Exception
	   {
			Proxy p = new Proxy();
			p.RelayPacket();
			receiveSocket.close();
			sendReceiveSocket.close();
	   }
	
}