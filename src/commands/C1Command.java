package commands;

import java.io.PrintStream;

public class C1Command implements Runnable {

    private PrintStream inputStream;

    public C1Command(PrintStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        
        System.out.println("Executing C1 command...");

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        inputStream.println("C1 Command succesfully executed");
        
    }

}
