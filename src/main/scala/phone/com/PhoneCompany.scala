package phone.com

import scala.io.Source

/**
  */
class PhoneCompany(retrieval: String => Source,
                   parser: String => Either[String, RawCallRecord]) extends CustomerCharges {

  def dailyProcess(filename: String) = {
    val rawWithValidationError = CallsLogReader.fileContent(filename)(retrieval, parser)

    inputIntegrity(rawWithValidationError)
      .groupBy(raw => raw.clientId)
      .mapValues(calculateDaily(_)(rec => rec.callDuration.getSeconds)(charges))

  }

  def inputIntegrity(raw: List[Either[String, RawCallRecord]]): List[RawCallRecord] = {
    raw collect {
      case Right(record) => record
    }
  }

}

