import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch例子
 * @author VincentJ
 * @date 2019-05-10
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println("线程" + Thread.currentThread().getName() + "  结束了");
                countDownLatch.countDown();
            }, Integer.toString(i)).start();
        }
        countDownLatch.await();
        System.out.println("终于所有的线程都走完了");

    }
}
