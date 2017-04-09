package com.xxoo.hotel.scrawller.db

import java.sql.{Blob => SqlBlob, Timestamp => SqlTimestamp}

import com.sun.crypto.provider.AESCipher.AES128_CBC_NoPadding

class HotelInfo {
  var id: String = _
  def getId: String = id

  var englishName: String = _
  def getEnglishName: String = englishName

  var chineseName: String = _
  def getChineseName: String = chineseName

  var cityId: String = _
  def getCityId: String = cityId

  var url: String = _
  def getUrl: String = url
}

object HotelInfo
{
  def apply (
              id: String,
              englishName: String,
              chineseName: String,
              url: String,
              cityId: String
            ): HotelInfo = {
    val HotelInfoObj = new HotelInfo

    HotelInfoObj.id = id
    HotelInfoObj.chineseName = chineseName
    HotelInfoObj.englishName = englishName
    HotelInfoObj.url = url
    HotelInfoObj.cityId = cityId

    HotelInfoObj
  }
}