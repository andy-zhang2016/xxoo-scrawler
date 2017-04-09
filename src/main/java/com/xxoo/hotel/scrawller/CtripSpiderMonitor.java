package com.xxoo.hotel.scrawller;

import com.xxoo.hotel.scrawller.utils.DBPipeLine;
import com.xxoo.hotel.scrawller.utils.SpikeFileCacheQueueScheduler;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.monitor.SpiderMonitor;

/**
 * Created by andy on 2017/4/9.
 */
public class CtripSpiderMonitor {

    public static void main(String[] args) throws Exception {



            SpikeFileCacheQueueScheduler file=new SpikeFileCacheQueueScheduler("/Users/andy/ctrip_tmp/");
            file.setRegx("");

            Spider ctripSpider = Spider.create(new CtripProcessor())
                    .addUrl("http://hotels.ctrip.com/hotel/anqing177")	//开始地址
                    .addPipeline(new ConsolePipeline())	//打印到控制台
                    .addPipeline(new DBPipeLine()) //写入数据库
                    .setScheduler(file)
                    .thread(5);	//开启5线程



        SpiderMonitor.instance().register(ctripSpider);


        ctripSpider.start();

    }
}
