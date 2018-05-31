package phone.com

import java.time.Duration

import org.scalatest.{Matchers, WordSpec}

import scala.io.Source


/**
  */
class PhoneCompanySpec extends WordSpec with Matchers {

  def shortCalls(duration: CallDuration) = {
    Duration.ofMinutes(3).compareTo(duration) > 0
  }

  def longCalls(duration: CallDuration) = {
    Duration.ofMinutes(3).compareTo(duration) <= 0
  }

  def fromString(input: String): Source = io.Source.fromString(input)

  val phoneCompany = new PhoneCompany(fromString, RawCallRecord.parse)

  "phone company" when {

    "dailyProcess" should {

      "calculate and return charges map" in {
        val rawContent =
          """
            |A 000-000-000 00:01:00
            |A 000-001-000 00:04:00
            |A 000-001-010 00:04:00
          """.stripMargin
        phoneCompany.dailyProcess(rawContent) shouldBe Map("A" -> BigDecimal(10.20))
      }

      "calculate while skipping invalid input records" in {
        val rawContent =
          """
            |A 000-000-000 00:01:00
            |A 000-001-000 00:04:00
            |A 000-TTT-000 00:04:00
            |A 000-001-010 00:04:00
          """.stripMargin
        phoneCompany.dailyProcess(rawContent) shouldBe Map("A" -> BigDecimal(10.20))
      }

      "calculate more than single customer data" in {
        val rawContent =
          """
            |A 000-000-000 00:01:00
            |A 000-001-000 00:04:00
            |B 000-001-000 00:01:00
            |B 000-001-200 00:01:00
            |A 000-TTT-000 00:04:00
            |A 000-001-010 00:04:00
          """.stripMargin
        phoneCompany.dailyProcess(rawContent) shouldBe Map("A" -> BigDecimal(10.20), "B" -> BigDecimal(3.0))
      }

    }

  }

}
