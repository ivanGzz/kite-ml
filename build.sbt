name := "kite-rs"

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies += "org.nd4j" % "nd4j-native-platform" % "0.4.0"
libraryDependencies += "org.deeplearning4j" % "deeplearning4j-core" % "0.6.0"
libraryDependencies += "edu.stanford.nlp" % "stanford-corenlp" % "3.5.2" artifacts (Artifact("stanford-corenlp", "models"), Artifact("stanford-corenlp"))
libraryDependencies += "org.postgresql" % "postgresql" % "9.4-1200-jdbc41"
libraryDependencies += "com.typesafe.play" %% "play-slick" % "1.1.0"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "1.1.0"