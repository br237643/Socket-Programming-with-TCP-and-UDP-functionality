/**
*DETAILS OF CODE
*The code of initialising the Datagramsocket and then connection and sending the data *to client has been taken from coderanch.com, Briyan Ringers author
*This author has wrote simple sockets programs for understanding, i have used * those to build our project
*What i have learned from it is the socket concept and now i think i would be *able to make new sockets with different modularities/
*Remote calculator code was there in geeksforgeeks.com which i have used as a *referenced and made up our operations work
*/



import java.io.*;
import java.net.*;
import java.util.Stack;

class server_java_udp
{
    
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
    public static void main(String args[]) throws Exception
       {
          DatagramSocket serverSocket = new DatagramSocket(3300);
             byte[] receiveData = new byte[1024];
             byte[] sendData = new byte[1024];
             final String Ack = "Ack";
             int a=0;
             String result="";
             while(true)
                {
                    DatagramPacket receivePacket1 = new DatagramPacket(receiveData, receiveData.length);
                   serverSocket.receive(receivePacket1);
                   String len = new String( receivePacket1.getData());
                    System.out.println("Length of the expression is" +len);
                   DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                   serverSocket.receive(receivePacket);
                   String sentence = new String( receivePacket.getData());
                  //System.out.println(sentence);
                      try{
                           System.out.println("Server 3300");
                          a=server_java_udp.evaluate(sentence);
                          System.out.println("Connected " +a);
                    	  InetAddress IPAddress = receivePacket.getAddress();
                          int port = receivePacket.getPort();
                     sendData = Ack.getBytes();
                   DatagramPacket sendPacket =
                   new DatagramPacket(sendData, sendData.length, IPAddress, port);
                   serverSocket.send(sendPacket);
                   
                    }catch(Exception e){
                    System.err.println("IO error in server");
			e.printStackTrace();
                    } 
                   //to send result 
                   InetAddress IPAddres = receivePacket.getAddress();
                   int por = receivePacket.getPort();
                   
                   //sendData = result.getBytes();
                   for(int j=0;j<a;j++){
                   
                   result=result + "Socket programming \n";
                    }
                    sendData = result.getBytes();
                    DatagramPacket sendPacket1 =
                   new DatagramPacket(sendData, sendData.length, IPAddres, por);
                   serverSocket.send(sendPacket1);
                    
                      System.out.println("RECEIVED From user: " + sentence);
                   InetAddress IPAddress = receivePacket.getAddress();
                   int port = receivePacket.getPort();
                   String capitalizedSentence = sentence.toUpperCase();
                   sendData = capitalizedSentence.getBytes();
                   DatagramPacket sendPacket =
                   new DatagramPacket(sendData, sendData.length, IPAddress, port);
                   serverSocket.send(sendPacket);
                 if(sentence.contains("3300"))
                 {
                  System.out.println("Connected " +a);
                 }                
       }
} 
}

