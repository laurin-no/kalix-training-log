package com.pellervoinen.workout.domain

import cats.syntax.all._
import com.google.protobuf.empty.Empty
import com.pellervoinen.workout
import com.pellervoinen.workout.SetItem
import kalix.scalasdk.eventsourcedentity.{ EventSourcedEntity, EventSourcedEntityContext }

import java.time.Instant
import java.util.UUID
import scala.language.implicitConversions

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

class Workout(context: EventSourcedEntityContext) extends AbstractWorkout {
  override def emptyState: WorkoutState = WorkoutState.defaultInstance

  // Command Handlers

  override def create(
      currentState: WorkoutState,
      createWorkoutCommand: workout.CreateWorkoutCommand): EventSourcedEntity.Effect[Empty] = {
    val event = WorkoutCreated(
      id = createWorkoutCommand.workoutId,
      startTime = Instant.now().getEpochSecond,
      username = createWorkoutCommand.username)

    currentState.toEmptyState.toEffect(event, _ => Empty.defaultInstance)
  }

  override def addSet(
      currentState: WorkoutState,
      addSetCommand: workout.AddSetCommand): EventSourcedEntity.Effect[Empty] = {
    val event = SetAdded(
      id = addSetCommand.workoutId,
      set = Set(
        UUID.randomUUID().toString,
        addSetCommand.exerciseName,
        addSetCommand.reps,
        addSetCommand.weight,
        Instant.now().getEpochSecond).some)

    currentState.toNonEmptyState.toEffect(event, _ => Empty.defaultInstance)
  }

  override def removeSet(
      currentState: WorkoutState,
      removeSetCommand: workout.RemoveSetCommand): EventSourcedEntity.Effect[Empty] = {
    val event = for {
      state <- currentState.toNonEmptyState
      _ <- state.getSet(removeSetCommand.setId)

    } yield SetRemoved(id = removeSetCommand.setId)

    event.eventToEffect(_ => Empty.defaultInstance)
  }

  override def getWorkout(
      currentState: WorkoutState,
      getWorkoutCommand: workout.GetWorkoutCommand): EventSourcedEntity.Effect[workout.Workout] = {

    effects.reply(currentState.toApi)
  }

  override def finishWorkout(
      currentState: WorkoutState,
      finishWorkoutCommand: workout.FinishWorkoutCommand): EventSourcedEntity.Effect[Empty] = {
    val event = for {
      _ <- currentState.toFinishableState
      _ <- validateFinishWorkoutCommand(finishWorkoutCommand)
    } yield WorkoutFinished(
      id = finishWorkoutCommand.workoutId,
      sets = currentState.sets,
      startTime = currentState.startTime,
      endTime = Instant.now().getEpochSecond,
      username = currentState.username,
      exhaustionRating = finishWorkoutCommand.exhaustionRating)

    event.eventToEffect(_ => Empty.defaultInstance)
  }

  // Event Handlers

  override def workoutFinished(currentState: WorkoutState, workoutFinished: WorkoutFinished): WorkoutState = {
    currentState.copy(endTime = workoutFinished.endTime, exhaustionRating = workoutFinished.exhaustionRating)
  }

  override def setAdded(currentState: WorkoutState, setAdded: SetAdded): WorkoutState = {
    currentState.copy(sets = currentState.sets ++ setAdded.set.toSeq)
  }

  override def setRemoved(currentState: WorkoutState, setRemoved: SetRemoved): WorkoutState = {
    currentState.copy(sets = currentState.sets.filterNot(_.id == setRemoved.id))
  }

  override def workoutCreated(currentState: WorkoutState, workoutCreated: WorkoutCreated): WorkoutState = {
    currentState.copy(id = workoutCreated.id, startTime = workoutCreated.startTime, username = workoutCreated.username)
  }

  // Error Handling
  private type Error = String

  private implicit class StateOps(val state: WorkoutState) {
    def toNonEmptyState: Either[Error, WorkoutState] = {
      Either.cond(state != WorkoutState.defaultInstance, state, "The state must not be empty")
    }

    def toEmptyState: Either[Error, WorkoutState] = {
      Either.cond(state == WorkoutState.defaultInstance, state, "The state must be empty")
    }

    def toFinishableState: Either[Error, WorkoutState] = {
      Either.cond(state.sets.nonEmpty, state, "The workout must be have at least one set")
    }

    def getSet(setId: String): Either[Error, Set] = {
      state.sets.find(_.id == setId).toRight("Set not found")
    }

    def toApi: workout.Workout = workout.Workout(
      workoutId = state.id,
      sets = state.sets.map(_.toApi),
      startTime = state.startTime,
      endTime = state.endTime,
      username = state.username,
      exhaustionRating = state.exhaustionRating)
  }

  private implicit class SetOps(val set: Set) {
    def toApi: SetItem = workout.SetItem(
      setId = set.id,
      exerciseName = set.exerciseName,
      reps = set.reps,
      weight = set.weight,
      timestamp = set.timestamp)
  }

  private implicit class EitherOps[A](val either: Either[Error, A]) {
    def toEffect[E <: Object, T](event: E, reply: WorkoutState => T): EventSourcedEntity.Effect[T] = {
      either.fold(errorMessage => effects.error(errorMessage), _ => effects.emitEvent(event).thenReply(reply))
    }
  }

  private implicit class EitherEventOps[E <: Object](val either: Either[Error, E]) {
    def eventToEffect[T](reply: WorkoutState => T): EventSourcedEntity.Effect[T] = {
      either.fold(errorMessage => effects.error(errorMessage), a => effects.emitEvent(a).thenReply(reply))
    }
  }

  private def validateFinishWorkoutCommand(
      command: workout.FinishWorkoutCommand): Either[Error, workout.FinishWorkoutCommand] = {
    val isValid = !command.workoutId.isBlank && command.exhaustionRating > 0

    Either.cond(
      isValid,
      workout.FinishWorkoutCommand(),
      "The command must include a valid workoutId and exhaustionRating")
  }

}
