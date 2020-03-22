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
    // create the statement, and run the select query
    // val statement = connection.createStatement()
    // println(statement)
    // val resultSet = statement.execute(
    //     """CREATE TABLE IF NOT EXISTS users(
    //         user_id INT AUTO_INCREMENT PRIMARY KEY,
    //         name VARCHAR(62),
    //         age INT,
    //         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    //         );
    //     """
    // )

    // val users = Seq(
    //     ("Michele", 42),
    //     ("Silvia", 35),
    //     ("Ugo", 72)
    // ) foreach{
    //     value =>
    //     val command = """INSERT INTO users (name, age)
    //     VALUES (?,?)
    //     """.stripMargin
    //     println(command)
    //     val stmt = connection.prepareStatement(command)
    //     stmt.setString(1, value._1)
    //     stmt.setString(2, value._2.toString())
    //     stmt.execute()
    //     stmt.close()
    // }
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
        "age": 41
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

    // val query = statement.executeQuery("""SELECT * FROM users""")
    // println(query)

    // while (query.next()) {
    //   val id_ = query.getInt("user_id")
    //   val name = query.getString("name")
    //   val age = query.getInt("age")
    //   val ts = query.getTime("created_at")
    //   println(s"\t$id_\t$name\t$age\t$ts")
    // }
    // val resultSet = statement.executeQuery("SELECT id, first_name, last_name, age FROM user")
    // while ( resultSet.next() ) {
    //     val firstName = resultSet.getString("first_name")
    //     val age = resultSet.getInt("age")
    //     println(s"$firstName $age")
    // }
  } catch {
    case e: Throwable => e.printStackTrace
  }
  connection.close()
}
