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
            server = new ServerSocket(5000);
            System.out.println("Server is running...");

            socket = server.accept();
            System.out.println("Client Connected");

            BufferedReader input = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );
            PrintWriter output = new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream()), true 
            );

            output.println("Welcome! Send coefficients.");

            while(true) {
                String msg = input.readLine();

                if(msg == null){
                    break;
                }

                if (msg.equals("EXIT")) {
                    output.println("Connection closed.");
                    break;
                }

                String[] parts = msg.split(" ");
                
                int p1 = Integer.parseInt(parts[0]);
                int p2 = Integer.parseInt(parts[1]);
                int p3 = Integer.parseInt(parts[2]);
                int result = Integer.parseInt(parts[3]);


                String answer = solve(p1, p2, p3, result);
                output.println(answer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
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
