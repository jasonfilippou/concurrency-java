import java.util.List;
import java.util.Random;

public class MinMaxMetrics {

    private volatile long min;
    private volatile long max;

    /** Initializes all member variables */
    public MinMaxMetrics() {
        min = 0;
        max = 0;
    }

    /** Adds a new sample to our metrics. */
    public synchronized void addSample(long newSample) {
        System.out.println("Thread " + Thread.currentThread().getName() + " adding sample: " + newSample);
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {
        }
        if (newSample < min) {
            min = newSample;
        } else if (newSample > max) {
            max = newSample;
        }
    }

    /** Returns the smallest sample we've seen so far. */
    public long getMin() {
        return min;
    }

    /** Returns the biggest sample we've seen so far. */
    public long getMax() {
        return max;
    }

    private static class BusinessLogic extends Thread {

        private final MinMaxMetrics minMaxMetrics;
        private final Random random = new Random();

        public BusinessLogic(MinMaxMetrics minMaxMetrics) {
            this.minMaxMetrics = minMaxMetrics;
        }

        @Override
        public void run() {
            while (true) {
                long sample = random.nextLong(10000);
                minMaxMetrics.addSample(randomSign() * sample);
            }
        }

        private long randomSign(){
            return random.nextInt(2) == 0? -1 : 1;
        }
    }

    private static class MetricsPrinter extends Thread {
        private final MinMaxMetrics minMaxMetrics;

        public MetricsPrinter(MinMaxMetrics minMaxMetrics){
            this.minMaxMetrics = minMaxMetrics;
        }
        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {
                }
                System.out.println("Current min sample: " + minMaxMetrics.getMin() + ". Current max sample:" + minMaxMetrics.getMax());
            }
        }
    }

    public static void main(String[] args) {
        MinMaxMetrics metrics = new MinMaxMetrics();
        List<BusinessLogic> threads =
                List.of(
                        new BusinessLogic(metrics),
                        new BusinessLogic(metrics),
                        new BusinessLogic(metrics),
                        new BusinessLogic(metrics),
                        new BusinessLogic(metrics),
                        new BusinessLogic(metrics),
                        new BusinessLogic(metrics),
                        new BusinessLogic(metrics));
        MetricsPrinter metricsPrinter = new MetricsPrinter(metrics);
        threads.forEach(Thread::start);
        metricsPrinter.start();
    }
}
