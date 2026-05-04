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
            //Server is waiting for a connection to happen over port 5000, listening
            server = new ServerSocket(5000);
            System.out.println("Server is running...");

            //TCP handshake 
            socket = server.accept();
            System.out.println("Client Connected");

            System.out.println("Client Connected :" + socket.getInetAddress().getHostAddress());

            BufferedReader input = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );
            PrintWriter output = new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream()), true 
            );

            output.println("Welcome! Send coefficients.");

            //infinite loop cuz the server is required to handle multiple requests from same client
            while(true) {
                String msg = input.readLine();

                //client closed connection
                if(msg == null){
                    break;
                }

                //handle client request to close connection
                if (msg.equals("EXIT")) {
                    System.out.println("Connection closed by client :" + socket.getInetAddress().getHostAddress());
                    break;
                }

                //parsing input 
                String[] parts = msg.split(" ");
                
                //converting input to int
                int p1 = Integer.parseInt(parts[0]);
                int p2 = Integer.parseInt(parts[1]);
                int p3 = Integer.parseInt(parts[2]);
                int result = Integer.parseInt(parts[3]);

                System.out.println("[Server] Solving: " + p1 + "*x +" + p2 + "*y + " + p3 + "*z =" + result);

                String answer = solve(p1, p2, p3, result);
                System.out.println("[Server] Response sent: " + answer);
                output.println(answer);
            }
            //handling the error if smth happens example: network fails, connection breaks, stream fails
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //closing resources, prevents memory leaks, port stayin' busy, even if an error happens it must closes it 
                if (socket != null) socket.close();
                if (server != null) server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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

        return "No Answer";
    }
}
