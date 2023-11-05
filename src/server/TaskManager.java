package server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import commands.C1Command;
import commands.C2CommandDatabase;
import commands.C2CommandWS;
import util.FutureResultJoin;

public class TaskManager implements Runnable {

    private Socket socket;
    private TasksServer server;
    private ExecutorService threadPool;
    private BlockingQueue<String> commandQueue;

    public TaskManager(Socket socket, TasksServer tasksServer, ExecutorService threadPool, BlockingQueue<String> commandQueue) {
        this.socket = socket;
        this.server = tasksServer;
        this.threadPool = threadPool;
        this.commandQueue = commandQueue;
    }

    @Override
    public void run() {
        System.out.println("Delegating task to "+socket);

        try {
            PrintStream inputStream = new PrintStream(socket.getOutputStream());

            try (Scanner sc = new Scanner(socket.getInputStream())) {
                while(sc.hasNextLine()) {
                    String command = sc.nextLine();
                    System.out.println("Received command "+ command);
    
                    switch(command) {
                        case "c1":
                            inputStream.println("Confirming c1");
                            C1Command c1Command = new C1Command(inputStream);
                            threadPool.execute(c1Command);
                        break;
                        case "c2":
                            inputStream.println("Confirming c2");
                            C2CommandDatabase c2CommandDatabase = new C2CommandDatabase(inputStream);
                            C2CommandWS c2CommandWs = new C2CommandWS(inputStream);
                            Future<String> futureDatabase = threadPool.submit(c2CommandDatabase);
                            Future<String> futureWs = threadPool.submit(c2CommandWs);

                            this.threadPool.submit(new FutureResultJoin(futureDatabase, futureWs, inputStream));
                        break;
                        case "c3":
                            commandQueue.put("c3");
                            inputStream.println("Added c3 to the queue");
                        break;
                        case "fim":
                            inputStream.println("Desligando servidor");
                            server.stop();
                            return;
                        default:
                            inputStream.println("Command not found");
                    }
    
                    System.out.println(command);
                }
    
                sc.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
    }

}
