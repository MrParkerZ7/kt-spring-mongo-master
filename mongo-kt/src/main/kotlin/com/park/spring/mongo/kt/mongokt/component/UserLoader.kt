package com.park.spring.mongo.kt.mongokt.component

import com.park.spring.mongo.kt.mongokt.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class UserLoader(@Autowired private var personRepository: PersonRepository,
                 @Autowired private var vehicleRepository: VehicleRepository) : CommandLineRunner {

    override fun run(vararg args: String?) {

        personRepository.deleteAll()
        val people: List<Person> = listOf(
                Person(null, "Park", 23,
                        Address(Country.TH.toString(), "Sisaket"),
                        Vehicle("001", VehicleType.Motorbike.toString(), VehicleBrand.BMW.toString())),
                Person(null, "Suck", 27,
                        Address(Country.US.toString(), "Tasksus"),
                        Vehicle("002", VehicleType.Car.toString(), VehicleBrand.BMW.toString())),
                Person(null, "Fuck", 25,
                        Address(Country.JP.toString(), "Toko"),
                        Vehicle("003", VehicleType.Car.toString(), VehicleBrand.Honda.toString())),
                Person(null, "Duck", 19,
                        Address(Country.CN.toString(), "Cendo"),
                        Vehicle("004", VehicleType.Motorbike.toString(), VehicleBrand.Honda.toString())))
        personRepository.saveAll(people)

    }
}