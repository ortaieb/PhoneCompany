package phone.com

import java.time.Duration

import org.scalatest.{FlatSpec, Matchers}

/**
  */
class PackageSpec extends FlatSpec with Matchers {

  "toDurations" should "parse string to java.time.Duration" in {
    "01:02:10".toDuration shouldBe Duration.parse("PT1H2M10S")
  }

}
