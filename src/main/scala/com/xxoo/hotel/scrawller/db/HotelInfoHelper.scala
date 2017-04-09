package com.xxoo.hotel.scrawller.db

import org.apache.ibatis.session.SqlSession
import org.slf4j.{Logger, LoggerFactory}

/**
  * Created by andy on 2017/4/9.
  */
class HotelInfoHelper {

  @transient
  private[this] val logger: Logger = LoggerFactory.getLogger(getClass.getName)

  def insertNewHotel(session: SqlSession, hotelInfo: HotelInfo): HotelInfo = {

    try {
      session.insert("InsertHotel", hotelInfo)
    } catch {case e:Exception=> { logger.error("Hotel information insertion failure with url of " + hotelInfo.url)
                                  e.printStackTrace()} }

    logger.info("Hotel inserted with id of " + hotelInfo.id)

    hotelInfo

  }
}
