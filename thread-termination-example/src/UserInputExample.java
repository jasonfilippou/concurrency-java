import java.io.IOException;

public class UserInputExample {

    public static void main(String [] args) {
        Thread thread = new Thread(new WaitingForUserInput());
        thread.setName("InputWaitingThread");
        thread.start();
        thread.interrupt();
    }

    private static class WaitingForUserInput implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    if(Thread.currentThread().isInterrupted()){
                        System.out.println("We got interrupted. Exiting...");
                        return;
                    }
                    char input = (char) System.in.read();
                    if(input == 'q') {
                        System.out.println("User typed in 'q'. Exiting...");
                        return;
                    }
                }
            } catch (IOException e) {
                System.out.println("An exception was caught " + e);
            };
        }
    }
}
