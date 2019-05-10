import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore的使用case
 * @author VincentJ
 * @date 2019-05-11
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        // 模拟抢车位，3车位--6辆车
        // 默认就是传false，为非公平锁
        Semaphore semaphore = new Semaphore(3, false);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                try {
                    // 表示占用了资源
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "  抢到车位");
                    TimeUnit.SECONDS.sleep(3L);
                    System.out.println(Thread.currentThread().getName() + "  停了3秒，然后离开了");
                } catch (InterruptedException pE) {
                    pE.printStackTrace();
                } finally {
                    // 表示释放了资源
                    semaphore.release();
                }

            }, Integer.toString(i)).start();
        }
    }
}
