package phone.com

object Main extends App {

  val dailyCharges =
    new PhoneCompany(CallsLogReader.fromFile, CallsLogReader.callParser)
      .dailyProcess(filePathToProcess(args))

  dailyCharges.toList.foreach(e => println(s"${e._1} : ${(e._2)}"))

  def filePathToProcess(args: Array[String]) =
    if (args.length < 1) {
      println("No calls.log input file, Expected \"Main <path to file>\"")
      println("Will default to packaged sample")

      "./target/scala-2.12/classes/calls.log"
    } else {
      args(0)
    }
}
