import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Exchanger的使用case
 * @author VincentJ
 * @date 2019-05-11
 */
public class ExchangerDemo {
    private static final Exchanger<String> exgr = new Exchanger<>();
    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        threadPool.execute(() -> {
            String A = "数据A";
            try {
                exgr.exchange(A);
            } catch (InterruptedException pE) {
                pE.printStackTrace();
            }
        });

        threadPool.execute(() -> {
            String B = "数据B";
            try {
                String A = exgr.exchange(B);
                System.out.println("A和B的数据是否一致:" + B.equals(A) + "\nA录入的数据为:" + A + "\nB录入的数据为:" + B);

            } catch (InterruptedException pE) {
                pE.printStackTrace();
            }
        });

        threadPool.shutdown();
    }
}
