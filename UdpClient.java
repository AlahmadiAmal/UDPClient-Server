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


public class UdpClient{

public static void main(String[] args) throws Exception {
        
  String ServerName = "192.168.100.125";// IP address of the server
  int port = 8000;// port number 
               
  DatagramSocket socket = new DatagramSocket();//create socket
  InetAddress SrverIPAddress = InetAddress.getByName(ServerName);//connect to server via ip address
          
  int sequence_number = 1;// seq num of first messsage  
          
  while(sequence_number < 11 ){ // repeat for 10 message   
              
  long sendTime = System.currentTimeMillis(); //time of sending 
              
         
  String  strPing = "PING" + sequence_number + " " + sendTime + "\n";
          
   byte[] sendbuf = new byte[1024];// create send buffer 
   sendbuf = strPing.getBytes();// add segment  
   
           // create packet for the segment    
   DatagramPacket ping = new DatagramPacket(sendbuf,sendbuf.length, SrverIPAddress, port);
     
   socket.send(ping);//send to server 
              
     try{ // for timeout 
           socket.setSoTimeout(1000);// 1 second 
              
           byte[] receivebuf = new byte[1024];// create receive buffer
           DatagramPacket response = new DatagramPacket(receivebuf, receivebuf.length); 
              
           socket.receive(response);// get reply
              
           long msReceived = System.currentTimeMillis();// time of arrival 
              
           long RTT = msReceived - sendTime;// calculate Round Trip Time  
              
           printData(response, RTT);
              
        }
     catch(IOException ex)// if there is a delay to receive the reply 
              {
             System.out.println("Timeout for packet " + sequence_number);
              
              }
              
              sequence_number++;// increment next message 
           }
    }//end of main
 
private static void printData(DatagramPacket response, long delayTime) throws Exception {
       // extract data from packet
      byte[] buf = response.getData();
      
      ByteArrayInputStream bais = new ByteArrayInputStream(buf);
      
      InputStreamReader isr = new InputStreamReader(bais);
      
      BufferedReader br = new BufferedReader(isr);
      
      String line = br.readLine();
       // printing the data 
      System.out.println("Received from " + response.getAddress().getHostAddress() + ": " + new String(line) + " RTT= " + delayTime+ " \n");
      
      }
    }//end of class client
