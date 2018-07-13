
lazy val akkaHttpVersion = "10.0.9"
lazy val akkaVersion    = "2.5.3"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization    := "org.ababup1192",
      scalaVersion    := "2.12.2"
    )),
    name := "log-department-store",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"         % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-xml"     % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-stream"       % akkaVersion,

      "org.slf4j" % "slf4j-api" % "1.7.22",
      "ch.qos.logback" % "logback-classic" % "1.1.7",
      "io.spray" %%  "spray-json" % "1.3.4",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",

      "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
      "org.scalatest"     %% "scalatest"         % "3.0.1"         % Test
    )
  )

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)
enablePlugins(AshScriptPlugin)

mainClass in Compile := Some("org.ababup1192.WebServerHttpApp")

dockerBaseImage       := "openjdk:jre-alpine"
