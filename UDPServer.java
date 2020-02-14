/**
 * This program demonstrates how to implement a UDP server program.
 * It will listens for requests in a loop and serve up quotations about programming. 
 * Then sends a response to the client. 
 * @author Elena Milan Lopez
 * @author Michael F. Brizendine
 * @version 1.0
 * UDP Project - Solution
 * SP20
 */

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
	
public class UDPServer {
	
	
	 private DatagramSocket ds;
	 private static ArrayList<String> questions = new ArrayList<String>();
	 
	 LocalDateTime now = LocalDateTime.now();
	 
	 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mma");
	 String time = dtf.format(now).toLowerCase();
	
	 DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
	 DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mma"); 
	   
 	
	
	 /*
	  * Constructor that will start server with given port number
	  */
	 
	 public UDPServer(int port) throws SocketException {
		 
         ds = new DatagramSocket(port);
        
     }//UDPServer
	 
	 
	public static void main (String[] args) throws IOException  {
		
		//Create a socket to listen at port 2020
    	UDPServer server = new UDPServer(5000);
    	//Load quotes from file
        server.loadQuotes("quotes.csv");
        server.service();
   
        
	}//main
	
	/*
	 * This method will 
	 */
	
	private void service() throws IOException {
		
		byte[] bb = new byte[1024];
		
		DatagramPacket dp = null;
		
		System.out.println("Server Started at " + time + " on " + dtf1.format(now) + ".");
	
        while (true) {
        	
        	//Accept data from the client
        	
            dp = new DatagramPacket(bb, bb.length);
            ds.receive(dp);
        
            byte [] bbb;	
           
            InetAddress clientAddress = dp.getAddress();
           
            int clientPort = dp.getPort();
            
            //If the user enters <END>, let user know client closed 
        	
            if (data(bb).toString().equals("<END>")) 
            { 
           	
            	String end = "Client Closed";
            	bbb= end.getBytes();
      
            	DatagramPacket dp2 = new DatagramPacket(bbb, bbb.length,clientAddress,clientPort);
            	ds.send(dp2);
          
           } 
           
           else {
        	   
        	   String quote = getRandomQuote();
        	   bbb = quote.getBytes();
           
        	   //Send data back to client 
            
        	   DatagramPacket dp2 = new DatagramPacket(bbb, bbb.length,clientAddress,clientPort);
        	   ds.send(dp2);
 
        	   System.out.println("Request Received from " + dp.getAddress().getHostAddress() + ": " + dp.getPort() + " " + dtf2.format(now));
             
          }
            
        }//while 
        
        
      }//service
       

	/*
	 * This method will read the quote file and add each quote to an array of strings 
	 */
	
	private void loadQuotes(String quoteFile) throws IOException {
		
        BufferedReader file = new BufferedReader(new FileReader(quoteFile));
        String quote;
        
        while ((quote = file.readLine()) != null) {
        	
            questions.add(quote);    
        }
       
       
        file.close();
        
    }//loadQuotes()
	
	/*
	 * This method will print a random quote from a list of quotes. 
	 */
	
	private String getRandomQuote() {
        
        int index = new java.util.Random().nextInt(questions.size());
    	String value = questions.get(index);
        return value;
        
    }//getRandomQuote()
	
	/*
	 * This method will convert the byte array data into a string representation.
	 */
	
	public static StringBuilder data(byte[] a) 
    { 
        if (a == null) 
            return null; 
        StringBuilder ret = new StringBuilder(); 
        int i = 0; 
        while (a[i] != 0) 
        { 
            ret.append((char) a[i]); 
            i++; 
        } 
        return ret;
    }//data()
	
	
}//UDPServer
