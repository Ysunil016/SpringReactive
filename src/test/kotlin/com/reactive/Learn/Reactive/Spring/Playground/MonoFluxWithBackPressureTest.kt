package com.reactive.Learn.Reactive.Spring.Playground

import org.junit.jupiter.api.Test
import reactor.core.publisher.BaseSubscriber
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class MonoFluxWithBackPressureTest {

  @Test
  fun `flux with back pressure`() {
    val finiteFlux = Flux.range(1, 10).log()

    StepVerifier.create(finiteFlux)
      .thenRequest(3)
      .expectNextCount(3)
      .thenRequest(3)
      .expectNextCount(3)
      .thenCancel()
      .verify()
  }

  @Test
  fun `flux with back pressure programatically`() {
    val finiteFlux = Flux.range(1, 10).log()
    finiteFlux.subscribe(
      { println("Element is $it") },
      { println("Has Error") },
      { println("Completed") },
      { r -> r.request(2) }
    )
  }

  @Test
  fun `flux with back pressure with cancle programatically`() {
    val finiteFlux = Flux.range(1, 10).log()
    finiteFlux.subscribe(
      { println("Element is $it") },
      { println("Has Error") },
      { println("Completed") },
      { r -> r.cancel() }
    )
  }

  @Test
  fun `flux with custom back pressure programatically`() {
    val finiteFlux = Flux.range(1, 10).log()
    finiteFlux.subscribe(CustomSubscriber())
  }

  class CustomSubscriber : BaseSubscriber<Int>() {
    override fun hookOnNext(value: Int) {
      request(1)
      println("Element is - $value")
      if (value == 4) cancel()
    }
  }
}

