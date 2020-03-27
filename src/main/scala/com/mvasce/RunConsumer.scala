package com.mvasce
// import scopt.OptionParser
import scopt.OParser

case class Table(database: String, table: String)

case class Config(
    replica_db_conn: String = "",
    replica_tables: String = ""
) {

  def getTables(): List[Table] = {
    replica_tables
    .replace(" ", "")
    .split(',')
    .map { x =>
      val splitted = x.split('.')
      Table(splitted(0), splitted(1))
    }.toList
  }

}

object RunConsumer extends App {

  val builder = OParser.builder[Config]

  val parser = {
    import builder._
    OParser.sequence(
      programName("RunConsumer"),
      head("Consumer", "0.1"),
      opt[String]('r', "replica_db_conn")
        .required()
        .action((x, c) => c.copy(replica_db_conn = x))
        .text("JDBC replica DB connection"),
      opt[String]('t', "replica_tables")
        .required()
        .action((x, c) => c.copy(replica_tables = x))
        .text(
          "Comma separated list of tables to be replicated (DB.TABLE): DB1.table1, DB2.table"
        ),
      help("help").text("prints this usage text")
    )
  }

  OParser.parse(parser, args, Config()) match {
    case Some(config) =>
      println(config)
      println(config.replica_tables)
      // val tables = config.replica_tables
      //   .replace(" ", "")
      //   .split(',')
      //   .map { x =>
      //     val splitted = x.split('.')
      //     Table(splitted(0), splitted(1))
      //   }
      //   .toList
      val tables = config.getTables()

      println(tables)

    case _ =>
      println("Error")
  }
}
