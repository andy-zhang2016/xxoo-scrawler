package com.xxoo.hotel.scrawller.db

import java.io.Reader

import org.apache.ibatis.io.Resources
import org.apache.ibatis.session.{SqlSession, SqlSessionFactory, SqlSessionFactoryBuilder}
import com.xxoo.hotel.scrawller.utils.ConfigLoader

object SessionFactory {

  private var sessionFactory: SqlSessionFactory = null

  private def getSessionFactory(): SqlSessionFactory = {
    if(sessionFactory == null) {
      this.synchronized {

        var mybatisConfigFile = "mybatis.xml" //default mybatis anchor file
        var jdbcPropFile = "jdbc.properties"    //default jdbc properties file

        if (ConfigLoader.getConf() .hasPath("service.myBatisConfigFile"))
          mybatisConfigFile = ConfigLoader.getConf().getString("service.myBatisConfigFile")

        if (ConfigLoader.getConf().hasPath("service.jdbcPropFile"))
          jdbcPropFile = ConfigLoader.getConf().getString("service.jdbcPropFile")


        val reader: Reader = Resources.getResourceAsReader(mybatisConfigFile)

        val properties = Resources.getResourceAsProperties(jdbcPropFile)
          //we have default # for poolMaximumActiveConnections and poolMaximumIdleConnections
        if(properties.getProperty("poolMaximumActiveConnections")==null)
            properties.setProperty("poolMaximumActiveConnections", "200")
          if(properties.getProperty("poolMaximumIdleConnections")==null)
            properties.setProperty("poolMaximumIdleConnections", "20")
          sessionFactory = new SqlSessionFactoryBuilder().build(reader, properties)
      }
    }
    sessionFactory
  }

  def getSession: SqlSession = {
    getSessionFactory().openSession()
  }

  def closeSession(session: SqlSession): Unit = {
    if(session != null){
      try{
        session.close()
      } catch {
        case e: Exception => // Do Nothing
      }
    }
  }

}
