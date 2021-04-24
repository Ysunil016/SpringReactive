package com.reactive.Learn.Reactive.Spring.Playground

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.time.Duration

class FluxMonoCombinedTest{

  @Test
  fun `combine publisher using merge`(){
    val fluxElementsOne = Flux.just("A","B","C")
    val fluxElementsTwo = Flux.just("D","E","F")

    val mergedFlux = Flux.merge(fluxElementsOne,fluxElementsTwo).log()

    StepVerifier.create(mergedFlux)
      .expectSubscription()
      .expectNext("A")
      .expectNextCount(4)
      .expectNext("F")
      .verifyComplete()

  }

  @Test
  fun `combine publisher using merge with delay in flux`(){
    val fluxElementsOne = Flux.just("A","B","C").delayElements(Duration.ofSeconds(5))
    val fluxElementsTwo = Flux.just("D","E","F").delayElements(Duration.ofSeconds(5))

    val mergedFlux = Flux.merge(fluxElementsOne,fluxElementsTwo).log()

    StepVerifier.create(mergedFlux)
      .expectSubscription()
//      .expectNext("A")
      .expectNextCount(6)
//      .expectNext("F")
      .verifyComplete()

  }

  @Test
  fun `combine publisher with concat and delay in flux with sequence - Order is Maintained`(){
    val fluxElementsOne = Flux.just("A","B","C").delayElements(Duration.ofSeconds(5))
    val fluxElementsTwo = Flux.just("D","E","F").delayElements(Duration.ofSeconds(5))

    val mergedFlux = Flux.concat(fluxElementsOne,fluxElementsTwo).log()

    StepVerifier.create(mergedFlux)
      .expectSubscription()
      .expectNext("A")
      .expectNextCount(4)
      .expectNext("F")
      .verifyComplete()
  }

  @Test
  fun `combine publisher using merge using zip`(){
    val fluxElementsOne = Flux.just("A","B","C").delayElements(Duration.ofSeconds(1))
    val fluxElementsTwo = Flux.just("D","E","F").delayElements(Duration.ofSeconds(1))

    val mergedFlux = Flux.zip(fluxElementsOne,fluxElementsTwo).log()

    StepVerifier.create(mergedFlux)
      .expectSubscription()
      .expectNextCount(3)
      .verifyComplete()
  }
}