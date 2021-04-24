package com.reactive.Learn.Reactive.Spring.Playground

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class FluxMonoFilterTest{

  @Test
  fun `filter test`(){
    val listElements = listOf("Adam","Eve","Edan","Andy")
    val fluxOfString = Flux.fromIterable(listElements).filter{ it.startsWith("A") }.log()

    StepVerifier.create(fluxOfString)
      .expectNext("Adam")
      .expectNext("Andy")
      .verifyComplete()

  }

  @Test
  fun `filter test with length`(){
    val listElements = listOf("Adam","Eve","Edan","Andy")
    val fluxOfString = Flux.fromIterable(listElements).filter{ it.length > 3 }.log()

    StepVerifier.create(fluxOfString)
      .expectNext("Adam","Edan","Andy")
      .verifyComplete()

  }

}