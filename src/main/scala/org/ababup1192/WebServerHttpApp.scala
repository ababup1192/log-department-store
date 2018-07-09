package org.ababup1192

import akka.event.jul.Logger
import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport.defaultNodeSeqMarshaller
import akka.http.scaladsl.server.{ HttpApp, Route }

/**
 * Server will be started calling `WebServerHttpApp.startServer("localhost", 8080)`
 * and it will be shutdown after pressing return.
 */
object WebServerHttpApp extends HttpApp with App {
  val logger = Logger("name")

  // Routes that this WebServer must handle are defined here
  // Please note this method was named `route` in versions prior to 10.0.7
  def routes: Route =
    pathEndOrSingleSlash { // Listens to the top `/`
      complete("Server up and running") // Completes with some text
    } ~
      path("finest") {
        get { // Listens only to GET requests
          logger.finest("finestでーす")
          complete("finest log") // Completes with some text
        }
      } ~
      path("finer") {
        get { // Listens only to GET requests
          logger.finer("finerでーす")
          complete("finer log") // Completes with some text

        }
      } ~
      path("fine") {
        get { // Listens only to GET requests
          logger.fine("fineでーす")
          complete("fine log") // Completes with some text

        }
      } ~
      path("config") {
        get { // Listens only to GET requests
          logger.config("configでーす")
          complete("config log") // Completes with some text

        }
      } ~
      path("info") {
        get { // Listens only to GET requests
          logger.info("infoでーす")
          complete("warning log") // Completes with some text

        }
      } ~
      path("warning") {
        get { // Listens only to GET requests
          logger.warning("warningでーす")
          complete("warning log") // Completes with some text

        }
      } ~
      path("severe") {
        get { // Listens only to GET requests
          logger.severe("severeでーす")
          complete("severe log") // Completes with some text

        }
      }

  // This will start the server until the return key is pressed
  startServer("0.0.0.0", 8080)
}

