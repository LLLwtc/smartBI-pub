package com.yin.BiInit.config;

import com.github.rholder.retry.Attempt;
import com.github.rholder.retry.RetryListener;

public class MyRetryListener implements RetryListener {


    @Override
    public <Boolean> void onRetry(Attempt<Boolean> attempt) {
        // 距离上一次重试的时间间隔
        System.out.println("距上一次重试的间隔时间为:" + attempt.getDelaySinceFirstAttempt());
        // 重试次数
        System.out.println("重试次数: " + attempt.getAttemptNumber());
        // 重试过程是否有异常
        System.out.println("重试过程是否有异常:" + attempt.hasException());
        if (attempt.hasException()) {
            System.out.println("异常的原因:" + attempt.getExceptionCause().toString());
        }
        //重试正常返回的结果
        System.out.println("重试结果为:" + attempt.hasResult());

        System.out.println();
    }
}
