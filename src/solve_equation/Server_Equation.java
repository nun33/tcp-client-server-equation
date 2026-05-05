package solve_equation;

import java.net.ServerSocket;
import java.net.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Server_Equation {

    public static void main (String[] args){

        ServerSocket server = null;
        Socket socket = null;
        try{
            // ----------- Step 1: Create a ServerSocket and start listening on port 5000 ----------
            server = new ServerSocket(5000);
            System.out.println("Server is running...");

            // ----------- Step 2: Wait for a client to connect (TCP handshake) ----------
            socket = server.accept();
            System.out.println("Client Connected");

            // Display client's IP address
            System.out.println("Client Connected :" + socket.getInetAddress().getHostAddress());

            // ----------- Step 3: Set up input and output streams ----------
            BufferedReader input = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );
            PrintWriter output = new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream()), true 
            );

            // Send welcome message to client
            output.println("Welcome! Send coefficients.");

            // ----------- Step 4: Main loop to handle client requests ----------
            while(true) {
                String msg = input.readLine();

                // If client disconnects (stream closed)
                if(msg == null){
                    break;
                }

                // If client requests to terminate connection
                if (msg.equals("EXIT")) {
                    System.out.println("Connection closed by client :" + socket.getInetAddress().getHostAddress());
                    break;
                }

                // ----------- Step 5: Parse the received equation ----------
                String[] parts = msg.split(" ");
                
                int p1 = Integer.parseInt(parts[0]);
                int p2 = Integer.parseInt(parts[1]);
                int p3 = Integer.parseInt(parts[2]);
                int result = Integer.parseInt(parts[3]);

                System.out.println("[Server] Solving: " + p1 + "*x +" + p2 + "*y + " + p3 + "*z =" + result);

                // ----------- Step 6: Solve the equation ----------
                String answer = solve(p1, p2, p3, result);

                // Send result back to client
                System.out.println("[Server] Response sent: " + answer);
                output.println(answer);
            }
            //handling the error if smth happens example: network fails, connection breaks, stream fails
        } catch (IOException e) {
            // ----------- Step 7: Handle communication errors ----------
            e.printStackTrace();
        } finally {
            // ----------- Step 8: Close resources (socket + server) ----------
            try {
                if (socket != null) socket.close();
                if (server != null) server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // ----------- Helper Method: Solve the equation ----------
    // Tries all values from 0 to 100 for x, y, z
    // Returns the first valid solution found
    public static String solve(int p1, int p2, int p3, int result) {

        for (int x = 0; x <= 100; x++) {
            for (int y = 0; y <= 100; y++) {
                for (int z = 0; z <=100; z++) {

                    if (p1 * x + p2 *y + p3 * z == result) {
                        return "x=" + x + ", y=" + y + ", z=" + z;
                    }
                }
            }
        }

        // No solution found in the given range
        return "No Answer";
    }
}
