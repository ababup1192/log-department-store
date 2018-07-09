package org.ababup1192.routes

import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport.defaultNodeSeqMarshaller
import akka.http.scaladsl.server.directives.MethodDirectives.get
import akka.http.scaladsl.server.directives.PathDirectives.path
import akka.http.scaladsl.server.directives.RouteDirectives.complete

/**
 * Routes can be defined in separated classes like shown in here
 */
trait SimpleRoutes {

  // This `val` holds one route (of possibly many more that will be part of your Web App)
  lazy val simpleRoutes =
    path("hello") { // Listens to paths that are exactly `/hello`
      get { // Listens only to GET requests
        complete("OK") // Completes with some html page
      }
    }
}
