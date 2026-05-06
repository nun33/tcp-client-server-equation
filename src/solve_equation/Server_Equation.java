package solve_equation;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Server_Equation {

    public static void main(String[] args) {

        ServerSocket server = null;
        try {
            // Create server socket on port 5000
            // This is the port clients will connect to
            server = new ServerSocket(5000);
            System.out.println("Server is running...");

            // ----------- Step1 : Keep server alive and always listening ---------------------------
            // This loop keeps running forever so the server can accept multiple clients
            while (true) {

                // Wait until a client connects
                Socket socket = server.accept();

                // Print client IP just for tracking
                System.out.println("Client Connected: " + socket.getInetAddress().getHostAddress());

                // ----------- Step 2: Handle each client in a separate thread ---------------------------
                // This is important so multiple clients can use the server at the same time
                Thread clientThread = new Thread(() -> handleClient(socket));

                // Start the thread (this runs handleClient method)
                clientThread.start();
            }

        } catch (IOException e) {
            // If something goes wrong with server setup
            e.printStackTrace();
        } finally {
            try {
                // Always close the server when program ends
                if (server != null) server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // ----------- Step 3: This method handles one client ---------------------------
    // Each client gets its own execution of this method inside a thread
    private static void handleClient(Socket socket) {
        try {

            // Used to read data coming from the client
            BufferedReader input = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );

            // Used to send data to the client
            PrintWriter output = new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream()), true
            );

            // Send a welcome message when client connects
            output.println("Welcome! Send coefficients.");

            // Keep reading messages from client until they disconnect
            while (true) {

                // Read one line sent from client
                String msg = input.readLine();

                // If client disconnects, msg will be null
                if (msg == null) break;

                // If client wants to exit
                if (msg.equals("EXIT")) {
                    output.println("Connection closed.");
                    System.out.println("Connection closed by client: " + socket.getInetAddress().getHostAddress());
                    break;
                }

                // Expected format: "p1 p2 p3 result"
                // Example: "2 3 4 20"
                String[] parts = msg.split(" ");

                // Convert string values to integers
                int p1 = Integer.parseInt(parts[0]);
                int p2 = Integer.parseInt(parts[1]);
                int p3 = Integer.parseInt(parts[2]);
                int result = Integer.parseInt(parts[3]);

                // print what equation we are solving
                System.out.println("[Server] Solving: " + p1 + "*x + " + p2 + "*y + " + p3 + "*z = " + result);

                // Call solve method to find values of x, y, z
                String answer = solve(p1, p2, p3, result);

                // Print result on server side (for debugging)
                System.out.println("[Server] Response sent: " + answer);

                // Send result back to client
                output.println(answer);
            }

        } catch (IOException e) {
            // Handles communication errors
            e.printStackTrace();
        } finally {
            try {
                // Always close socket after client is done
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // ----------- Step 4: Solve the equation ---------------------------
    public static String solve(int p1, int p2, int p3, int result) {

        for (int x = 0; x <= 100; x++)
            for (int y = 0; y <= 100; y++)
                for (int z = 0; z <= 100; z++)

                    // Check if this combination satisfies the equation
                    if (p1 * x + p2 * y + p3 * z == result)

                        // Return first solution found
                        return "x=" + x + ", y=" + y + ", z=" + z;

        // If no solution found in range
        return "No Answer";
    }
}