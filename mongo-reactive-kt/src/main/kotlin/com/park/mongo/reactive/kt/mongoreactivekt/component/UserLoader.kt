package com.park.mongo.reactive.kt.mongoreactivekt.component

import com.park.mongo.reactive.kt.mongoreactivekt.model.Raider
import com.park.mongo.reactive.kt.mongoreactivekt.model.RaiderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class UserLoader(@Autowired var raiderRepository: RaiderRepository) : CommandLineRunner {

    override fun run(vararg args: String?) {
//        raiderRepository.deleteAll()
//
//        val raiders: List<Raider> = listOf(
//                Raider(name = "Park"),
//                Raider(name = "Fuck"),
//                Raider(name = "Duck"),
//                Raider(name = "Muck"))
//
//        raiderRepository.saveAll(raiders)
    }

}