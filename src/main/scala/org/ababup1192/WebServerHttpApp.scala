package org.ababup1192

import akka.event.jul.Logger
import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport.defaultNodeSeqMarshaller
import akka.http.scaladsl.server.{ HttpApp, Route }
import akka.event.Logging
import com.typesafe.scalalogging.LazyLogging

/**
 * Server will be started calling `WebServerHttpApp.startServer("localhost", 8080)`
 * and it will be shutdown after pressing return.
 */
object WebServerHttpApp extends HttpApp with LazyLogging with App {
  println(getClass().getName())
  // Routes that this WebServer must handle are defined here
  // Please note this method was named `route` in versions prior to 10.0.7
  def routes: Route =
    pathEndOrSingleSlash { // Listens to the top `/`
      complete("Server up and running") // Completes with some text
    } ~
      path("trace") {
        get { // Listens only to GET requests
          logger.trace("traceでーす")
          complete("trace log") // Completes with some text
        }
      } ~
      path("debug") {
        get { // Listens only to GET requests
          logger.debug("debugでーす")
          complete("debug log") // Completes with some text

        }
      } ~
      path("info") {
        get { // Listens only to GET requests
          logger.info("infoでーす")
          complete("info log") // Completes with some text

        }
      } ~
      path("warn") {
        get { // Listens only to GET requests
          logger.warn("warnでーす")
          complete("warn log") // Completes with some text

        }
      } ~
      path("error") {
        get { // Listens only to GET requests
          logger.error("errorでーす")
          complete("error log") // Completes with some text

        }
      }

  // This will start the server until the return key is pressed
  startServer("0.0.0.0", 8080)
}

