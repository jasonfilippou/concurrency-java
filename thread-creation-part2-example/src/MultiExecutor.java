import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiExecutor {

  private final List<Thread> threads;

  /**
   * @param tasks to executed concurrently
   */
  public MultiExecutor(List<Runnable> tasks) {
    if (tasks == null) {
      throw new RuntimeException("Provided with null list.");
    }
    // Eagerly creates the list of threads
    threads = new ArrayList<>(tasks.size());
    for (Runnable task : tasks) {
      threads.add(new Thread(task));
    }
  }

  /** Starts and executes all the tasks concurrently */
  public void executeAll() {
    threads.forEach(Thread::start);
  }

  public static void main(String[] args) {
    AtomicInteger taskID = new AtomicInteger();
    List<Runnable> tasks =
        List.of(
            () -> System.out.println("Task with Id: " + taskID.getAndIncrement()),
            () -> System.out.println("Task with Id: " + taskID.getAndIncrement()),
            () -> System.out.println("Task with Id: " + taskID.getAndIncrement()),
            () -> System.out.println("Task with Id: " + taskID.getAndIncrement()),
            () -> System.out.println("Task with Id: " + taskID.getAndIncrement()),
            () -> System.out.println("Task with Id: " + taskID.getAndIncrement()),
            () -> System.out.println("Task with Id: " + taskID.getAndIncrement()),
            () -> System.out.println("Task with Id: " + taskID.getAndIncrement()),
            () -> System.out.println("Task with Id: " + taskID.getAndIncrement()),
            () -> System.out.println("Task with Id: " + taskID.getAndIncrement()));
    MultiExecutor executor = new MultiExecutor(tasks);
    executor.executeAll();
  }
}
