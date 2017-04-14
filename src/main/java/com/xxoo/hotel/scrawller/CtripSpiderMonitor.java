package com.xxoo.hotel.scrawller;

import com.xxoo.hotel.scrawller.utils.ConfigLoader;
import com.xxoo.hotel.scrawller.utils.DBPipeLine;
import com.xxoo.hotel.scrawller.utils.SpikeFileCacheQueueScheduler;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.monitor.SpiderMonitor;

/**
 * Created by andy on 2017/4/9.
 */
public class CtripSpiderMonitor {

    //起始页的正则表达式
    public static final String START_URL= "http://hotels\\.ctrip\\.com/domestic-city-hotel\\.html";

    public static void main(String[] args) throws Exception {



        SpikeFileCacheQueueScheduler file=new SpikeFileCacheQueueScheduler(ConfigLoader.getConf().getString("scrawllers.ctrip.domestic.queueSchedulerDir"));
        file.setRegx(START_URL);

            Spider ctripSpider = Spider.create(new CtripProcessor())
                    .addUrl(ConfigLoader.getConf().getString("scrawllers.ctrip.domestic.start_url"))	//开始地址
                    .addPipeline(new ConsolePipeline())	//打印到控制台
                    .addPipeline(new DBPipeLine()) //写入数据库
                    .setScheduler(file)
                    .thread(ConfigLoader.getConf().getInt("scrawllers.ctrip.domestic.thread_number"));	//开启多线程



        SpiderMonitor.instance().register(ctripSpider);


        ctripSpider.start();

    }
}
