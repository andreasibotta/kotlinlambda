package com.kotlinlambda

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Item
import com.amazonaws.services.dynamodbv2.document.Table
import com.amazonaws.services.dynamodbv2.model.*
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import java.time.OffsetDateTime

fun main() {
    println("Hello World!")
    val app = App()
    app.createTable()
}

class App : RequestHandler<HandlerInput, HandlerOutput> {
    private val translator: PirateTranslator = DefaultPirateTranslator()
    private var tableName: String = ""

    override fun handleRequest(input: HandlerInput?, context: Context?): HandlerOutput {
        createTable()
        val response2 = putItem()

        input?.let {
            return HandlerOutput(it.message, translator.translate(it.message) + "-2-" + "-" + response2)
        }
        return HandlerOutput("", "")
    }

    private fun getDdbClient(): DynamoDB {
        val provider: AWSCredentialsProvider = DefaultAWSCredentialsProviderChain()
        val client: AmazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(EndpointConfiguration("http://localstack:4569", "us-east-1"))
            .withCredentials(provider)
            .build()

        return DynamoDB(client)
    }

    fun createTable(): String {
        var s = ""

        val dynamoDB = getDdbClient()

        val now = OffsetDateTime.now()
        tableName = "Movies" + now.hour + now.minute + now.second

        try {
            s += ("Attempting to create table; please wait...")
            val table: Table = dynamoDB.createTable(tableName,
                listOf(KeySchemaElement("year", KeyType.HASH),
                    KeySchemaElement("title", KeyType.RANGE)),
                listOf(AttributeDefinition("year", ScalarAttributeType.N),
                    AttributeDefinition("title", ScalarAttributeType.S)),
                ProvisionedThroughput(10L, 10L))
            table.waitForActive()
            s += ("Success.  Table status: " + table.description.tableStatus)
        } catch (e: Exception) {
            s += ("Unable to create table: ")
            s += (e.message)
        }

        return s
    }

    private fun putItem(): String {
        var s = ""

        val dynamoDB = getDdbClient()

        val table = dynamoDB.getTable(tableName)

        val year = 2015
        val title = "The Big New Movie"

        val infoMap: MutableMap<String, Any?> = HashMap()
        infoMap["plot"] = "Nothing happens at all."
        infoMap["rating"] = 0

        try {
            s += ("Adding a new item...")
            val outcome = table.putItem(Item()
                .withPrimaryKey("year", year, "title", title)
                .withMap("info", infoMap))
            s += ("""PutItem succeeded: ${outcome.putItemResult}""".trimIndent())
        } catch (e: java.lang.Exception) {
            s += ("Unable to add item: $year $title")
            s += (e.message)
        }

        return s
    }
}