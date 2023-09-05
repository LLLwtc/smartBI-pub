package com.yin.BiInit.api;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.yin.BiInit.config.MyRetryListener;
import com.yin.BiInit.manager.AiManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 调用 AI 平台的示例代码
 */
@Service
@Slf4j
public class OpenAiApi {
    @Resource
    private AiManager aiManager;
    static Retryer<String> retryer;

    //guavaRetry
    static {
        retryer = RetryerBuilder.<String>newBuilder()
                .retryIfException() // 抛出异常会进行重试
//                .retryIfResult(Predicates.equalTo(false)) // 如果接口返回的结果不符合预期,也需要重试
                .retryIfResult(string->string.isEmpty())// 当返回值为空时重试
                .withWaitStrategy(WaitStrategies.fixedWait(1, TimeUnit.SECONDS)) // 重试策略, 此处设置的是重试间隔时间
                .withStopStrategy(StopStrategies.stopAfterAttempt(5)) // 重试次数
                .withRetryListener(new MyRetryListener())//监视器，日志打印重试情况
                .build();
    }

    /**
     * 调用AI服务进行重试
     * @param biModelId
     * @param userInput
     * @return
     */
    public String getAIResult(long biModelId, StringBuilder userInput) {
        try {
            return retryer.call(new Callable<String>() {
                int count=0;
                @Override
                public String call() throws Exception {
                    if(count!=0)log.info("重试调用");
                    count++;
                    return aiManager.doChat(biModelId,userInput.toString());
                }
            });
        } catch (Exception e) {
//            e.printStackTrace();
            log.error(e.getMessage(),e);
            return "调用AI服务重试出错";
        }
    }
//        /**
//         * AI 对话（需要自己创建请求响应对象）
//         *
//         * @param request
//         * @param openAiApiKey
//         * @return
//         */
//        public CreateChatCompletionResponse createChatCompletion(CreateChatCompletionRequest request, String openAiApiKey) {
//            if (StringUtils.isBlank(openAiApiKey)) {
//                throw new BusinessException(ErrorCode.PARAMS_ERROR, "未传 openAiApiKey");
//            }
//            String url = "https://api.openai.com/v1/chat/completions";
//            String json = JSONUtil.toJsonStr(request);
//            String result = HttpRequest.post(url)
//                    .header("Authorization", "Bearer " + openAiApiKey)
//                    .body(json)
//                    .execute()
//                    .body();
//            return JSONUtil.toBean(result, CreateChatCompletionResponse.class);
//        }
}
