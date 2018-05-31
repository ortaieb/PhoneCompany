package phone.com

import org.scalatest.{FlatSpec, Matchers}

import scala.io.Source

/**
  */
class CallsLogReaderSpec extends FlatSpec with Matchers {

  def fromString(input: String): Source = io.Source.fromString(input)

  def idParser(raw: String): String = raw


  "CallsLogReader" should "return empty list for empty content" in {
    CallsLogReader.fileContent("")(fromString, idParser) shouldBe List()
  }

  it should "return list with entry per line of source" in {
    val content =
      """line1
        |line2
        |line3""".stripMargin

    CallsLogReader.fileContent(content)(fromString, idParser) shouldBe List("line1", "line2", "line3")
  }
}
