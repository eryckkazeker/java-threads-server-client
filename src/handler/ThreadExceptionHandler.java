package handler;

import java.lang.Thread.UncaughtExceptionHandler;

public class ThreadExceptionHandler implements UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        System.out.println("Error in thread " + thread.getName() +", "+ throwable.getMessage());
        
    }

}
