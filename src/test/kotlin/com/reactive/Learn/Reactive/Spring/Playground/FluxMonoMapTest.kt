package com.reactive.Learn.Reactive.Spring.Playground

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import reactor.test.StepVerifier


class FluxMonoMapTest {

  @Test
  fun `transform flux using map and filter`() {
    val listElements = listOf("Adam", "Eve", "Edan", "Andy")
    val fluxOfString =
      Flux.fromIterable(listElements) // Publisher or Data Emitter
        .filter { it.length > 3 }
        .map { "$it Bull" }.log()

    StepVerifier.create(fluxOfString)
      .expectNext("Adam Bull")
      .expectNext("Edan Bull")
      .expectNext("Andy Bull")
      .verifyComplete()

  }

  @Test
  fun `transform flux using map for length`() {
    val listElements = listOf("Adam", "Eve", "Edan", "Andy")
    val fluxOfStringLength = Flux.fromIterable(listElements).map { it.length }.log()

    StepVerifier.create(fluxOfStringLength)
      .expectNext(4)
      .expectNext(3)
      .expectNext(4)
      .expectNext(4)
      .verifyComplete()

  }

  @Test
  fun `transform flux using map for length with repeat`() {
    val listElements = listOf("Adam", "Eve", "Edan", "Andy")
    val fluxOfStringLength = Flux.fromIterable(listElements).map { it.length }.repeat(5).log()

    StepVerifier.create(fluxOfStringLength)
      .expectNext(4)
      .expectNext(3)
      .expectNext(4)
      .expectNext(4)
      .expectNext(4)
      .expectNext(3)
      .expectNext(4)
      .expectNext(4)
      .expectNext(4)
      .expectNext(3)
      .expectNext(4)
      .expectNext(4)
      .expectNext(4)
      .expectNext(3)
      .expectNext(4)
      .expectNext(4)
      .expectNext(4)
      .expectNext(3)
      .expectNext(4)
      .expectNext(4)
      .expectNext(4)
      .expectNext(3)
      .expectNext(4)
      .expectNext(4)
      .verifyComplete()

  }


  @Test
  fun `transform flux using flat map`() {
    val listElements = listOf("Adam", "Eve", "Edan", "Andy", "Sunil", "Parvati")
    val fluxOfStringLength = Flux.fromIterable(listElements)
      .flatMap { Flux.fromIterable(convertToList(it)) } // DB Call or External Calls that Returns Flux
      .log()

    StepVerifier.create(fluxOfStringLength)
      .expectNextCount(12)
      .verifyComplete()
  }

  @Test
  fun `transform flux using flat map parallel`() {
    val listElements = listOf("Adam", "Eve", "Edan", "Andy", "Sunil", "Parvati")
    val fluxOfStringLength = Flux.fromIterable(listElements)
      .window(2) // Flux of String - ("Adam", "Eve"), ("Edan", "Andy")
      .flatMap { value ->
        value.map { this.convertToList(it) }.subscribeOn(Schedulers.parallel()) // Parallel Executing
      } // DB Call or External Calls that Returns Flux
      .flatMap { Flux.fromIterable(it) }
      .log()

    StepVerifier.create(fluxOfStringLength)
      .expectNextCount(12)
      .verifyComplete()
  }

  private fun convertToList(el:String): List<String> {
    Thread.sleep(10000)
    return listOf(el,"New")
  }

}