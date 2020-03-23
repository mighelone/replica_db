package com.mvasce

import play.api.libs.json.Json
import play.api.libs.json.JsObject

object Maxwell {


    def getCommand(jsonString: String):(String, List[String]) = {
        val json = Json.parse(jsonString)
    
        val table = (json \ "table").get.as[String]
        val database = (json \ "database").get.as[String]
        val commandType = (json \ "type").get.as[String]
    
        val data = (json \ "data").get.as[JsObject]
    
        val keyString = s"(${data.keys.mkString(", ")})"
        val placeHolder = s"(${List.fill(data.keys.toList.length)("?").mkString(", ")})"

        val command = s"""INSERT INTO $database.$table
        $keyString
        VALUES $placeHolder
        """
        val values = data.values.map(_.toString().replace("\"", "")).toList
        (command, values)
    }


    // val fieldString = data.get.
}