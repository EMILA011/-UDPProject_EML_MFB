/**
 * This program demonstrates how to implement a UDP client program.
 * It will request quotations from the UDP Server until the user enters END. 
 * Then it receives the response and prints it in a console. 
 * @author Elena Milan Lopez
 * @author Michael F. Brizendine
 * @version 1.0
 * UDP Project - Solution
 * SP20
 */

import java.io.*;
import java.net.*;
import java.util.*; 

public class UDPClient {
	
	public static void main (String[] args) throws IOException {
		
        //Take input from the user
        
        Scanner sc = new Scanner(System.in);
        
     // Create the socket object for carrying the data. 
		
        DatagramSocket ds = new DatagramSocket();
        
        //ip address
        
        String ipAddress = args[0];
        InetAddress address = InetAddress.getByName(ipAddress);
        
        //port number
        
        int port = Integer.parseInt(args[1]);
        
        
        byte[] b = null;
        
        //Loop while user not enters "END"
        
        while(true) {
        	
        	
        	System.out.print("Enter a command: ");
        	
        	//Convert data into byte array.
        
        	String inp = sc.nextLine();
        	b = inp.getBytes();
        	
        	
        	//Create the datagramPacket for sending the data to the server.
            
            DatagramPacket dp = new DatagramPacket(b, b.length, address, port);
            
            //Send the data. 
            
            ds.send(dp);
            
            //Accept data
            
            byte[] bb = new byte[1024];
            DatagramPacket dp2 = new DatagramPacket(bb, bb.length);
            ds.receive(dp2);
            
          
            //Get data and print data
            
            String str = new String(dp2.getData());
            System.out.println();
            System.out.println(str);
            System.out.println();
            
            //Break the loop if user enters "END"
            
        	if (inp.equals("END")) { 
        		
            	break; 
            }
        	
        	
        	
        }//while
        
	}//main
	

}//UDPClient
