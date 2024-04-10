package com.klover.xxl.job.jobs;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * TestJob
 *
 * @author klover
 * @date 2024/4/9 9:58
 */
@Slf4j
@Component
public class TestJob {
    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("testJobHandler")
    public String testJobHandler() {
        String param = XxlJobHelper.getJobParam();
        log.info("XXL-JOB, Hello World. param:{}", param);
        System.out.println(param);
        return "SUCCESS";
    }
}
