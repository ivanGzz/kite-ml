name := "kite-rs"

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies += "org.deeplearning4j" % "deeplearning4j-core" % "0.6.0"