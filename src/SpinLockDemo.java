import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 手写一个自旋锁
 * @author VincentJ
 * @date 2019-05-13
 */
public class SpinLockDemo {
    // 原子引用线程
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(() -> {
            spinLockDemo.myLock();
            try {
                TimeUnit.SECONDS.sleep(5L);
            } catch (InterruptedException pE) {
                pE.printStackTrace();
            }
            spinLockDemo.myUnlock();
        }, "AA").start();

        // 确保线程AA先执行
        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException pE) {
            pE.printStackTrace();
        }

        new Thread(() -> {
            spinLockDemo.myLock();
            spinLockDemo.myUnlock();
        }, "BB").start();
    }

    public void myLock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + " 来了");
        while (!atomicReference.compareAndSet(null, thread)) {

        }
    }

    public void myUnlock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName() + "调用了解锁");
    }

}
