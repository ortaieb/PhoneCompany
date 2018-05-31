package phone.com

import java.time.Duration

import org.scalatest.{EitherValues, Matchers, WordSpec}
import phone.com.RawCallRecord.RawCallValidator._

class RawCallValidatorSpec
  extends WordSpec
    with Matchers
    with EitherValues {

  "RawCallValidator" when {

    "validClientId" should {

      "return left error for empty input" in {
        validClientId("").leftSide shouldBe Left("Invalid ClientId input")
      }

      "return left error for whitespaces input" in {
        validClientId("     ").leftSide shouldBe Left("Invalid ClientId input")
      }

      "return right with input if non empty input" in {
        val input = "A"
        validClientId(input) shouldBe Right(input)
      }

    }

    "validPhoneNumber" should {

      "return left error for empty input" in {
        val input = ""
        validPhoneNumber(input).leftSide shouldBe Left(s"Invalid PhoneNumber input [${input}]")
      }

      "return left error for wrong patterned input" in {
        val input = "000-000-00s"
        validPhoneNumber(input).leftSide shouldBe Left(s"Invalid PhoneNumber input [${input}]")
      }

      "return right with phone number string" in {
        val input = "000-000-000"
        validPhoneNumber(input) shouldBe Right(input)
      }

    }

    "validCallDuration" should {

      "return left error for empty input" in {
        val input = ""
        validCallDuration(input).leftSide shouldBe Left(s"Invalid CallDuration input [${input}]")
      }

      "return left error for wrong patterned input" in {
        val input = "00:xx:00"
        validCallDuration(input).leftSide shouldBe Left(s"Invalid CallDuration input [${input}]")
      }

      "return right wrapped CallDuration patterned for valid input" in {
        validCallDuration("00:03:21") shouldBe Right(Duration.parse("PT3M21S"))
      }

    }

  }

}
