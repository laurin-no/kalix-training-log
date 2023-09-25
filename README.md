kalix-training-log

An example implementation of a strength training log implemented with [Kalix](https://www.kalix.io/).

## Use Case Description

Athletes can be created in the system, and then they can start strength training workouts and log the done work
(exercise name, reps and weight) for each of the sets they have been doing. The system also supports removing sets and
fetching the data for a workout or listing all finished workouts for an athlete. After finishing a workout, the
athletes current training status (fatigue, fitness and readiness) is automatically calculated based on the done
workouts. The athletes can be also listed with their current training status details.

## Implementation Details

There is two main entities in the system: `Athlete` and `Workout`. The `Athlete` entity is a value entity and `Workout`
is an Event Sourced Entity. For automatically calculating the training status, an action is subscribed to
the `WorkoutFinished` events, which fetches the finished workouts for the athlete via the `FinishedWorkoutByAthlete`
view and forwards this data to the `Athlete` entity. The `Athlete` entity then calculates the training status and stores
it in the state. Additionally, the information stored in the `Athlete` entity can be queried from the `AthletesListing`
view. 