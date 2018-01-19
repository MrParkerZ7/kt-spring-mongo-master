package com.park.mongo.reactive.kt.mongoreactivekt.controller

import com.park.mongo.reactive.kt.mongoreactivekt.model.Raider
import com.park.mongo.reactive.kt.mongoreactivekt.model.RaiderReactive
import com.park.mongo.reactive.kt.mongoreactivekt.model.RaiderReactiveRepository
import com.park.mongo.reactive.kt.mongoreactivekt.model.RaiderRepository
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.util.function.Tuple2

import java.time.Duration
import java.util.*
import java.util.function.Function
import java.util.stream.Stream


@RestController
@RequestMapping("/reactive")
class RaiderReactiveController(@Autowired var raiderReactiveRepository: RaiderReactiveRepository,
                               @Autowired var raiderRepository: RaiderRepository) {

    @GetMapping("/all")
    fun getAll(): Flux<Raider>? = raiderReactiveRepository.findAll()

    @GetMapping("/name/{name}")
    fun getByName(@PathVariable("name") name: String): Flux<Raider>? = raiderReactiveRepository.findByName(name)

    @GetMapping("/id/{id}")
    fun getById(@PathVariable("id") id: ObjectId): Mono<Raider> = raiderReactiveRepository.findById(id)

    @GetMapping("/action/{id}/{sec}", produces = arrayOf(MediaType.TEXT_EVENT_STREAM_VALUE))
    fun getActionRaider(@PathVariable("id") id: ObjectId, @PathVariable("sec") sec: Int): Flux<RaiderReactive>? {
        return raiderReactiveRepository.findById(id)
                .flatMapMany { raider ->
                    val interval = Flux.interval(Duration.ofSeconds(2))
                    val raiderReactiveFlux = Flux.fromStream(
                            Stream.generate {
                                RaiderReactive(raider = raiderRepository.findById(id), date = Date())
                            }
                    )
                    Flux.zip(interval, raiderReactiveFlux)
                            .map<RaiderReactive>(Function<Tuple2<Long, RaiderReactive>, RaiderReactive> { it.t2 })
                }
    }

    @PostMapping("/post")
    fun postRaider(@RequestBody raider: Raider): Mono<Raider> = raiderReactiveRepository.save(raider)
}

@RestController
@RequestMapping("/raiders")
class RaiderController(@Autowired var raiderRepository: RaiderRepository) {

    @GetMapping("/all")
    fun getAll(): List<Raider> = raiderRepository.findAll()

    @GetMapping("/name/{name}")
    fun getByName(@PathVariable("name") name: String): List<Raider>? = raiderRepository.findByName(name)

    @GetMapping("/id/{id}")
    fun getById(@PathVariable("id") id: ObjectId): Optional<Raider>? = raiderRepository.findById(id)

    @PutMapping("/put")
    fun putRaider(@RequestBody raider: Raider): Raider = raiderRepository.insert(raider)!!

    @PostMapping("/post")
    fun postRaider(@RequestBody raider: Raider): Raider = raiderRepository.save(raider)!!

    @DeleteMapping("/name/{name}")
    fun deleteByName(@PathVariable("name") name: String): String = raiderRepository.deleteByName(name).let { return@let "Delete Name: $name Successful" }

    @DeleteMapping("/id/{id}")
    fun deleteById(@PathVariable("id") id: ObjectId): String = raiderRepository.deleteById(id).let { return@let "Delete Id: $id Successful" }

}