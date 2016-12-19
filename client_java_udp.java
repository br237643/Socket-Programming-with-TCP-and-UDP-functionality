/**
*DETAILS OF CODE 
*The code of initialising the Datagramsocket and then connection and sending the data *to client has been taken from coderanch.com, Briyan Ringers author
*What i have learned from it is the socket concept and now i think i would be *able to make new sockets with different modularities
*This specific author has wrote simple socket programs for understanding.
*/




import java.io.*;
import java.net.*;

class client_java_udp
{ 
    public static void main(String args[]) throws Exception
    {
    String a;
    int b=0;
//**For the server name**
//**storing the name in l
//** will be used to pass in datagrampacket sending
        System.out.println("Enter the server name or IP address");
         BufferedReader std = new BufferedReader(new InputStreamReader(System.in));
                String l=std.readLine();
               if(l.contains("localhost")){
//**To store the port and use to pass while sending the datagrampacket
    	System.out.println("Enter the port number");
      // BufferedReader inFromUser =new BufferedReader(new InputStreamReader(System.in));
       BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                 a=stdIn.readLine();
                 b=Integer.parseInt(a);
                  if(b>0 || b<65535){
                     if(a.contains("3300")){
       DatagramSocket clientSocket = new DatagramSocket();
       InetAddress IPAddress = InetAddress.getByName(l);
       byte[] sendData = new byte[1024];
       byte[] receiveData = new byte[1024];
       System.out.println("Enter the expression length");
       BufferedReader FromUser =new BufferedReader(new InputStreamReader(System.in));
       String length = FromUser.readLine();
       sendData=length.getBytes();
        DatagramPacket sendPacket2 = new DatagramPacket(sendData, sendData.length, IPAddress, b);
       clientSocket.send(sendPacket2);
       System.out.println("Enter the expression");
       BufferedReader inFromUser =new BufferedReader(new InputStreamReader(System.in));
       String sentence = inFromUser.readLine();
       
       sendData = sentence.getBytes();
       DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, b);
       clientSocket.send(sendPacket);
       //** receives a packet of data**//
       DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
       clientSocket.receive(receivePacket);
       String modifiedSentence = new String(receivePacket.getData());
       //System.out.println("FROM SERVER:" + modifiedSentence);
       DatagramPacket receivePacket1 = new DatagramPacket(receiveData, receiveData.length);
       clientSocket.receive(receivePacket1);
       String result = new String(receivePacket1.getData());
       System.out.println(result);
       System.out.println("FROM SERVER:" + modifiedSentence);
       clientSocket.close();

       } 
         else{ 
          System.out.println("Couldn't connect to " + b +"port");
            }
       }
        else 
        System.out.println("Invalid port number. Terminating");
       }
        else 
         System.out.println("Could not connect to server. Terminating.");
       
    }
} 