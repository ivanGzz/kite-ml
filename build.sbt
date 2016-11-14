name := "kite-rs"

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies += "org.nd4j" % "nd4j-native-platform" % "0.4.0"
libraryDependencies += "org.deeplearning4j" % "deeplearning4j-core" % "0.6.0"