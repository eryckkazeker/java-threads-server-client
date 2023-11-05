package commands;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class C2CommandWS implements Callable<String> {

    private PrintStream inputStream;

    public C2CommandWS(PrintStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public String call() throws Exception {
        System.out.println("Server received C2 command... - WS");

        inputStream.println("Processing c2 command - WS");

        Thread.sleep(20000);

        int number = new Random().nextInt(100) + 1;

        inputStream.println("C2 Command succesfully executed - WS");

        return Integer.toString(number);

    }

}
