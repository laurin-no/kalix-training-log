package com.pellervoinen.athlete.domain

import com.google.protobuf.empty.Empty
import com.pellervoinen.athlete
import com.pellervoinen.athlete.{ CalculateTrainingImpactCommand, Workout }
import kalix.scalasdk.valueentity.ValueEntity
import kalix.scalasdk.valueentity.ValueEntityContext

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

class Athlete(context: ValueEntityContext) extends AbstractAthlete {
  override def emptyState: AthleteState = AthleteState.defaultInstance

  override def createAthlete(
      currentState: AthleteState,
      createAthleteCommand: athlete.CreateAthleteCommand): ValueEntity.Effect[Empty] = {

    val newState = currentState.copy(
      username = createAthleteCommand.username,
      birthYear = createAthleteCommand.birthYear,
      fatigueScore = 0,
      fitnessScore = 0,
      readinessScore = 0)

    currentState.toEmptyState
      .map(_ => newState)
      .updateState(Empty.defaultInstance)
  }

  override def getAthlete(
      currentState: AthleteState,
      getAthleteCommand: athlete.GetAthleteCommand): ValueEntity.Effect[athlete.Athlete] = {
    currentState.toNonEmptyState
      .map(state =>
        athlete.Athlete(state.username, state.birthYear, state.fatigueScore, state.fitnessScore, state.readinessScore))
      .fold(error => effects.error(error), effects.reply(_))
  }

  override def calculateTrainingImpact(
      currentState: AthleteState,
      calculateTrainingImpactCommand: CalculateTrainingImpactCommand): ValueEntity.Effect[Empty] = {

    currentState.toNonEmptyState
      .map(_.calculateTrainingImpact(calculateTrainingImpactCommand.workouts))
      .updateState(Empty.defaultInstance)
  }

  // Error Handling
  private type Error = String

  private implicit class StateOps(val state: AthleteState) {
    def toEmptyState: Either[Error, AthleteState] = {
      Either.cond(state == AthleteState.defaultInstance, state, "The state must be empty")
    }

    def toNonEmptyState: Either[Error, AthleteState] = {
      Either.cond(state != AthleteState.defaultInstance, state, "The state must not be empty")
    }

    def calculateTrainingImpact(workouts: Seq[Workout]): AthleteState = {
      val fatigue = workouts.map(_.exhaustionRating).sum
      val fitness = workouts.map(_.sets.map(i => i.reps * i.weight).sum).sum
      val readiness = fitness - fatigue

      state.copy(fatigueScore = fatigue, fitnessScore = fitness, readinessScore = readiness)
    }
  }

  private implicit class EitherOps(val either: Either[Error, AthleteState]) {
    def updateState[T](reply: T): ValueEntity.Effect[T] = {
      either.fold(error => effects.error(error), state => effects.updateState(state).thenReply(reply))
    }
  }
}
