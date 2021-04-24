package com.reactive.Learn.Reactive.Spring.Playground

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier


class ErrorHandlingInFluxTest{

  @Test
  fun `handle onError resume inside Flux`(){
    val fluxString = Flux.just("A","B","C")
      .concatWith(Flux.error(RuntimeException("Error Occurred Here")))
      .concatWith( Flux.just("X","Y","Z"))
      .onErrorResume {
        println("Handling Error Here ${it.localizedMessage}")
        Flux.empty()
      }

    StepVerifier.create(fluxString)
      .expectSubscription()
      .expectNext("A","B","C")
//      .expectNext("X","Y","Z")
      .verifyComplete()
  }

  @Test
  fun `handle onError return inside Flux`(){
    val fluxString = Flux.just("A","B","C")
      .concatWith(Flux.error(RuntimeException("Error Occurred Here")))
      .concatWith( Flux.just("X","Y","Z"))
      .onErrorReturn("Default")

    StepVerifier.create(fluxString)
      .expectSubscription()
      .expectNext("A","B","C")
//      .expectNext("X","Y","Z")
      .expectNext("Default")
      .verifyComplete()
  }

  @Test
  fun `handle onError map inside Flux`(){
    val fluxString = Flux.just("A","B","C")
      .concatWith(Flux.error(RuntimeException("Error Occurred Here")))
      .concatWith( Flux.just("X","Y","Z"))
      .onErrorMap {
        println("Handling Error Here ${it.localizedMessage}")
        throw ArithmeticException(it.localizedMessage)
      }

    StepVerifier.create(fluxString)
      .expectSubscription()
      .expectNext("A","B","C")
      .expectError(ArithmeticException::class.java)
      .verify()
  }

  @Test
  fun `handle onError map inside Flux with retry`(){
    val fluxString = Flux.just("A","B","C")
      .concatWith(Flux.error(RuntimeException("Error Occurred Here")))
      .concatWith( Flux.just("X","Y","Z"))
      .onErrorMap {
        println("Handling Error Here ${it.localizedMessage}")
        throw ArithmeticException(it.localizedMessage)
      }
      .retry(2)

    StepVerifier.create(fluxString)
      .expectSubscription()
      .expectNext("A","B","C")
      .expectNext("A","B","C")
      .expectNext("A","B","C")
      .expectError(ArithmeticException::class.java)
      .verify()
  }
}