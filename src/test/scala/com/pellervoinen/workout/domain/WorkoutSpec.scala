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
    "correctly process commands of type Create" in {
      val testKit = WorkoutTestKit(new Workout(_))

      val createCommand = workout.CreateWorkoutCommand(workoutId = "workoutId", username = "username")
      val creationResult = testKit.create(createCommand)

      val actualEvent = creationResult.nextEvent[WorkoutCreated]
      val expectedEvent = WorkoutCreated(id = "workoutId", username = "username")

      val expectedState = WorkoutState(id = "workoutId", username = "username", sets = Seq.empty)

      creationResult.events should have size 1
      testKit.allEvents should have size 1

      actualEvent.id shouldBe expectedEvent.id
      actualEvent.username shouldBe expectedEvent.username

      testKit.currentState.id shouldBe expectedState.id
      testKit.currentState.username shouldBe expectedState.username

      creationResult.reply shouldBe Empty.defaultInstance
    }
  }
}
