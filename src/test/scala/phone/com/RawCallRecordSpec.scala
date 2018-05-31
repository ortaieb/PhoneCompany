package phone.com

import java.time.Duration

import phone.com.RawCallRecord.parse
import org.scalatest.{EitherValues, Matchers, WordSpec}

/**
  */
class RawCallRecordSpec
  extends WordSpec
    with Matchers
    with EitherValues {

  "RawCallRecord" when {

    "parse" should {

      "fail with error when input is empty" in {
        val line = ""
        parse(line).left.value shouldBe s"Input[${line}] does not match [Client PhoneNumber Duration] format"
      }

      "fail with error where input has less than three tokens" in {
        val line = "A 555-663-111"
        parse(line).left.value shouldBe s"Input[${line}] does not match [Client PhoneNumber Duration] format"
      }

      "fail with error where input has more than three tokens" in {
        val line = "A 555-663-111 00 02 03"
        parse(line).left.value shouldBe s"Input[${line}] does not match [Client PhoneNumber Duration] format"
      }

      "fail with error where input with two spaces" in {
        val line = "    "
        parse(line).left.value shouldBe s"Input[${line}] does not match [Client PhoneNumber Duration] format"
      }

      "fail with error where input have invalid phone number format" in {
        val line = "A 555-AAA-000 00:00:30"
        parse(line).left.value shouldBe s"Invalid PhoneNumber input [555-AAA-000]"
      }

      "fail with error where input have invalid call duration format" in {
        val line = "A 555-999-000 00:s0:m0"
        parse(line).left.value shouldBe s"Invalid CallDuration input [00:s0:m0]"
      }

      "create instance for three token input" in {
        parse("A 555-663-111 00:02:03").right.value shouldBe RawCallRecord("A", "555-663-111", Duration.parse("PT2M3S"))
      }
    }

  }


}
