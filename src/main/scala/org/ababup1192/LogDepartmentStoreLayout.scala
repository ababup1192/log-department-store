package org.ababup1192

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.pattern.ThrowableProxyConverter
import scala.collection.mutable.LinkedHashMap
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.LayoutBase

class LogDepartmentStoreLayout extends LayoutBase[ILoggingEvent] with LayoutHelper {
  var mdcList: Seq[String] = Seq.empty
  override def doLayout(event: ILoggingEvent): String = {
    implicit val _event = event

    val msg = sanitize(event.getMessage)

    val layout = LinkedHashMap(
      "time" -> isoFormattedTimestamp(event.getTimeStamp),
      "thread_s" -> event.getThreadName,
      "level_s" -> event.getLevel.levelStr,
      "logger_s" -> sanitize(event.getLoggerName),
      "msg_txt" -> msg,
      "ex" -> sanitize(new ThrowableProxyConverter().convert(event)),
      "serviceVersion" -> Option(System.getProperty("service.version")).getOrElse("unknown"),
      "instanceName" -> sanitize(Option(System.getProperty("logging.hostname")).getOrElse("unknown")),
      "instanceId" -> sanitize(Option(System.getProperty("logging.instanceid")).getOrElse("unknown")),
      "caller" -> caller(Level.WARN, 5),
      "log_id" -> caller(Level.INFO),
      "corporateId" -> mdc("corporateId"),
      "hrMedia" -> mdc("hrMedia")
    )

    mdcList.foreach { mdcKey =>
      layout.put(mdcKey, mdc(mdcKey))
    }

    asJsonString(layout)
  }
}
