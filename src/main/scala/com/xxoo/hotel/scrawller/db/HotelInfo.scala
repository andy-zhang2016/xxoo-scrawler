package com.xxoo.hotel.scrawller.db


class HotelInfo {
  var id: Integer = _
  def getId: Integer = id

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
              id: Integer,
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