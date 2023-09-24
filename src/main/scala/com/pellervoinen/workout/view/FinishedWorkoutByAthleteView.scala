package com.pellervoinen.workout.view

import com.google.protobuf.any.{ Any => ScalaPbAny }
import com.pellervoinen.workout.Workout
import com.pellervoinen.workout.domain.WorkoutFinished
import kalix.scalasdk.view.View.UpdateEffect
import kalix.scalasdk.view.ViewContext

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

class FinishedWorkoutByAthleteView(context: ViewContext) extends AbstractFinishedWorkoutByAthleteView {

  override def emptyState: Workout = Workout.defaultInstance

  override def processWorkoutFinished(state: Workout, workoutFinished: WorkoutFinished): UpdateEffect[Workout] = {
    val finished = Workout(
      workoutId = workoutFinished.id,
      sets = workoutFinished.sets.map(set =>
        com.pellervoinen.workout.SetItem(
          setId = set.id,
          exerciseName = set.exerciseName,
          reps = set.reps,
          weight = set.weight,
          timestamp = set.timestamp)),
      startTime = workoutFinished.startTime,
      endTime = workoutFinished.endTime,
      username = workoutFinished.username,
      exhaustionRating = workoutFinished.exhaustionRating)

    effects.updateState(finished)
  }
}
