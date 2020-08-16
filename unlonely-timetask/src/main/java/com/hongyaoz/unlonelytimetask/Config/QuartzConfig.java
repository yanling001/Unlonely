package com.hongyaoz.unlonelytimetask.Config;

import com.hongyaoz.unlonelytimetask.Job.emailJob;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class QuartzConfig {
    /**
     * 1.创建Job对象
     */
    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean() {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        //关联我们自己的Job类
        factory.setJobClass(emailJob.class);
        return factory;
    }

    /**
     * 2.创建Trigger对象
     * 简单的Trigger
     */
    /**
     * Cron Trigger
     */
    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean(JobDetailFactoryBean jobDetailFactoryBean) {
        CronTriggerFactoryBean factory = new CronTriggerFactoryBean();
        factory.setJobDetail(jobDetailFactoryBean.getObject());
        //这里涉及到Cron表达式 可以去看我写的Cron表达式详解博客！！！ 此处代表每10秒钟 调用一次
        factory.setCronExpression("0/10 * * * * ?");
        return factory;
    }
    /**
     * 3.创建Scheduler对象
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(CronTriggerFactoryBean cronTriggerFactoryBean, MyAdaptableJobFactory myAdaptableJobFactory) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        //关联trigger
        factory.setTriggers(cronTriggerFactoryBean.getObject());
        factory.setJobFactory(myAdaptableJobFactory);
        return factory;
    }
}
