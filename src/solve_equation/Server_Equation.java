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

            //server is waiting until a connection is established, waiting for the client
            socket = server.accept();
            System.out.println("Client Connected");

            BufferedReader input = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );
            PrintWriter output = new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream()), true 
            );

            //welcoming msg sent to client, TCP connection established
            output.println("Welcome! Send coefficients.");

            //infinite loop cuz the server is required to handle multiple requests from same client
            while(true) {
                String msg = input.readLine();

                //handling dissconnection
                if(msg == null){
                    break;
                }

                //handling exists
                if (msg.equals("EXIT")) {
                    output.println("Connection closed.");
                    break;
                }

                //parsing input 
                String[] parts = msg.split(" ");
                
                //converting input to nums
                int p1 = Integer.parseInt(parts[0]);
                int p2 = Integer.parseInt(parts[1]);
                int p3 = Integer.parseInt(parts[2]);
                int result = Integer.parseInt(parts[3]);


                String answer = solve(p1, p2, p3, result);
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
                        return "x=" + x + ", y=" + y + ", z =" + z;
                    }
                }
            }
        }

        return "No Answer";
    }
}
