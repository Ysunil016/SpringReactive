package com.reactive.Learn.Reactive.Spring.Playground

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.function.Supplier

class MonoFluxFactoryTest{

  @Test
  fun `flux from iterables`(){
    var listElements = listOf("Adam","Eve","Edan")
    val fluxOfString = Flux.fromIterable(listElements).log()

    StepVerifier.create(fluxOfString)
      .expectNextCount(2)
      .expectNext("Edan")
      .verifyComplete()

  }

  @Test
  fun `flux from array`(){
    var arrayElements = arrayOf("Adam","Eve","Edan")
    val fluxOfString = Flux.fromArray(arrayElements).log()

    StepVerifier.create(fluxOfString)
      .expectNextCount(2)
      .expectNext("Edan")
      .verifyComplete()
  }

  @Test
  fun `flux using stream`(){
    var arrayElements = listOf("Adam","Eve","Edan")
    val fluxOfString = Flux.fromStream(arrayElements.stream()).log()

    StepVerifier.create(fluxOfString)
      .expectNextCount(2)
      .expectNext("Edan")
      .verifyComplete()
  }

  @Test
  fun `mono using Just or Empty`(){
    val emptyMono = Mono.justOrEmpty<String>(null)
    StepVerifier.create(emptyMono)
      .verifyComplete()
  }

  @Test
  fun `mono using Supplier`(){
    var supplier = Supplier{ "Sunil" }
    val monoInput = Mono.fromSupplier(supplier).log()
    StepVerifier.create(monoInput)
      .expectNext("Sunil")
      .verifyComplete()
  }

  @Test
  fun `flux using range`(){
    val monoInput = Flux.range(0,5).log()
    StepVerifier.create(monoInput)
      .expectNextCount(3)
      .expectNext(3)
      .expectNextCount(1)
      .verifyComplete()
  }


}