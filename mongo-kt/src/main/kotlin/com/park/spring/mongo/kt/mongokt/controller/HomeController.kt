package com.park.spring.mongo.kt.mongokt.controller

import com.park.spring.mongo.kt.mongokt.model.Person
import com.park.spring.mongo.kt.mongokt.model.PersonRepository
import com.park.spring.mongo.kt.mongokt.model.Vehicle
import com.park.spring.mongo.kt.mongokt.model.VehicleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/users")
class PersonController(@Autowired private var personRepository: PersonRepository) {

    @GetMapping("/all")
    fun getAll(): MutableList<Person>? = personRepository.findAll()

    @GetMapping("/{name}")
    fun getUserByName(@PathVariable("name") name: String): Person = personRepository.findByName(name = name)
}

@RestController
@RequestMapping("/vehicles")
class VehicleController(@Autowired private var vehicleRepository: VehicleRepository) {

    @GetMapping("/all")
    fun getAll(): MutableList<Vehicle>? = vehicleRepository.findAll()

    @GetMapping("/{id}")
    fun getVehicleById(@PathVariable("id") id: String): Optional<Vehicle>? = vehicleRepository.findById(id)
}

