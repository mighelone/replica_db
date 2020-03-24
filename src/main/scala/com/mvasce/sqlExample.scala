/*
 * https://manuel.bernhardt.io/2014/02/04/a-quick-tour-of-relational-database-access-with-scala/
 */
package com.mvasce

import scalikejdbc._

object sqlExample {

  def main(args: Array[String]): Unit = {
    // initialize JDBC driver & connection pool
    val driver = "com.mysql.cj.jdbc.Driver"
    Class.forName(driver)
    val url = "jdbc:mysql://127.0.0.1/test"
    val username = "root"
    val password = "example"
    ConnectionPool.singleton(url, username, password)

    // ad-hoc session provider on the REPL

    DB autoCommit { implicit session =>
      val table = DB.getTable("users").get
      println(table)
      val columns = DB.getColumnNames("users")
      println(columns)
      val col =table.columns(0)
      println(col.typeName)
      // val columns = "(name, age)"
      // val placeHolders = "VALUES (?,?)"
      // val command = SQL(s"""INSERT INTO users $columns
      // $placeHolders
      // """.trim())
      // command.bind("Michele", "42").update.apply()
    }

    

    // val result = DB readOnly {
    //   implicit session =>
    //   SQL("SELECT * FROM test.users").map(rs => rs.toMap()).list().apply()
    // }
    // result.foreach{
    //   x => println(x)
    // }

  }
}
