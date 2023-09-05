package com.yin.BiInit.guava_retrying;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.base.Predicates;
import com.yin.BiInit.config.MyRetryListener;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 使用 guava-retrying demo
 */
public class TestGuavaRetry {

    static Retryer<Boolean> retryer;

    static {
        retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfException() // 抛出异常会进行重试
                .retryIfResult(Predicates.equalTo(false)) // 如果接口返回的结果不符合预期,也需要重试
                .withWaitStrategy(WaitStrategies.fixedWait(1, TimeUnit.SECONDS)) // 重试策略, 此处设置的是重试间隔时间
                .withStopStrategy(StopStrategies.stopAfterAttempt(5)) // 重试次数
                .withRetryListener(new MyRetryListener())
                .build();
    }


    public boolean uploadFile(String fileName) {
        try {

            return retryer.call(new Callable<Boolean>() {
                int count = 0;
                @Override
                public Boolean call() throws Exception {
                    return new TestGuavaRetry().uploadPicture(fileName, count++);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean uploadPicture(String fileName, int count) {
        System.out.println("开始上传文件:" + fileName);
        // 模拟在第3次重试成功
        if (count == 3) {
            System.out.println("文件上传成功, 重试次数:" + count);
            return true;
        }
        // 模拟因网络等原因导致的图片上传服务超时
        return false;
    }



    public static void main(String[] args) {
        new TestGuavaRetry().uploadFile("testFile");
    }
}


