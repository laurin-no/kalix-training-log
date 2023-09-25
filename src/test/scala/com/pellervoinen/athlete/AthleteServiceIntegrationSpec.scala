package com.pellervoinen.athlete

import com.pellervoinen.Main
import kalix.scalasdk.testkit.KalixTestKit
import org.scalatest.BeforeAndAfterAll
import org.scalatest.concurrent.Eventually.eventually
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import org.scalatest.time.{ Millis, Seconds, Span }
import org.scalatest.wordspec.AnyWordSpec

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

class AthleteServiceIntegrationSpec extends AnyWordSpec with Matchers with BeforeAndAfterAll with ScalaFutures {

  implicit private val patience: PatienceConfig =
    PatienceConfig(Span(5, Seconds), Span(500, Millis))

  private val testKit = KalixTestKit(Main.createKalix()).start()

  private val client = testKit.getGrpcClient(classOf[AthleteService])

  "AthleteService" must {

    "create and list athletes" in {
      val createAthleteCommandOne = CreateAthleteCommand("arnold.schwarzenegger", 1990)
      val createAthleteCommandTwo = CreateAthleteCommand("rocky", 1992)

      client.createAthlete(createAthleteCommandOne).futureValue
      client.createAthlete(createAthleteCommandTwo).futureValue

      val getAthleteCommandOne = GetAthleteCommand("arnold.schwarzenegger")
      val getAthleteCommandTwo = GetAthleteCommand("rocky")

      eventually {
        client.getAthlete(getAthleteCommandOne).futureValue shouldBe Athlete("arnold.schwarzenegger", 1990)
        client.getAthlete(getAthleteCommandTwo).futureValue shouldBe Athlete("rocky", 1992)
      }
    }

  }

  override def afterAll(): Unit = {
    testKit.stop()
    super.afterAll()
  }
}
