package sample;

import com.github.CCweixiao.thrift.HBaseThriftService;

import java.util.Random;

/**
 * @author leojie 2020/12/27 11:59 下午
 */
public class HBaseThriftPoolTest {
    public static void main(String[] args) {
        HBaseThriftService hBaseThriftService = HBaseThriftService.getInstance("localhost", 9090);
        Random random = new Random();

        while (true) {

            System.out.println(hBaseThriftService);

            try {
                int r = random.nextInt(10) + 1;
                //int r = 4;
                System.out.println("即将等待：" + r + "分钟");
                Thread.sleep(r * 60 * 1000);
                System.out.println(hBaseThriftService);
                //System.out.println("getNumIdle=" + hBaseThriftPool.getNumIdle());
                //System.out.println("getNumActive=" + hBaseThriftPool.getNumActive());
                System.out.println("等待时间：" + r + "分钟");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("################################################################################################");
        }

    }
}
