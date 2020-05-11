package com.kotlinlambda

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Table
import com.amazonaws.services.dynamodbv2.model.*
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import java.time.OffsetDateTime


fun main(args: Array<String>) {
    println("Hello World!")
    val app = App()
    app.createTable()
}

class App : RequestHandler<HandlerInput, HandlerOutput> {
    val translator: PirateTranslator = DefaultPirateTranslator()


    override fun handleRequest(input: HandlerInput?, context: Context?): HandlerOutput {
        val response = createTable()
        input?.let {
            return HandlerOutput(it.message, translator.translate(it.message) + "-2-" + response)
        }
        return HandlerOutput("", "");
    }

    fun createTable(): String {
        var s = ""
        val provider: AWSCredentialsProvider =
//            try {
//                DefaultAWSCredentialsProviderChain()
//            } catch (e: Exception) {
//                println("No default AWS credentials")
                AWSStaticCredentialsProvider(BasicAWSCredentials("localstack", "localstack")) // TODO: Fix
//            }
        s+=("proivder")
        s+=(provider)
        s+=(provider.credentials.awsSecretKey)
        val client: AmazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration("http://localstack:4569", "us-east-1"))
//            .withRegion("us-east-1")
            .withCredentials(provider)
            .build()

        val dynamoDB = DynamoDB(client)

        val now = OffsetDateTime.now()
        val tableName = "Movies" + now.hour + now.minute + now.second

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
}