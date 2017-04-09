package com.xxoo.hotel.scrawller;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import com.xxoo.hotel.scrawller.utils.*;
import com.xxoo.hotel.scrawller.utils.ConfigLoader;
import com.xxoo.hotel.scrawller.utils.DBPipeLine;

/**
 *
 * @author ReverieNight@Foxmail.com
 *
 */
public class CtripProcessor implements PageProcessor{

    private Site site = Site.me().setSleepTime(1000).setRetryTimes(3);

    //列表页起始页的正则表达式
    //例如：http://hotels.ctrip.com/hotel/anqing177
    public static final String URL_LIST = "http://hotels\\.ctrip\\.com/hotel/[A-Za-z0-9]+$";

    //列表页翻页的正则表达式
    //例如：http://hotels.ctrip.com/hotel/anqing177/p2
    public static final String URL_LIST_MORE_PAGE = "http://hotels\\.ctrip\\.com/hotel/[A-Za-z0-9]+／p[0-9]+$";

    //详情页的正则表达式
    //例如：http://hotels.ctrip.com/hotel/1070392.html?isFull=F#ctm_ref=hod_sr_lst_dl_n_1_5
    public static final String URL_POST = "http://hotels\\.ctrip\\.com/hotel/\\d+\\.html\\w*";

    //起始页的正则表达式
    public static final String START_URL= "http://hotels\\.ctrip\\.com/domestic-city-hotel\\.html";

    @Override
    public Site getSite() {
        return site;
    }


    @Override
    public void process(Page page) {

        //处理首页，拿到所有城市的入口url
        //if(page.getUrl().regex(START_URL).match()) {
        String regx=ConfigLoader.getConf().getString("scrawllers.ctrip.domestic.START_URL_regex");
        if(page.getUrl().regex(ConfigLoader.getConf().getString("scrawllers.ctrip.domestic.START_URL_regex")).match()) {
            page.addTargetRequests(removeDuplicate(page.getHtml().xpath(
                    ConfigLoader.getConf().getString("scrawllers.ctrip.domestic.xpath.citylist")).links().
                    regex(URL_LIST).all()));
        }
        else {
            //城市酒店列表页
            if (page.getUrl().regex(ConfigLoader.getConf().getString("scrawllers.ctrip.domestic.URL_LIST_regex")).match() ||
                    page.getUrl().regex(ConfigLoader.getConf().getString("scrawllers.ctrip.domestic.URL_LIST_MORE_PAGE_regex")).match()) {
                //Selectable node = page.getHtml().xpath("//div[@id=\"hotel_list\"]").links();
                List<String> l_post = page.getHtml().xpath(ConfigLoader.getConf().getString("scrawllers.ctrip.domestic.xpath.city.hotel_url_list"))
                        .links().regex(URL_POST).all(); //当前城市页的酒店url列表
                page.addTargetRequests(removeDuplicate(l_post));
                //如果是首页，还需要将后面翻页的信息加进来
                if(page.getUrl().regex(URL_LIST).match()) {

                    //如果有多页，先计算最后一页的页号
                    int lastPage = Integer.parseInt(page.getHtml().xpath(ConfigLoader.getConf().getString("scrawllers.ctrip.domestic.xpath.city.total_list_page_number")).get());

                    if(lastPage>1){
                        List<String> l_url = new ArrayList<String>();
                        //需要把所有翻页加入

                        for (int i = 2; i < lastPage; i++)
                            l_url.add(page.getUrl().toString() + "/p" + i);

                        page.addTargetRequests(l_url);
                    }

                }
                //酒店详情页
            } else {
                String ChineseName = page.getHtml().xpath(ConfigLoader.getConf().getString("scrawllers.ctrip.domestic.xpath.city.hotel.ChineseName")).toString();
                String EnglishName = page.getHtml().xpath(ConfigLoader.getConf().getString("scrawllers.ctrip.domestic.xpath.city.hotel.EnglishName")).toString();
                page.putField("hotel_url", page.getUrl().toString());
                page.putField("hotel_cn_name",ChineseName);
                page.putField("hotel_en_name",EnglishName.trim());
                System.out.println();
            }
        }
    }

    //去重
    public static List removeDuplicate(List list) {
        HashSet hs = new HashSet(list);
        list.clear();
        list.addAll(hs);
        return list;
    }


    public static void main(String[] args) {

        SpikeFileCacheQueueScheduler file=new SpikeFileCacheQueueScheduler("/Users/andy/ctrip_tmp/");
        file.setRegx(START_URL);

        Spider.create(new CtripProcessor())
                .addUrl("http://hotels.ctrip.com/hotel/xuanhua1497")	//开始地址
                .addPipeline(new ConsolePipeline())	//打印到控制台
                .addPipeline(new DBPipeLine()) //写入数据库
                .setScheduler(file) //支持重启
                .thread(5)	//开启5线程
                .run();
    }

}
