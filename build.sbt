name := "Akka-DistributedPubSub"

version := "1.0"

scalaVersion := "2.11.7"

lazy val akkaVersion = "2.4.0"

libraryDependencies ++=
  Seq(
    "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
    "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion
  )

libraryDependencies <+= scalaVersion("org.scala-lang" % "scala-compiler" % _ )