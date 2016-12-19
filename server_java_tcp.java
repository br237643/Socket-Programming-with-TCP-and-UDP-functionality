/**
*DETAILS OF CODE
*The code of initialising the socket and then connection and sending the data *to client has been taken from coderanch.com, Briyan Ringers author
*This author has wrote simple sockets programs for understanding, i have used * those to build our project
*What i have learned from it is the socket concept and now i think i would be *able to make new sockets with different modularities/
*Remote calculator code was there in geeksforgeeks.com which i have used as a *referenced and made up our operations work
*/



import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Stack;

/**
 * Main Server class. Includes all functionality including main method to start
 * 
 * @author Srinivas
 *
 */
public class server_java_tcp {
	int port;
	
	/* Fixed response messages */
	final String WELCOME     = " Welcome ";
	final String HELO        = "ACK";
	final String e="Enter server name or IP address:";
	final String Time        = "Timeout";
	

	private ServerSocket serverSocket;
	
	/* Flag used to check if successful HELO has been processed */
	private Boolean heloAck;
	

	
	/**
	 * Constructor of the File Server which uses the given port to start a socket 
	 * connection at at.
	 * 
	 * @param port
	 */
	public server_java_tcp (int port) {
		this.port = port;
		try {
			System.out.println("Server "+port);
			this.serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Cannot open socket. Check port "+port);
			e.printStackTrace();
		}
	}

	/**
	 * Server run method which starts the socket connection to read and write 
	 * to/from client.
	 * Resets the helo flag to ensure check after reconnection.
	 * 
	 * @throws IOException
	 */
	public void run() throws IOException {
		
		while(true){
			Socket socket = this.serverSocket.accept();
			OutputStream os = socket.getOutputStream();
			InputStream is = socket.getInputStream();
			BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(os));
			
			this.heloAck = false;
			//writer(bWriter, "Client"); // Sending Welcome message
			try {
				readLoop(os, is);
			} finally {
				socket.close();
			}
		}
	}

	/**
	 * Loops to keep processing user input 
	 * 
	 * After successful HELO check it starts processing CD, PWD, LS, GET commands
	 * 
	 * @param os OutputSteam used to write to client
	 * @param is InputStream used to read what client has sent
	 * @throws IOException
	 */
	public void readLoop(OutputStream os, InputStream is) throws IOException {

		BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(os));
		BufferedReader bReader = new BufferedReader(new InputStreamReader(is));
		while(true) {
			int l=0;
			
			
		    // writer(bWriter, e );
			//String name=bReader.readLine();
			
				
				writer(bWriter,"Enter the expression length");
				String leng=bReader.readLine();
				//int len=Integer.parseInt(leng);
				writer(bWriter,"Enter the expression");
				//String g=bReader.readLine();
				String exp=bReader.readLine();
				writer(bWriter, "you have entered" +exp );
				
	            //exp.append(" ");
				writer(bWriter, "The result of expression is :" +server_java_tcp.evaluate(exp));
	           for(int i=0;i<server_java_tcp.evaluate(exp);i++){
                     writer(bWriter, "SOCKET PROGRAMMING");
                      }

        
		     	   break;
			
			
	}
		
	}		
//**
	 public static int evaluate(String expression)
	    {
	        char[] tokens = expression.toCharArray();
	 
	         // Stack for numbers: 'values'
	        Stack<Integer> values = new Stack<Integer>();
	 
	        // Stack for Operators: 'ops'
	        Stack<Character> ops = new Stack<Character>();
	 
	        for (int i = 0; i < tokens.length; i++)
	        {
	             // Current token is a whitespace, skip it
	            if (tokens[i] == ' ')
	                continue;
	 
	            // Current token is a number, push it to stack for numbers
	            if (tokens[i] >= '0' && tokens[i] <= '9')
	            {
	                StringBuffer sbuf = new StringBuffer();
	                // There may be more than one digits in number
	                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9')
	                    sbuf.append(tokens[i++]);
	                values.push(Integer.parseInt(sbuf.toString()));
	            }
	 
	            // Current token is an opening brace, push it to 'ops'
	            else if (tokens[i] == '(')
	                ops.push(tokens[i]);
	 
	            // Closing brace encountered, solve entire brace
	            else if (tokens[i] == ')')
	            {
	                while (ops.peek() != '(')
	                  values.push(applyOp(ops.pop(), values.pop(), values.pop()));
	                ops.pop();
	            }
	 
	            // Current token is an operator.
	            else if (tokens[i] == '+' || tokens[i] == '-' ||
	                     tokens[i] == '*' || tokens[i] == '/')
	            {
	                // While top of 'ops' has same or greater precedence to current
	                // token, which is an operator. Apply operator on top of 'ops'
	                // to top two elements in values stack
	                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
	                  values.push(applyOp(ops.pop(), values.pop(), values.pop()));
	 
	                // Push current token to 'ops'.
	                ops.push(tokens[i]);
	            }
	        }
	 
	        // Entire expression has been parsed at this point, apply remaining
	        // ops to remaining values
	        while (!ops.empty())
	            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
	 
	        // Top of 'values' contains result, return it
	        return values.pop();
	    }
	 
	    // Returns true if 'op2' has higher or same precedence as 'op1',
	    // otherwise returns false.
	    public static boolean hasPrecedence(char op1, char op2)
	    {
	        if (op2 == '(' || op2 == ')')
	            return false;
	        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
	            return false;
	        else
	            return true;
	    }
	 
	    // A utility method to apply an operator 'op' on operands 'a' 
	    // and 'b'. Return the result.
	    public static int applyOp(char op, int b, int a)
	    {
	        switch (op)
	        {
	        case '+':
	            return a + b;
	        case '-':
	            return a - b;
	        case '*':
	            return a * b;
	        case '/':
	            if (b == 0)
	                throw new
	                UnsupportedOperationException("Cannot divide by zero");
	            return a / b;
	        }
	        return 0;
	    }

	/**
	 * Writes the given String to the BufferedWriter with required commands for
	 * sending on a Mac. 
	 * Calling newLine() may not be required on Windows.
	 * 
	 * @param bWriter buffered writer to write to socket
	 * @param message message to be written to client
	 * @throws IOException
	 */
	public void writer(BufferedWriter bWriter, String message) throws IOException{
		bWriter.write(message);
		bWriter.newLine();
		bWriter.flush();
	}
	

	/**
	 * Main method which will instantiate a Server with a given port number.
	 * 
	 * @param arguments
	 */
	public static void main(String[] arguments) {
		
		

		server_java_tcp server = new server_java_tcp(3300);
		try {
			server.run();
			
			
		} catch (IOException e) {
			System.err.println("IO error in server");
			e.printStackTrace();
		}

	}
}
