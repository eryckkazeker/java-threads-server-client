package server;

import java.util.concurrent.BlockingQueue;

public class ConsummerTask implements Runnable {

    private BlockingQueue<String> commandQueue;

    public ConsummerTask(BlockingQueue<String> commandQueue) {
        this.commandQueue = commandQueue;
    }

    @Override
    public void run() {
        try {
            String command = null;
            while((command = commandQueue.take())!=null) {
                System.out.println("Consuming command "+command + " - "+Thread.currentThread().getName());
                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }        
    }
    
}
