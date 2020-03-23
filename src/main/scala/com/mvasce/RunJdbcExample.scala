// https://alvinalexander.com/scala/scala-jdbc-connection-mysql-sql-select-example

package com.mvasce

import java.sql.DriverManager
import java.sql.Connection

import com.mvasce.Maxwell.getCommand

object RunJdbcExample extends App {
  val driver = "com.mysql.cj.jdbc.Driver"
  val url = "jdbc:mysql://127.0.0.1/test"
  val username = "root"
  val password = "example"

  var connection: Connection = null

  try {
    Class.forName(driver)
    connection = DriverManager.getConnection(url, username, password)
    val jsonString = """{
   "database":"test",
   "table":"users",
   "type":"insert",
   "ts":1477053217,
   "xid":23396,
   "commit":true,
   "position":"master.000006:800911",
   "server_id":23042,
   "thread_id":108,
   "primary_key": [10],
   "primary_key_columns": ["user_id"],
   "data":{
        "name": "Stefano",
        "age": 41,
        "created_at": "2020-03-23 17:34:41"
        }
    }
    """.stripMargin

    val (command, values) = getCommand(jsonString)
    println(command)
    println(values)

    val statement = connection.prepareStatement(command)
    var counter = 1
    values.foreach{
        x =>
        statement.setString(counter, x)
        counter += 1
    }
    statement.execute()
    statement.close()
    println("Statememt executed")
  } catch {
    case e: Throwable => e.printStackTrace
  }
  connection.close()
}
