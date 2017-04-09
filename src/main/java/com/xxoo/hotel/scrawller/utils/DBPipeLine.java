package com.xxoo.hotel.scrawller.utils;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import org.apache.ibatis.session.SqlSession;

import com.xxoo.hotel.scrawller.db.SessionFactory;
import com.xxoo.hotel.scrawller.db.HotelInfo;
import com.xxoo.hotel.scrawller.db.HotelInfoHelper;


/**
 * Created by andy on 2017/4/9.
 */
public class DBPipeLine implements Pipeline{
    @Override
    public void process(ResultItems resultItems, Task task){
      SqlSession session = SessionFactory.getSession();

      String url = resultItems.get("hotel_url");
      String id = resultItems.get("hotel_id");
      String Chinese_name = resultItems.get("hotel_cn_name");
      String English_name = resultItems.get("hotel_en_name");
      String cityId = resultItems.get("cityId");

      HotelInfoHelper htlh = new HotelInfoHelper();

      HotelInfo targetHotel = new HotelInfo();
      targetHotel.apply(id, English_name,Chinese_name,url,cityId );
      htlh.insertNewHotel(session, targetHotel);

      session.close();
    }
}
