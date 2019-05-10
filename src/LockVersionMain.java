import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock版本(传统版)的生产者消费者模式
 */

/**
 * 线程操作资源类
 */
class SorceBean {
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    /**
     * 数字增加1
     */
    public void increase() throws Exception {
        lock.lock();
        try {
            // 判断--多线程判断用while
            while (number != 0) {
                //等待，不能生产
                condition.await();
            }
            //干活
            number++;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            // 通知唤醒
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 数字减少1
     */
    public void decrease() throws Exception {
        lock.lock();
        try {
            // 判断
            while (number == 0) {
                //等待，不能生产
                condition.await();
            }
            //干活
            number--;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            // 通知唤醒
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}

public class LockVersionMain {

    public static void main(String[] args) {
        SorceBean sorceBean = new SorceBean();
        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    sorceBean.increase();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "AA").start();


        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    sorceBean.decrease();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "BB").start();

    }
}
