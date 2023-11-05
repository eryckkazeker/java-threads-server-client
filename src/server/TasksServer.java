package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import factory.MyThreadFactory;

public class TasksServer {

    private ServerSocket server;
    private ExecutorService threadPool;
    private AtomicBoolean isRunning = new AtomicBoolean(true);
    private BlockingQueue<String> commandQueue;

    public TasksServer() throws IOException {
        System.out.println("--------------Starting Server----------------");
        this.server = new ServerSocket(12345);
        this.threadPool = Executors.newCachedThreadPool(new MyThreadFactory(Executors.defaultThreadFactory()));
        this.commandQueue = new ArrayBlockingQueue<>(2);

        startConsumers();
    }
    
    private void startConsumers() {
        Runnable t1 = new ConsummerTask(commandQueue);
        Runnable t2 = new ConsummerTask(commandQueue);

        this.threadPool.execute(t1);
        this.threadPool.execute(t2);
    }
    
    public static void main(String[] args) throws Exception {

        TasksServer server = new TasksServer();
        server.run();
        server.stop();
        
    }

    public void stop() throws IOException {
        this.isRunning.set(false);
        this.server.close();
        this.threadPool.shutdown();
        System.out.println("System Shut Down");
    }

    public void run() throws IOException {
        while(this.isRunning.get()) {
            try {
                Socket socket = server.accept();
                System.out.println("Server - New client on port "+socket.getPort());

                TaskManager manager = new TaskManager(socket, this, threadPool, commandQueue);

                threadPool.execute(manager);
            } catch (SocketException ex) {
                System.out.println("Socket Exception, Is server running? "+this.isRunning);
            }
            
            
        }
    }
}
