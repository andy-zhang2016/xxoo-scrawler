package com.xxoo.hotel.scrawller.utils

import scala.reflect.ClassTag
import com.typesafe.config.ConfigFactory



/**
  * Created by andy on 16/11/29.
  */
object ConfigLoader {
  lazy val config = ConfigFactory.load("scrawllerService.conf")

  def getConf[T: ClassTag](confName: String): T = {
    config.getAnyRef(confName).asInstanceOf[T]
  }

  def getConf() = config

}
