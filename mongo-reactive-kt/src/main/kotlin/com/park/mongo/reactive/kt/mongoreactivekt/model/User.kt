package com.park.mongo.reactive.kt.mongoreactivekt.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Document
data class Raider(@Id var id: ObjectId = ObjectId(), @Indexed var name: String? = null, var score: Int = 0)

data class RaiderReactive(var raider: Optional<Raider>, var date: Date)

@Repository
interface RaiderReactiveRepository : ReactiveMongoRepository<Raider, ObjectId> {
    fun findByName(name: String): Flux<Raider>?
    override fun findById(id: ObjectId?): Mono<Raider>
}

@Repository
interface RaiderRepository : MongoRepository<Raider, ObjectId> {
    fun findByName(name: String): List<Raider>?
    fun deleteByName(name: String)
}