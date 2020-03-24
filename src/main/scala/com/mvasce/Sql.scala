package com.mvasce

import java.sql.JDBCType
import scalikejdbc._
import scalikejdbc.metadata.Table
import play.api.libs.json.JsObject
import play.api.libs.json.Json
import play.api.libs.json.JsValue
// import scalikejdbc.metadata.Table

object Sql {

  /**
    * Get a map with the column names -> type of the given table
    *
    * @param tableName
    * @return
    */
  def getColumns(tableName: String): Map[String, String] = {
    // val columns = DB.getColumnNames(table)
    val table: Table = DB.getTable(tableName).get
    // define exception
    table.columns.map(col => (col.name, col.typeName)).toMap // typeCode
  }
}

case class TableMachwell(name: String) {
  val table: Table = DB.getTable(name).get
  val columnsMap: Map[String, String] =
    table.columns.map(col => (col.name, col.typeName)).toMap

  def castValue(column: String, value: JsValue): Any = {
    columnsMap.get(column).get match {
      // TODO complete the list
      case "INT"     => value.as[Int]
      case "VARCHAR" => value.as[String]
      case _         => null
    }
  }

  def insert(data: JsValue) = {
    val object_ = data.as[JsObject].value
    object_.keys.mkString(",")
    val keyString = s"(${object_.keys.mkString(",")})"
    val placeholderString = s"(${object_.map(x => "?").mkString(", ")})"
    val keyUpdate = object_.map(x=>s"${x._1}=?").mkString(",\n")
    println(keyUpdate)
    
    println(placeholderString)
    val cmd = s""" INSERT INTO $name 
            $keyString
            VALUES $placeholderString
            ON DUPLICATE KEY UPDATE
            $keyUpdate
            """.trim()
    println(cmd)
    val command = SQL(cmd)
    var casted = object_.map { x => castValue(x._1, x._2) }.toList
    casted ++= casted
    println(casted)
    command.bind(casted: _*).update
  }
}

object Run extends App {
  val driver = "com.mysql.cj.jdbc.Driver"
  Class.forName(driver)
  val url = "jdbc:mysql://127.0.0.1/test"
  val username = "root"
  val password = "example"
  ConnectionPool.singleton(url, username, password)

  val json = Json.parse("""{"user_id":11, "age": 42, "name": "Michele"}""")
  val age = (json \ "age").get

  // println(JDBCType.ARRAY)

  DB autoCommit { implicit session =>
    // val columns = Sql.getColumns("users")
    val table = TableMachwell("users")
    // println(columns)
    println(table)

    val x = table.castValue("age", age)
    println(x)

    val command = table.insert(json)
    command.apply()
  }
}
