package com.xxoo.hotel.scrawller.utils;

import com.xxoo.hotel.scrawller.db.*;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import org.apache.ibatis.session.SqlSession;


/**
 * Created by andy on 2017/4/9.
 */
public class DBPipeLine implements Pipeline{
    @Override
    public void process(ResultItems resultItems, Task task){
      SqlSession session = SessionFactory.getSession();

      if(resultItems.get("cityURL") != null)
      //现在在处理city 页
      {
          CityInfoHelper cyh = new CityInfoHelper();
          CityInfo targetCity = CityInfo.apply(resultItems.get("cityId"),resultItems.get("City_EN_Name"), resultItems.get("City_CN_Name"),resultItems.get("province"),
                  resultItems.get("cityURL"),  resultItems.get("hotelCount"));

          cyh.insertNewCity(session, targetCity);
      }

      else {

          String url = resultItems.get("hotel_url");
          Integer id = resultItems.get("hotel_id");
          String Chinese_name = resultItems.get("hotel_cn_name");
          String English_name = resultItems.get("hotel_en_name");
          String cityId = resultItems.get("hotel_CityId");

          HotelInfoHelper htlh = new HotelInfoHelper();

          HotelInfo targetHotel = HotelInfo.apply(id, English_name, Chinese_name, url, cityId);
          htlh.insertNewHotel(session, targetHotel);
      }

      session.close();
    }
}
