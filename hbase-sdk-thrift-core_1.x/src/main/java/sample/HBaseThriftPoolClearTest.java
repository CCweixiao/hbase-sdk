package sample;

import com.github.CCweixiao.thrift.HBaseThriftService;
import com.github.CCweixiao.thrift.HBaseThriftServiceHolder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author leojie 2021/12/2 3:06 下午
 */
public class HBaseThriftPoolClearTest {
    static class Work implements Runnable {
        private final HBaseThriftService thriftService = HBaseThriftServiceHolder.getInstance("internal_dev", 9091, 2);

        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println(Thread.currentThread().getName());
                    System.out.println(thriftService.getByRowKeyToMap("LEO_USER", "a10001"));
                    Thread.sleep( 10 * 1000);
                    // thriftService.clearThriftPool();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("################################################################################################");
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 20; i++) {
            executorService.execute(new Work());
        }
    }
}
