import java.math.BigInteger;

public class ComplexCalculation {
  public static BigInteger calculateResult(
      BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2)
      throws InterruptedException {
    PowerCalculatingThread threadOne = new PowerCalculatingThread(base1, power1);
    PowerCalculatingThread threadTwo = new PowerCalculatingThread(base2, power2);
    threadOne.start();
    threadTwo.start();
    threadOne.join();
    threadTwo.join();
    return threadOne.getResult().add(threadTwo.getResult());
  }

  private static class PowerCalculatingThread extends Thread {
    private BigInteger result = BigInteger.ONE;
    private BigInteger base;
    private BigInteger power;

    public PowerCalculatingThread(BigInteger base, BigInteger power) {
      this.base = base;
      this.power = power;
    }

    @Override
    public void run() {
      BigInteger result = BigInteger.ONE;
      // Can do this faster with repeated squaring, but let's keep it naive for now.
      for (BigInteger i = BigInteger.ZERO; i.compareTo(power) < 0; i = i.add(BigInteger.ONE)) {
        if (Thread.currentThread().isInterrupted()) {
          this.result = BigInteger.ZERO;
          return;
        }
        result = result.multiply(base);
      }
      this.result = result;
    }

    public BigInteger getResult() {
      return result;
    }
  }

  public static void main(String[] args) throws InterruptedException {
    System.out.println(
        "1^1 + 1^1 = "
            + calculateResult(BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE));
    System.out.println(
        "2^1 + 2^1 = "
            + calculateResult(
                BigInteger.valueOf(Long.parseLong("2")),
                BigInteger.ONE,
                BigInteger.valueOf(Long.parseLong("2")),
                BigInteger.ONE));
    System.out.println(
        "2^1 + 2^2 = "
            + calculateResult(
                BigInteger.valueOf(Long.parseLong("2")),
                BigInteger.ONE,
                BigInteger.valueOf(Long.parseLong("2")),
                BigInteger.valueOf(Long.parseLong("2"))));
    System.out.println(
        "3^2 + 3^3 = "
            + calculateResult(
                BigInteger.valueOf(Long.parseLong("3")),
                BigInteger.valueOf(Long.parseLong("2")),
                BigInteger.valueOf(Long.parseLong("3")),
                BigInteger.valueOf(Long.parseLong("3"))));
    System.out.println(
        "3^3 + 3^4 = "
            + calculateResult(
                BigInteger.valueOf(Long.parseLong("3")),
                BigInteger.valueOf(Long.parseLong("3")),
                BigInteger.valueOf(Long.parseLong("3")),
                BigInteger.valueOf(Long.parseLong("4"))));
    System.out.println(
        "10^10 + 11^11 = "
            + calculateResult(
                BigInteger.valueOf(Long.parseLong("10")),
                BigInteger.valueOf(Long.parseLong("10")),
                BigInteger.valueOf(Long.parseLong("11")),
                BigInteger.valueOf(Long.parseLong("11"))));

    System.out.println(
            "20^20 + 40^40 = "
                    + calculateResult(
                    BigInteger.valueOf(Long.parseLong("20")),
                    BigInteger.valueOf(Long.parseLong("20")),
                    BigInteger.valueOf(Long.parseLong("40")),
                    BigInteger.valueOf(Long.parseLong("40"))));
  }
}
