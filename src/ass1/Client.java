/**
 * 
 */
package ass1;

import java.net.*;
import java.io.*;
import java.util.Random;
/**
 * @author samuel
 *
 */
public class Client {
	DatagramSocket sendReceiveSocket;
	DatagramPacket sendPacket,receivePacket;

	
	public Client() {
		
		try {sendReceiveSocket = new DatagramSocket();} catch (SocketException se) {   // Can't create the socket.
	         se.printStackTrace();
	         System.exit(1);
	      } }
	
	public void sendAndReceive() throws UnknownHostException
	{
		
		
			
	System.out.println("Generating Random String.\n");
	String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	String randomString = "";
	int size = 20;
	
	Random rand = new Random();
	char[] text = new char[size];
	
	for (int i = 0;i < size;i++) 
	{text[i] = characters.charAt(rand.nextInt(characters.length()));}
	
	for (int i = 0 ;i<text.length;i++) {
		randomString += text[i];
		}
	
	System.out.println("Complete!! Here is the result: "+randomString+"\n");
	
	
	
	byte [] data = randomString.getBytes();
	
	sendPacket = new DatagramPacket(data,data.length,InetAddress.getLocalHost(),5100);
	

	
	System.out.println("Client: Sending packet:");
    System.out.println("To host: " + sendPacket.getAddress());
    System.out.println("Destination host port: " + sendPacket.getPort());
    int len = sendPacket.getLength();
    System.out.println("Length: " + len);
    System.out.print("Containing: ");
    System.out.println(new String(sendPacket.getData(),0,len));
    
    
	try {
		sendReceiveSocket.send(sendPacket);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	byte[] receivedata = new byte[1024];
	receivePacket = new DatagramPacket (receivedata,receivedata.length);
	try {
		sendReceiveSocket.receive(receivePacket);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	 System.out.println("Client: Packet received:");
     System.out.println("From host: " + receivePacket.getAddress());
     System.out.println("Host port: " + receivePacket.getPort());
     len = receivePacket.getLength();
     System.out.println("Length: " + len);
     System.out.print("Containing: ");
	 String str = new String (receivePacket.getData());

	System.out.println("Data with Vowels Removed " + str);
//	sendReceiveSocket.close();
		}
	
	
		
	
	
	public static void main(String args[]) throws Exception
	   {
			Client c = new Client();
			c.sendAndReceive();  
	   }
	
	
}
