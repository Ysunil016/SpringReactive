package com.reactive.Learn.Reactive.Spring.Playground

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import reactor.test.scheduler.VirtualTimeScheduler
import java.time.Duration
import java.util.function.Supplier


class VirtualTimeTest{

  @Test
  fun `testing without virtual time`(){
    val finiteFlux = Flux
      .interval(Duration.ofSeconds(1))
      .take(3)
      .log()

    StepVerifier.create(finiteFlux)
      .expectSubscription()
      .expectNext(0L)
      .expectNext(1L)
      .expectNext(2L)
      .verifyComplete()
  }

  @Test
  fun `testing with virtual time for simulating clock to make test fast`(){
    VirtualTimeScheduler.getOrSet()
    val finiteFlux = Flux.interval(Duration.ofSeconds(1)).take(3).log()

    StepVerifier.withVirtualTime { finiteFlux }
      .expectSubscription()
      .thenAwait(Duration.ofSeconds(4))
      .expectNext(0L)
      .expectNext(1L)
      .expectNext(2L)
      .verifyComplete()
  }

  @Test
  fun `combine publisher using merge with delay in flux with virtual time`(){
    VirtualTimeScheduler.getOrSet()

    val fluxElementsOne = Flux.just("A","B","C").delayElements(Duration.ofSeconds(5))
    val fluxElementsTwo = Flux.just("D","E","F").delayElements(Duration.ofSeconds(5))

    val mergedFlux = Flux.merge(fluxElementsOne,fluxElementsTwo).log()

    StepVerifier.withVirtualTime { mergedFlux }
      .expectSubscription()
      .thenAwait(Duration.ofSeconds(15))
      .expectNextCount(6)
      .expectComplete()
      .verify()

  }

}