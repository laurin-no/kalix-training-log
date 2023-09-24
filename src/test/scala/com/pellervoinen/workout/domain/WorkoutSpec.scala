package com.pellervoinen.workout.domain

import com.google.protobuf.empty.Empty
import com.pellervoinen.workout
import kalix.scalasdk.eventsourcedentity.EventSourcedEntity
import kalix.scalasdk.testkit.EventSourcedResult
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

class WorkoutSpec extends AnyWordSpec with Matchers {
  "The Workout" should {
    "have example test that can be removed" in {
      val testKit = WorkoutTestKit(new Workout(_))
      pending
      // use the testkit to execute a command:
      // val result: EventSourcedResult[R] = testKit.someOperation(SomeRequest("id"));
      // verify the emitted events
      // val actualEvent: ExpectedEvent = result.nextEventOfType[ExpectedEvent]
      // actualEvent shouldBe expectedEvent
      // verify the final state after applying the events
      // testKit.state() shouldBe expectedState
      // verify the reply
      // result.reply shouldBe expectedReply
      // verify the final state after the command
    }

    "correctly process commands of type Create" in {
      val testKit = WorkoutTestKit(new Workout(_))
      pending
      // val result: EventSourcedResult[Empty] = testKit.create(workout.CreateWorkoutCommand(...))
    }

    "correctly process commands of type AddSet" in {
      val testKit = WorkoutTestKit(new Workout(_))
      pending
      // val result: EventSourcedResult[Empty] = testKit.addSet(workout.AddSetCommand(...))
    }

    "correctly process commands of type RemoveSet" in {
      val testKit = WorkoutTestKit(new Workout(_))
      pending
      // val result: EventSourcedResult[Empty] = testKit.removeSet(workout.RemoveSetCommand(...))
    }

    "correctly process commands of type GetWorkout" in {
      val testKit = WorkoutTestKit(new Workout(_))
      pending
      // val result: EventSourcedResult[workout.Workout] = testKit.getWorkout(workout.GetWorkoutCommand(...))
    }

    "correctly process commands of type FinishWorkout" in {
      val testKit = WorkoutTestKit(new Workout(_))
      pending
      // val result: EventSourcedResult[Empty] = testKit.finishWorkout(workout.FinishWorkoutCommand(...))
    }
  }
}
