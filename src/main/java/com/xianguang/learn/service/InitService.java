package com.xianguang.learn.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * @Date
 * @Author wxg
 * 模拟并发请求
 */
@Component
public class InitService {
    private static final Logger log= LoggerFactory.getLogger(InitService.class);
    /**
     * 请求线程数量
     */
    private static final Integer threadNum = 100;

    /**
     * 处理抢购服务
     */
    @Autowired
    private CommonMQService commonMQService;

    /**
     * 模拟手机号码
     */

    private static  Integer mobile = 0;

    /**
     * 产生多线程
     */
    public void generateMultiThread(){
        log.info("开始初始化线程数----->");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try{
            for (int i = 0; i < threadNum; i++) {
                new Thread(new RunThread(countDownLatch)).start();
            }
        }catch (Exception e){
            log.info("线程初始化错误,{}",e.fillInStackTrace());
        }finally {
            //当前线程退出
            countDownLatch.countDown();
        }
    }

    private class RunThread implements Runnable{

        private final CountDownLatch startLatch;

        public RunThread(CountDownLatch countDownLatch) {
            this.startLatch = countDownLatch;
        }

        @Override
        public void run() {
            //TODO:线程等待
            try {
                startLatch.await();
                mobile+=1;
                //输出手机号
//                log.info("mobile:{}", String.valueOf(mobile));
                //当前手机号 送入队列排队抢购
                commonMQService.sendRobbingMsg(String.valueOf(mobile));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}













