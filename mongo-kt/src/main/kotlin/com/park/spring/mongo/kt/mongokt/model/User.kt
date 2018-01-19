package com.park.spring.mongo.kt.mongokt.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Document
data class Person(@Id var id: ObjectId? = null, @Indexed var name: String? = null, var age: Int? = null,
                  var address: Address? = null, @DBRef var vehicle: Vehicle? = null)

data class Address(var Country: String, var city: String)
enum class Country { TH, EN, US, UK, CN, JP }

@Document
data class Vehicle(@Id @Indexed var id: String? = null, var type: String? = null, var brand: String? = null)

enum class VehicleType { Car, Motorbike }
enum class VehicleBrand { BMW, Honda }

@Repository
interface PersonRepository : MongoRepository<Person, ObjectId> {
    fun findByName(name: String?): Person
}

@Repository
interface VehicleRepository : MongoRepository<Vehicle, String>


