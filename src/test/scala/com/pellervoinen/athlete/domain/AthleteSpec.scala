package com.pellervoinen.athlete.domain

import com.google.protobuf.empty.Empty
import com.pellervoinen.athlete
import kalix.scalasdk.testkit.ValueEntityResult
import kalix.scalasdk.valueentity.ValueEntity
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class AthleteSpec extends AnyWordSpec with Matchers {

  "Athlete" must {

    "handle command CreateAthlete" in {
      val service = AthleteTestKit(new Athlete(_))

      val createCommand = athlete.CreateAthleteCommand(username = "arnold.schwarzenegger", birthYear = 1990)
      val creationResult = service.createAthlete(createCommand)

      val expectedState = AthleteState(username = "arnold.schwarzenegger", birthYear = 1990)

      creationResult.reply shouldBe Empty.defaultInstance
      creationResult.updatedState shouldBe expectedState
    }

    "handle command GetAthlete" in {

      val service = AthleteTestKit(new Athlete(_))

      val createCommand = athlete.CreateAthleteCommand(username = "arnold.schwarzenegger", birthYear = 1990)
      service.createAthlete(createCommand)

      val getCommand = athlete.GetAthleteCommand(username = "arnold.schwarzenegger")
      val getResult = service.getAthlete(getCommand)

      val expectedResult = athlete.Athlete(username = "arnold.schwarzenegger", birthYear = 1990)

      getResult.reply shouldBe expectedResult
    }

  }
}
