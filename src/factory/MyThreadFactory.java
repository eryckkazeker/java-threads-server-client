package factory;

import java.util.concurrent.ThreadFactory;

import handler.ThreadExceptionHandler;

public class MyThreadFactory implements ThreadFactory{

    private ThreadFactory defaultFactory;

    public MyThreadFactory(ThreadFactory defaultFactory) {
        this.defaultFactory = defaultFactory;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread newThread = defaultFactory.newThread(r);
        newThread.setUncaughtExceptionHandler(new ThreadExceptionHandler());
        return newThread;
    }

}
