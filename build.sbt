lazy val phoneCompany = (project in file(".")).settings(
  Seq(
    name := "disco-test-phone-company",
    version := "1.0",
    scalaVersion := "2.12.3"
  )
)

libraryDependencies ++= {
  val scalaTestV  = "3.0.5"

  Seq(
    "org.scalactic"   %% "scalactic" % scalaTestV,
    "org.scalatest"   %% "scalatest" % scalaTestV % "test"
  )
}