package solve_equation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client_Equation {

	public static void main(String[] args) throws IOException{
		
		// -----------Step1:We defind the server IP address and port ---------------------------
		
		String serverIP=("localhost");
		int port = 5000; 
		
		// ---------- Step 2: Create a TCP socket and connect to the server ----------------------
     	// Socket opens a connection between the client and the server
         Socket client_socket =  new Socket(serverIP,port);
         BufferedReader Input = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
         PrintWriter Output = new PrintWriter(new OutputStreamWriter(client_socket.getOutputStream()),true);
         
         
      // ------------- Step 3: Set up scanner to read from the user ----------------------------
         Scanner User = new Scanner(System.in);
         
        
      //---------------Step 4: Read and display the welcome message from the server -----------------
         System.out.println("Server:" + Input.readLine());
         
        
         
      //---------------Step 5: Main loop — Display the menu for the user  keep running until the user chooses to exit-----
         
         while (true) {
             System.out.println("----------------------------------------");
             System.out.println("Choose an option :");
             System.out.println("1.Enter coefficients and result  (P1 P2 P3 result)");
             System.out.println("2.To Exit");
             System.out.print("> ");
        
             String choice = User.next();
             
             
          //------Option 2: If the user wants to exit ----------------------
             if (choice.equals("2")) {
            	
            	 Output.println("EXIT");
            	 System.out.println("Closing connection.......");
                 System.out.println("Server: " + Input.readLine());
             
                 break;
             }
             
             
          //--------Option 1: If the user wants to enter an equation---------------------
             else if (choice.equals("1")) {
                 int p1 = CheckCoefficient(User, "P1");
                 int p2 = CheckCoefficient(User, "P2");
                 int p3 = CheckCoefficient(User, "P3");
                 int result = CheckCoefficient(User, "Result");

             
             
             String[] equation = {
                     String.valueOf(p1),
                     String.valueOf(p2),
                     String.valueOf(p3),
                     String.valueOf(result)
                 };
             
             String Equation = equation[0] + " " + equation[1] + " " + equation[2] + " " + equation[3];             
             
             
          // ----------Step 7: Send equation and measure Round-Trip Time (RTT)------------------------
             long startTime = System.currentTimeMillis();
             Output.println(Equation);
             String response=Input.readLine();
             long endTime = System.currentTimeMillis();
             long rtt = endTime - startTime;

          // -----------Step 8: Display the results ----------------------------
             System.out.println("\n========== RESULT ==========");
             System.out.println("Your Equation : " + p1 + "*x + " + p2 + "*y + " + p3 + "*z = " + result);
             System.out.println("Solution : " + response);
             System.out.println("RTT      : " + rtt + " ms");
             System.out.println("===============================");
           
             
          // ── Invalid input: user entered something other than 1 or 2 ───────
             } else {
            	 
             System.out.println("Invalid choice. Please try again ");
             
             }
             
          
             }
         
      // -----------------Step 9: Close the socket and scanner when done-----------------------
      // This releases the network connection
         client_socket.close();
         User.close();
	}
	
     // Countinue of step 5 option 1:
	//------------------ Helper Method: GetCoefficient-------------------------------
		// Reads a value from the user and validates it is an integer
		// Keeps asking until the user enters a valid integer
         
             private static int CheckCoefficient (Scanner User ,String name){
             
             while(true) {
            	 System.out.print("Enter "+ name +"(integer) :");
            	 
            	 String Input_Coefficient = User.next();
            	 
            	 try {
            		 return Integer.parseInt(Input_Coefficient);
            	 } catch(NumberFormatException e) {
            	 System.out.println("Error "+ name + " must be integer.");
            	
             
             
            	 
            	 
            	 
            	 
            	 
             }
             
             
             
             
             
             
	}
	}


}