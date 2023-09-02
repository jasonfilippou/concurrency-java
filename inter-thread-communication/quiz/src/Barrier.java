import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Barrier {
    private final int numberOfWorkers;
    private final Semaphore semaphore = new Semaphore(0); // Blank 1
    private int counter = 0; // Blank 2
    private final Lock lock = new ReentrantLock();

    public Barrier(int numberOfWorkers) {
        this.numberOfWorkers = numberOfWorkers;
    }

    public void waitForOthers() throws InterruptedException {
        lock.lock();
        boolean isLastWorker = false;
        try {
            counter++;  // Not an atomic operation, but we have synchronized on the lock.
            if (counter == numberOfWorkers) {
                isLastWorker = true;
            }
        } finally {
            lock.unlock();
        }
        if (isLastWorker) {
            semaphore.release(numberOfWorkers - 1); // Blank 3
        } else {
            semaphore.acquire(); // So all non-last workers will block until the last worker allows them to.
        }
    }
}