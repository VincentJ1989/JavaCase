import java.util.concurrent.TimeUnit;

class HoldLockThread implements Runnable {

    private final String lockA;
    private final String lockB;

    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t 自己持有：" + lockA + "\t 尝试获得：" + lockB);
            try {
                TimeUnit.SECONDS.sleep(2L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t 自己持有：" + lockB + "\t 尝试获得：" + lockA);
            }
        }
    }
}

/**
 * 手写死锁case
 */
public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";
        new Thread(new HoldLockThread(lockA,lockB),"ThreadA").start();
        new Thread(new HoldLockThread(lockB,lockA),"ThreadB").start();
while (true){

}
        // 如何排查
        /**
         * linux  ps -ef|grep xxxx  ls -l
         *
         * windows下的java运行程序  也有类似ps查看进程的命令，但是目前需要查看的知识java
         *         jps = java ps    jps -l
         */
    }
}
