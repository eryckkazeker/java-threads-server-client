package commands;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class C2CommandDatabase implements Callable<String> {

    private PrintStream inputStream;

    public C2CommandDatabase(PrintStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public String call() throws Exception {
        System.out.println("Server received C2 command... - DB");

        inputStream.println("Processing c2 command - Database");

        Thread.sleep(15000);

        int number = new Random().nextInt(100) + 1;

        inputStream.println("C2 Command succesfully executed - DB");

        return Integer.toString(number);

    }

}
