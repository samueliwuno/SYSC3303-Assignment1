/**
 * 
 */


import java.net.*;
import java.io.*;
import java.util.Random;
/**
 * @author Samuel & Caleb
 *
 */
public class Client {
	static DatagramSocket sendReceiveSocket;
	DatagramPacket sendPacket,receivePacket;
	static long initialTime,finalTime;
	static double timeTotal;
	
	public Client() {
		
		try {sendReceiveSocket = new DatagramSocket();} catch (SocketException se) {   // Can't create the socket.
	         se.printStackTrace();
	         System.exit(1);
	      } }
	
	public void sendAndReceive() throws UnknownHostException
	{
		
		
	//Generate  Random String or char characters of size 20
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
	
	
	 byte[] data = new byte[512];					//initializes byte to be sent with the datagram socket
	data = randomString.getBytes();					// copies generated string to byte
	
	sendPacket = new DatagramPacket(data,data.length,InetAddress.getLocalHost(),5100);
	

	// sends Packet to Proxy Server on given Port
	System.out.println("Client: Sending packet:");
    System.out.println("To host: " + sendPacket.getAddress());
    System.out.println("Destination host port: " + sendPacket.getPort());
    int len = sendPacket.getLength();
    System.out.println("Length: " + len);
    System.out.print("Containing: ");
    System.out.println(new String(sendPacket.getData(),0,len));
    System.out.println("");
    
    
	try {
		initialTime = System.currentTimeMillis();
		sendReceiveSocket.send(sendPacket);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	byte[] receivedata = new byte[1024];  // initials byte that was received from Proxy
	receivePacket = new DatagramPacket (receivedata,receivedata.length);
	try {
		sendReceiveSocket.receive(receivePacket);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	// packet received from Proxy is Processed
	 System.out.println("Client: Packet received:");
     System.out.println("From host: " + receivePacket.getAddress());
     System.out.println("Host port: " + receivePacket.getPort());
     len = receivePacket.getLength();
     System.out.println("Length: " + len);
     String str = new String (receivePacket.getData()); // byte data translated to String

	System.out.println("Data with Vowels Removed " + str);
	finalTime = System.currentTimeMillis();
		}
	
	
		
	
	
	public static void main(String args[]) throws Exception
	   {
			Client c = new Client();
			for(int i=1;i<101;i++){  // repeats entire process 100 times
				c.sendAndReceive();
				System.out.println("Completed process " + i + " out of 100.\n");
				
			}
			timeTotal = (finalTime-initialTime);
			System.out.println("The total time is: " + timeTotal + " Seconds\n");
			sendReceiveSocket.close();  	//socket closed after entire Process
	   }
	
	
}