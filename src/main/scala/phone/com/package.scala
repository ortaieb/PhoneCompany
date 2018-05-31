package phone

import java.time.{Duration, LocalTime}

/**
  */
package object com {

  type ClientId = String

  type PhoneNumber = String

  type CallDuration = Duration


  class StringDurationConv(val str: String) {
    def toDuration = Duration.ZERO.withSeconds(LocalTime.parse(str).toSecondOfDay)
  }

  implicit def toDuration(str: String) = new StringDurationConv(str)

}
