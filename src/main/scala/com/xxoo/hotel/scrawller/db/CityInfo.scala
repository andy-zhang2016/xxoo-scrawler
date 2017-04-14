package com.xxoo.hotel.scrawller.db


class CityInfo {

  var id: String = _
  def getId: String = id

  var englishName: String = null
  def getEnglishName = englishName

  var chineseName: String = null
  def getChineseName = chineseName

  var province: String = null
  def getProvince = province

  var url: String = null
  def getUrl = url

  var hotelCount: Int = _
  def getHotelCount = hotelCount
  }

object CityInfo
{
  def apply (
              id: String,
              englishName: String,
              chineseName: String,
              province: String,
              url: String,
              hotelCount: Int
            ): CityInfo = {
    val CityInfoObj = new CityInfo

    CityInfoObj.id = id
    CityInfoObj.englishName = englishName
    CityInfoObj.chineseName = chineseName
    CityInfoObj.province= province
    CityInfoObj.url = url
    CityInfoObj.hotelCount = hotelCount

    CityInfoObj

  }
}