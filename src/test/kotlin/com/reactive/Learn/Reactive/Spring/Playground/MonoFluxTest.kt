package com.reactive.Learn.Reactive.Spring.Playground

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class MonoFluxTest{

  @Test
  fun `testing flux with exception invoking onError`(){
    val allString = Flux.just("Sunil","linux")
      .concatWith(Flux.error(RuntimeException("Error Occurred Here")))
      .concatWith(Flux.just("AFTER EXCEPTION"))
      .log("Log for Fetching Data")

    allString
      .subscribe(
        { value -> println(value) },
        { error -> println(error.localizedMessage) },
        { println("Task Completed") }
      )
  }

  @Test
  fun `testing flux without exception and with onComplete Method invoke`(){
    val allString = Flux.just("Sunil","linux")
      .concatWith(Flux.just("Last"))
      .log("Log for Fetching Data")

    allString
      .subscribe(
        { value -> println(value) },
        { error -> println(error.localizedMessage) },
        { println("Task Completed") }
      )
  }


  @Test
  fun `testing flux for assertion`(){
    val allString = Flux.just("Sunil","linux")
      .concatWith(Flux.just("Last"))
      .log()

    StepVerifier.create(allString)
      .expectNext("Sunil")
      .expectNext("linux")
      .expectNext("Last")
      .verifyComplete()
  }

  @Test
  fun `testing flux for assertion with error`(){
    val allString = Flux.just("Sunil","linux")
      .concatWith(Flux.error(RuntimeException("Error Occurred Here")))
      .concatWith(Flux.just("Last"))
      .log()

    StepVerifier.create(allString)
      .expectNext("Sunil")
      .expectNext("linux")
      .expectError(RuntimeException::class.java)
      .verify()
  }

  @Test
  fun `testing flux for assertion with error message`(){
    val allString = Flux.just("Sunil","linux")
      .concatWith(Flux.error(RuntimeException("Error Occurred Here")))
      .concatWith(Flux.just("Last"))
      .log()

    StepVerifier.create(allString)
      .expectNext("Sunil","linux")
      .expectErrorMessage("Error Occurred Here")
      .verify()
  }

  @Test
  fun `testing flux for assertion number of element count`(){
    val allString = Flux.just("Sunil", "Yadav", "linux")
      .concatWith(Flux.error(RuntimeException("Error Occurred Here")))
      .concatWith(Flux.just("Last"))
      .log()

    StepVerifier.create(allString)
      .expectNextCount(2)
      .expectNext("linux")
      .expectErrorMessage("Error Occurred Here")
      .verify()
  }


  @Test
  fun `testing mono`(){
    val allString = Mono.just("Sunil")
      .log()

    StepVerifier.create(allString)
      .expectNext("Sunil")
      .verifyComplete()
  }

  @Test
  fun `testing mono with concate`(){
    val allString = Mono.just("Sunil")
      .concatWith(Flux.just("Last"))
      .log()

    StepVerifier.create(allString)
      .expectNext("Sunil")
      .expectNext("Last")
      .verifyComplete()
  }

  @Test
  fun `testing mono with error`(){
    val allString = Mono.error<RuntimeException>(RuntimeException("Some Error"))
      .log()

    StepVerifier.create(allString)
      .expectErrorMessage("Some Error")
      .verify()
  }


  @Test
  fun `testing mono with error 2`(){
    val allString = Mono.error<RuntimeException>(RuntimeException("Some Error"))
      .log()

    StepVerifier.create(allString)
      .verifyError()
  }


}