import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 阻塞队列版生产者消费者模式
 * volatile/CAS/AtomicInteger/BlockingQueue
 */
class ShareData {
    // 多线程可见-用volatile
    //默认开启进行生成、消费
    private volatile boolean FLAG = true;
    // 原子性，否则i++多线程不安全
    private AtomicInteger atomicInteger = new AtomicInteger();

    private BlockingQueue<String> blockingQueue = null;

    // 架构上只能传接口，不能传具体实现类!
    public ShareData(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    public void myProd() throws Exception {
        String data = null;
        boolean retValue;
        while (FLAG) {
            data = atomicInteger.incrementAndGet() + "";
            retValue = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (retValue) {
                System.out.println(Thread.currentThread().getName() + "\t" + "插入队列" + data + "成功");
            } else {
                System.out.println(Thread.currentThread().getName() + "\t" + "插入队列" + data + "失败");
            }
            TimeUnit.SECONDS.sleep(1L);
        }
        System.out.println(Thread.currentThread().getName() + "\t大老板叫停了，表示FLAG为false,生产动作结束");
    }

    public void myConsumer() throws Exception {
        String result = null;
        while (FLAG) {
            result = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (result == null || result.equalsIgnoreCase("")) {
                FLAG = false;
                System.out.println(Thread.currentThread().getName() + "\t" + "超过2秒没取到消息，消费退出");
                return;
            } else {
                System.out.println(Thread.currentThread().getName() + "\t" + "消费队列消息" + result + "成功");
            }
        }
    }

    public void stop() throws Exception {
        this.FLAG = false;
    }
}

public class BlockingQueneVersionMain {
    public static void main(String[] args) {
        ShareData shareData = new ShareData(new ArrayBlockingQueue<>(10));
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t生产线程启动");
            try {
                shareData.myProd();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Prod").start();


        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t消费线程启动");
            try {
                shareData.myConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Consumer").start();

        try {
            TimeUnit.SECONDS.sleep(5L);
            System.out.println("5秒时间到，大老板main线程叫停，活动结束");
            shareData.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
