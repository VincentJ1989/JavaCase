import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier的使用case
 * @author VincentJ
 * @date 2019-05-10
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        runSimpleCase();
        runAdvancedCase();
    }

    private static void runSimpleCase() {
        // 如果这里5改成6，则打印语句不会执行，因为等不到第6个线程来，一直阻塞
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "线程来了");
                    cyclicBarrier.await();
                    System.out.println(Thread.currentThread().getName() + "线程走了");
                } catch (InterruptedException | BrokenBarrierException pE) {
                    pE.printStackTrace();
                }
            }, Integer.toString(i)).start();
        }

    }

    private static void runAdvancedCase() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(6, () -> System.out.println("线程都来齐了，放行了"));
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "线程来了");
                    cyclicBarrier.await();
                    System.out.println(Thread.currentThread().getName() + "线程走了");
                } catch (InterruptedException | BrokenBarrierException pE) {
                    pE.printStackTrace();
                }
            }, Integer.toString(i)).start();
        }
        try {
            cyclicBarrier.await();
        } catch (InterruptedException pE) {
            pE.printStackTrace();
        } catch (BrokenBarrierException pE) {
            pE.printStackTrace();
        }
    }
}
