package phone.com

import scala.io.Source

/**
  */
object CallsLogReader {

  def fileContent[A](filename: String)
                    (retrieval: String => Source, parser: String => A): List[A] =
    Control.using(retrieval(filename)) { source =>
      source.getLines().map(parser(_)).toList
    }

  def fromFile(filename: String): Source = io.Source.fromFile(filename)

  def callParser(raw: String) = RawCallRecord.parse(raw)

  object Control {
    def using[A <: {def close() : Unit}, B](resource: A)(f: A => B): B =
      try {
        f(resource)
      } finally {
        resource.close()
      }
  }

}
