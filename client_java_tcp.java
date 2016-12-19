/**
*DETAILS OF CODE 
*The code of initialising the Datagramsocket and then connection and sending the data *to client has been taken from coderanch.com, Briyan Ringers author
*What i have learned from it is the socket concept and now i think i would be *able to make new sockets with different modularities
*This specific author has wrote simple socket programs for understanding.
*/



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * Command line file client which also contains runnable main method
 * 
 * @author srinivas
 *
 */
public class client_java_tcp implements Runnable{
	int port; 				// port to conenct to server
	private Socket socket;  
	Thread thread;
	
	/**
	 * Starts ClientSocket at given port number on initialization of class.
	 * Starts thread
	 * @param port number to use for socket connection. 
	 */
	public client_java_tcp (int port) {
		try {
			socket = new Socket("localhost", port);
			thread = new Thread(this);
			thread.start();
			while(true){
				sendInput();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sends whatever input user enters in command lin client
	 * @throws IOException
	 */
	public void sendInput() throws IOException{
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
		String fromUser;
		
		fromUser = stdIn.readLine();
		if (fromUser != null) {
			out.println(fromUser);
		}
	}
	
	/**
	 * Thread's run method which is used to display whatever server sends instantly.
	 * 
	 */
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			String fromServer;
			while ((fromServer = in.readLine()) != null ) {
				System.out.println(fromServer);
				if (fromServer.equals("Timeout"))
					System.exit(1);					// quit command has been acknowledged
			}
		} catch (IOException e) {
			e.printStackTrace();
}
         
	}
	

	/**
	 * Main method which will instantiate a Client with a given port number.
	 * 
	 * @param arguments
	 */
	public static void main(String[] arguments) {
               System.out.println("Client");
               String a;
               int b=0;
                try{
                System.out.println("Enter the IP address or the Server name");
                BufferedReader std = new BufferedReader(new InputStreamReader(System.in));
                String l=std.readLine();
               if(l.contains("localhost")){                 
                System.out.println("Enter the port number ");
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                 a=stdIn.readLine();
                 b=Integer.parseInt(a);
                   if(b>0 && b<65535){
                     if(a.contains("3300")){
                  client_java_tcp client = new client_java_tcp(b);
                     } 
                     else
                    System.out.println("Cannot connect to that port ");
               }
                 else 
                       System.out.println("Invalid port number, Terminating");

               }
                 
                 else 
                System.out.println("Cannot connect to server");
               }catch(Exception e){}
		
               
	}
}
