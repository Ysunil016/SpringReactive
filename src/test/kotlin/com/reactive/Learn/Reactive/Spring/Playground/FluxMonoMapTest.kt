package com.reactive.Learn.Reactive.Spring.Playground

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
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


}