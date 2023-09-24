package com.pellervoinen.workout.domain

import com.google.protobuf.empty.Empty
import com.pellervoinen.workout
import kalix.scalasdk.eventsourcedentity.EventSourcedEntity
import kalix.scalasdk.eventsourcedentity.EventSourcedEntityContext

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

class Workout(context: EventSourcedEntityContext) extends AbstractWorkout {
  override def emptyState: WorkoutState = WorkoutState.defaultInstance

  override def create(currentState: WorkoutState, createWorkoutCommand: workout.CreateWorkoutCommand): EventSourcedEntity.Effect[Empty] =
    effects.error("The command handler for `Create` is not implemented, yet")

  override def addSet(currentState: WorkoutState, addSetCommand: workout.AddSetCommand): EventSourcedEntity.Effect[Empty] =
    effects.error("The command handler for `AddSet` is not implemented, yet")

  override def removeSet(currentState: WorkoutState, removeSetCommand: workout.RemoveSetCommand): EventSourcedEntity.Effect[Empty] =
    effects.error("The command handler for `RemoveSet` is not implemented, yet")

  override def getWorkout(currentState: WorkoutState, getWorkoutCommand: workout.GetWorkoutCommand): EventSourcedEntity.Effect[workout.Workout] =
    effects.error("The command handler for `GetWorkout` is not implemented, yet")

  override def finishWorkout(currentState: WorkoutState, finishWorkoutCommand: workout.FinishWorkoutCommand): EventSourcedEntity.Effect[Empty] =
    effects.error("The command handler for `FinishWorkout` is not implemented, yet")

  override def workoutFinished(currentState: WorkoutState, workoutFinished: WorkoutFinished): WorkoutState =
    throw new RuntimeException("The event handler for `WorkoutFinished` is not implemented, yet")

  override def setAdded(currentState: WorkoutState, setAdded: SetAdded): WorkoutState =
    throw new RuntimeException("The event handler for `SetAdded` is not implemented, yet")

  override def setRemoved(currentState: WorkoutState, setRemoved: SetRemoved): WorkoutState =
    throw new RuntimeException("The event handler for `SetRemoved` is not implemented, yet")

  override def workoutCreated(currentState: WorkoutState, workoutCreated: WorkoutCreated): WorkoutState =
    throw new RuntimeException("The event handler for `WorkoutCreated` is not implemented, yet")

}
