package phone.com

import java.time.Duration

import org.scalatest.{Matchers, WordSpec}

/**
  */
class CustomerChargesSpec extends WordSpec with Matchers {

  val durationInSeconds: RawCallRecord => Long =
    record => record.callDuration.getSeconds

  val identity: PartialFunction[Long, BigDecimal] = {
    case in if in >= 0 => BigDecimal(in)
  }

  val customerChargesImpl = new Object with CustomerCharges

  "CustomerCharges" when {

    "calculateDaily" should {

      "return zero for empty input" in {
        customerChargesImpl.calculateDaily(List())(durationInSeconds)(identity) shouldBe BigDecimal(0L)
      }

      "return zero for single element input" in {
        val input = List(RawCallRecord("A", "000-000-000", Duration.ofSeconds(30)))
        customerChargesImpl.calculateDaily(input)(durationInSeconds)(identity) shouldBe BigDecimal(0L)
      }

      "calculate minus grater for input with more than one element" in {
        val input = List(
          RawCallRecord("A", "000-000-000", Duration.ofSeconds(200)),
          RawCallRecord("A", "000-000-001", Duration.ofSeconds(100)),
          RawCallRecord("A", "000-000-000", Duration.ofSeconds(300))
        )
        customerChargesImpl.calculateDaily(input)(durationInSeconds)(identity) shouldBe BigDecimal(300)
      }

      "calculate minus grater only once for input with more than one max element" in {
        val input = List(
          RawCallRecord("A", "000-000-000", Duration.ofSeconds(200)),
          RawCallRecord("A", "000-000-001", Duration.ofSeconds(300)),
          RawCallRecord("A", "000-000-000", Duration.ofSeconds(300))
        )
        customerChargesImpl.calculateDaily(input)(durationInSeconds)(identity) shouldBe BigDecimal(500)
      }
    }

  }

}
