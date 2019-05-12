import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import sun.misc.VM;

/**
 * 演示常见的几种OOM
 * 1.java.lang.StackOverflowError
 * 2.java.lang.OutOfMemoryError:Java heap space
 * 3.java.lang.OutOfMemoryError:GC overhead limit exceeded
 * 4.java.lang.OutOfMemoryError:Direct buffer memory
 * 5.java.lang.OutOfMemoryError:unable to create new native thread
 * 6.java.lang.OutOfMemoryError:Metaspace
 *
 * @author VincentJ
 * @date 2019-05-12
 */
public class OOMDemo {
    public static void main(String[] args) {
        // showStackOverflowError();
        // showOOMJavaHeapSpace();
        // showOOMGCOverLimitExceeded();
        // showOOMDirectBufferMemory();
        // showOOMNativeThreadError();
        // showOOMMetaspaceError(args);
    }

    static class TestBean {
    }

    private static void showOOMMetaspaceError(String[] pArgs) {
        int i = 0;
        try {
            for (;;) {
                i++;
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(TestBean.class);
                enhancer.setUseCache(false);
                enhancer.setCallback(new MethodInterceptor() {
                    @Override
                    public Object intercept(Object pO, Method pMethod, Object[] pObjects, MethodProxy pMethodProxy)
                        throws Throwable {
                        return pMethodProxy.invokeSuper(pO, pArgs);
                    }
                });
                enhancer.create();
            }
        } catch (Throwable pThrowable) {
            System.out.println("***********多少次后发生了异常:" + i);
            pThrowable.printStackTrace();
        }
    }

    private static void showOOMNativeThreadError() {
        // 这个需要在Linux服务器上演示
        for (int i = 0;; i++) {
            System.out.println("************i=" + i);
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(Integer.MIN_VALUE);
                } catch (InterruptedException pE) {
                    pE.printStackTrace();
                }
            }, Integer.toString(i)).start();
        }
    }

    private static void showOOMDirectBufferMemory() {
        System.out.println("当前配置的MaxDirectMemory：" + VM.maxDirectMemory() / (1024 * 1024) + "M");
        try {
            TimeUnit.SECONDS.sleep(3L);
        } catch (InterruptedException pE) {
            pE.printStackTrace();
        }
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(6 * 1024 * 1024);
    }

    private static void showOOMGCOverLimitExceeded() {
        int i = 0;
        List<String> stringList = new ArrayList<>();
        for (;;) {
            stringList.add(Integer.toString(++i).intern());
        }
    }

    private static void showOOMJavaHeapSpace() {
        byte[] bytes = new byte[80 * 1024 * 1024];
    }

    /**
     * 展示java.lang.StackOverflowError
     * 记住："栈管运行，堆管存储"
     */
    private static void showStackOverflowError() {
        showStackOverflowError();
    }
}
