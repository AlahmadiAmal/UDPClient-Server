package Project;

/*
The implementation of a simple client server ping pong application. 
Clients send ping messages to a server. 
Whereas the server answers with a pong. 
UDP transport protcol 
 */

/**
 Amal Khalid Alahmadi
 Amal Abdullah AlMohawes
 Hajer Khalid Altuwijri
 Jawaher Saud Abuhaimed
 */

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;



public class UdpServer
{
  
  public static void main(String [] args) //or try catch
   throws SocketException, IOException, InterruptedException, Exception {
   
   int port = 8000;// port number 
   DatagramSocket socket = new DatagramSocket(port); //create listening socket
   
   while(true){ //receive 
      byte receive[] = new byte[1024]; // create the receive buffer
      // create packet
      DatagramPacket receivedQuery = new DatagramPacket(receive,receive.length); 
      socket.receive(receivedQuery); // recieve the packet from client
		 
      printData(receivedQuery);//print 


//for replying to client  
      InetAddress clintHost = receivedQuery.getAddress(); //get client address
      int clientPort =receivedQuery.getPort();//get client port
      byte[] buf = receivedQuery.getData();//get client data
      DatagramPacket reply =new DatagramPacket(buf,buf.length,
              clintHost,clientPort);//create reply packet
		 socket.send(reply);// send reply to client
		 System.out.println("Reply send");	 
       }
	  
  }//end main
  
  public static void printData(DatagramPacket receivedQuery) throws Exception {
       // extract data from packet
      byte[] buf = receivedQuery.getData(); 
      
	  ByteArrayInputStream bais = new ByteArrayInputStream(buf);
	  
	  InputStreamReader isr;
          isr = new InputStreamReader(bais);
	  
	  BufferedReader br = new BufferedReader(isr);
	  
	  String line;
          line = br.readLine();
	  // printing the data 
	  System.out.println( "Received From "  + receivedQuery.getAddress().getHostAddress() + ": " + line); 
	   }
}//end of class server


