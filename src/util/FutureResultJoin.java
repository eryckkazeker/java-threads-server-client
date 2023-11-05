package util;

import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureResultJoin implements Callable<Void> {

    private Future<String> futureDatabase;
    private Future<String> futureWs;
    private PrintStream inputStream;

    public FutureResultJoin(Future<String> futureDatabase, Future<String> futureWs, PrintStream inputStream) {
        this.futureDatabase = futureDatabase;
        this.futureWs = futureWs;
        this.inputStream = inputStream;
    }

    @Override
    public Void call() {

        System.out.println("Waiting for future results");

        try {
            String wsResult = futureWs.get(25, TimeUnit.SECONDS);
            String databaseResult = futureDatabase.get(25, TimeUnit.SECONDS);

            this.inputStream.println("C2 Results: " + wsResult + " - "+ databaseResult);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            inputStream.println("C2 Command error "+ e.getMessage());
            futureDatabase.cancel(true);
            futureWs.cancel(true);
            System.out.println("Futures canceled");
        }
        
        System.out.println("Result join execution ended");
        return null;
    }


}
