package com.reactive.Learn.Reactive.Spring.Playground

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.time.Duration

class MonoAndFluxWithTimeTest{

  @Test
  fun `infinite sequence with flux interval`(){
    val infiniteFlux = Flux.interval(Duration.ofMillis(200)).log()
    infiniteFlux.subscribe { println(it) }
    Thread.sleep(4000)
  }

  @Test
  fun `infinite sequence with flux interval test`(){
    val finiteFlux = Flux.interval(Duration.ofMillis(200)).take(3).log()

    StepVerifier.create(finiteFlux)
      .expectNext(0L)
      .expectNext(1L)
      .expectNext(2L)
      .verifyComplete()
  }

  @Test
  fun `infinite sequence map with flux interval test`(){
    val finiteFlux = Flux.interval(Duration.ofMillis(200))
      .map { it.toInt() }
      .take(3).log()

    StepVerifier.create(finiteFlux)
      .expectNext(0)
      .expectNext(1)
      .expectNext(2)
      .verifyComplete()
  }

  @Test
  fun `infinite sequence map with flux interval test with delay`(){
    val finiteFlux = Flux.interval(Duration.ofMillis(200))
      .delayElements(Duration.ofSeconds(2))
      .map { it.toInt() }
      .take(3).log()

    StepVerifier.create(finiteFlux)
      .expectNext(0)
      .expectNext(1)
      .expectNext(2)
      .verifyComplete()
  }


}