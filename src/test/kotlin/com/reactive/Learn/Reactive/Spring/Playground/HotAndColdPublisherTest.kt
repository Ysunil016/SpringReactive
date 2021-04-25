package com.reactive.Learn.Reactive.Spring.Playground

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import java.time.Duration

class HotAndColdPublisherTest{

  @Test
  fun `cold publisher test`(){

    val finiteFlux = Flux
      .just("A","B","C","D","E","F")
      .delayElements(Duration.ofSeconds(1))

    finiteFlux.subscribe{
      println("Subscriber One Value $it")
    }

    finiteFlux.subscribe{
      println("Subscriber Two Value $it")
    }

    Thread.sleep(8000)
  }

  @Test
  fun `hot publisher test`(){

    val finiteFlux = Flux
      .just("A","B","C","D","E","F")
      .delayElements(Duration.ofSeconds(1))

    val connectableFlux = finiteFlux.publish()
    connectableFlux.connect()

    connectableFlux.subscribe{
      println("Subscriber One Value $it")
    }

    Thread.sleep(4000)
    // Starting From the Published Elements

    connectableFlux.subscribe{
      println("Subscriber Two Value $it")
    }

    Thread.sleep(8000)
  }

}