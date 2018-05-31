package phone.com

/**
  */
case class RawCallRecord(clientId: ClientId,
                         phoneNumber: PhoneNumber,
                         callDuration: CallDuration)

object RawCallRecord {

  def parse(input: String): Either[String, RawCallRecord] = {

    import RawCallValidator._

    val delimiter = """\s"""

    def splitInput(input: String): Either[String, (String, String, String)] =
      input.split(delimiter) match {
        case Array(clientIdStr, phoneNumberStr, callDurationStr) => Right(clientIdStr, phoneNumberStr, callDurationStr)
        case _ => Left(s"Input[${input}] does not match [Client PhoneNumber Duration] format")
      }

    for {
      spiltedInput <- splitInput(input)
      (strClientId, strPhoneNumber, strCallduration) = spiltedInput

      vClientId <- validClientId(strClientId)
      vPhoneNum <- validPhoneNumber(strPhoneNumber)
      vCallduration <- validCallDuration(strCallduration)
    } yield RawCallRecord(vClientId, vPhoneNum, vCallduration)

  }

  object RawCallValidator {

    val phoneNumberPattern = """[0-9]{3}\-[0-9]{3}\-[0-9]{3}""".r
    val callDurationPattern = """[0-9]{2}\:[0-9]{2}\:[0-9]{2}""".r

    def validClientId(input: String): Either[String, String] = input.trim match {
      case "" => Left("Invalid ClientId input")
      case validClientId => Right(validClientId)
    }

    def validPhoneNumber(input: String): Either[String, String] = input match {
      case phoneNumberPattern() => Right(input)
      case _ => Left(s"Invalid PhoneNumber input [${input}]")
    }

    def validCallDuration(input: String): Either[String, CallDuration] = input match {
      case callDurationPattern() => Right(input.toDuration)
      case _ => Left(s"Invalid CallDuration input [${input}]")
    }

  }

}
