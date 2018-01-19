package com.park.mongo.reactive.kt.mongoreactivekt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MongoReactiveKtApplication

fun main(args: Array<String>) {
    runApplication<MongoReactiveKtApplication>(*args)
}