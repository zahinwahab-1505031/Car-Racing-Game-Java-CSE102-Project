/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carrace4random;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client2  {
	String identity;
	String name;
	int clientPort;
	String serverIP;
	String via;
	Thread t;
	
static	DatagramSocket socketReceiver;
static	DatagramSocket socketSender;
	
	DatagramPacket sendingPack;
	InetAddress IPAddress;
	String OName;
	public Client2( String name, int clientPort, String serverIP, String via ) throws SocketException, UnknownHostException {
		this.name = name;
		this.clientPort = clientPort;
		this.serverIP = serverIP;
		this.via = via;
             //   this.OName=OName;
		this.identity = "Via: " + via +'\n'+ "To: " + via +'\n'+ "From: " + name +'\n'+ "Port: " + clientPort;
		     try {         
			IPAddress = InetAddress.getByName( serverIP );
			
			socketSender = new DatagramSocket();
			byte[] data = identity.getBytes();
			sendingPack = new DatagramPacket( data, data.length, IPAddress, 4545 );
			socketSender.send( sendingPack );
                        socketReceiver = new DatagramSocket(clientPort);
                    
			
		} catch (Exception e) {
			
		}
		
	}

    
//	
//	public void run() {
//		try {
//			SenderThread senderThread = new SenderThread( socketSender, name, via, IPAddress, 4545,OName);
//			ReceiverThread receiverThread = new ReceiverThread( socketReceiver, name, via, IPAddress );
//			senderThread.t.join();
//			receiverThread.t.join();
//		} catch (Exception e) {
//			
//		}
//	}
	

}