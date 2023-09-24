package com.pellervoinen.athlete.actions

import akka.stream.Materializer
import akka.stream.scaladsl.Sink
import com.google.protobuf.empty.Empty
import com.pellervoinen.athlete.CalculateTrainingImpactCommand
import com.pellervoinen.workout.domain.WorkoutFinished
import com.pellervoinen.workout.view.ByNameRequest
import com.pellervoinen.athlete
import kalix.scalasdk.action.Action
import kalix.scalasdk.action.ActionCreationContext

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

class WorkoutEventSubscriptionAction(creationContext: ActionCreationContext)
    extends AbstractWorkoutEventSubscriptionAction {

  override def onWorkoutFinished(workoutFinished: WorkoutFinished): Action.Effect[Empty] = {
    implicit val mat: Materializer = creationContext.materializer()

    val workoutService =
      actionContext.getGrpcClient(classOf[com.pellervoinen.workout.view.FinishedWorkoutByAthlete], "workouts")

    val workouts = workoutService
      .getWorkouts(ByNameRequest(workoutFinished.username))
      .map(w =>
        athlete.Workout(
          w.workoutId,
          w.sets.map(s => athlete.SetItem(s.setId, s.exerciseName, s.reps, s.weight, s.timestamp)),
          w.startTime,
          w.endTime,
          w.username,
          w.exhaustionRating))
      .runWith(Sink.seq)

    val call = workouts.map(w =>
      effects.forward(
        components.athlete.calculateTrainingImpact(CalculateTrainingImpactCommand(workoutFinished.username, w))))

    effects.asyncEffect(call)
  }
}
