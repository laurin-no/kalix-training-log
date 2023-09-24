package com.pellervoinen.domain

import com.google.protobuf.empty.Empty
import com.pellervoinen
import kalix.scalasdk.testkit.ValueEntityResult
import kalix.scalasdk.valueentity.ValueEntity
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CounterSpec
    extends AnyWordSpec
    with Matchers {

  "Counter" must {

    "have example test that can be removed" in {
      val service = CounterTestKit(new Counter(_))
      pending
      // use the testkit to execute a command
      // and verify final updated state:
      // val result = service.someOperation(SomeRequest)
      // verify the reply
      // val reply = result.getReply()
      // reply shouldBe expectedReply
      // verify the final state after the command
      // service.currentState() shouldBe expectedState
    }

    "handle command Increase" in {
      val service = CounterTestKit(new Counter(_))
      pending
      // val result = service.increase(pellervoinen.IncreaseValue(...))
    }

    "handle command Decrease" in {
      val service = CounterTestKit(new Counter(_))
      pending
      // val result = service.decrease(pellervoinen.DecreaseValue(...))
    }

    "handle command Reset" in {
      val service = CounterTestKit(new Counter(_))
      pending
      // val result = service.reset(pellervoinen.ResetValue(...))
    }

    "handle command GetCurrentCounter" in {
      val service = CounterTestKit(new Counter(_))
      pending
      // val result = service.getCurrentCounter(pellervoinen.GetCounter(...))
    }

  }
}
