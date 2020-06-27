package br.com.pintos.framework.model

import java.io.File
import java.io.FileReader
import java.util.*

class DBProperties(banco: String) {
  private val prop = properties()
  val driver = prop?.getProperty("datasource.$banco.databaseDriver") ?: ""
  val url = prop?.getProperty("datasource.$banco.databaseUrl") ?: ""
  val username = prop?.getProperty("datasource.$banco.username") ?: ""
  val password = prop?.getProperty("datasource.$banco.password") ?: ""
  val test = prop?.getProperty("test") == "true"
  
  companion object {
    private val propertieFile
      get() = System.getProperty("ebean.props.file")
    
    private fun properties(): Properties? {
      val properties = Properties()
      val file = File(propertieFile)
      
      properties.load(FileReader(file))
      return properties
    }
    
    fun initializePropertyFile() {
      val home = System.getenv("HOME")
      val fileName = System.getenv("EBEAN_PROPS") ?: "$home/ebean.pintos.properties"
      System.setProperty("ebean.props.file", fileName)
    }
  }
}