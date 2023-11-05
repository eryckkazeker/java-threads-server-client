package client;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class TasksClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 12345);
        System.out.println("----------------Connection Established---------------");
        PrintStream output = new PrintStream(socket.getOutputStream());
        Scanner input = new Scanner(socket.getInputStream());
        Scanner keyboard = new Scanner(System.in);

        Thread ouputThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while(keyboard.hasNextLine()) {
                    String line = keyboard.nextLine();

                    if (line.trim().equals("")) {
                        break;
                    }

                    output.println(line);
                }
                output.close();
                keyboard.close();
            }
            
        });

        Thread inputThread = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("Receiving data...");

                while(input.hasNextLine()) {
                    String line = input.nextLine();
                    System.out.println(line);
                }
                input.close();
            }
            
        });

        inputThread.start();
        ouputThread.start();

        inputThread.join();
        System.out.println("Closing client socket...");
        socket.close();
    }
}
