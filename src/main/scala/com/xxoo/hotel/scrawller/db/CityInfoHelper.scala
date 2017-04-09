package com.xxoo.hotel.scrawller.db

import java.util.Calendar

import org.apache.ibatis.session.SqlSession
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.JavaConverters._

class CityInfoHelper() {

  @transient
  private[this] val logger: Logger = LoggerFactory.getLogger(getClass.getName)

  def insertNewCity(session: SqlSession, cityInfo: CityInfo): CityInfo = {

    try {
      session.insert("InsertCity", cityInfo)
    } catch {case e:Exception=> { logger.error("Hotel information insertion failure with url of " + cityInfo.url)
      e.printStackTrace()} }

    logger.info("City inserted with id of " + cityInfo.id)
    cityInfo

  }


}
