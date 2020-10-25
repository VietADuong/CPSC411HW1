package org.csuf.cpsc411Hw1

import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.utils.io.readAvailable
import org.csuf.cpsc411Hw1.Dao.Person.ClaimDao
import org.csuf.cpsc411Hw1.Dao.Person.ClaimEntity

import java.util.*

//import con.almworks.sqlite4java.SQLiteConnection

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    routing {
        ///ClaimService/getAll on GET
        get("/ClaimService/getAll"){
            val claimList = ClaimDao().getAll()
            println("The number of claims : ${claimList.size}")
            // JSON Serialization/Deserialization
            val respJsonStr = Gson().toJson(claimList)
            println("HTTP message is using GET method with /getAll")
            call.respondText(respJsonStr, status= HttpStatusCode.OK, contentType= ContentType.Application.Json)
        }
        ///ClaimService/add on POST
        this.post("/ClaimService/add"){

            val data = call.request.receiveChannel()
            val dataLength = data.availableForRead
            var output = ByteArray(dataLength)
            data.readAvailable(output)
            val str1 = String(output)
            // JSON serialization/deserialization // GSON (Google Library)
            var gsStr = Gson().fromJson(str1, ClaimEntity::class.java)
            var cObj : ClaimEntity
            cObj = ClaimEntity(UUID.randomUUID(), gsStr.title, gsStr.date, isSolved = false)
            ClaimDao().addClaim(cObj)
            println("HTTP message is using POST method with /add ")
            call.respondText("The POST request was successfully processed. ",
                    status= HttpStatusCode.OK, contentType = ContentType.Text.Plain)
        }
    }
}
