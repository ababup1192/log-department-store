package org.ababup1192

import java.time._
import java.time.format.DateTimeFormatter
import scala.collection.mutable.LinkedHashMap

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import spray.json._
import spray.json.DefaultJsonProtocol._

trait LayoutHelper {
  private def lineSeparator = """\\\\n"""
  private val TEMP_LINE_SEPARATOR = s"___TEMP_LINE_SEP_${LocalDate.now()}___" //仮の改行

  def isoFormattedTimestamp(timeStamp: Long): String = {
    val datetime = Instant.ofEpochMilli(timeStamp).atZone(ZoneOffset.UTC).toOffsetDateTime()
    DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(datetime)
  }

  def asLtsvString(layout: LinkedHashMap[String, String]): String = {
    val list = layout.toSeq.map(kv => s"${kv._1}:${kv._2}")
    list.mkString("\t") + "\tltsv:end\n"
  }

  def asJsonString(layout: LinkedHashMap[String, String]): String = {
    val layoutMap: Map[String, String] = layout.toMap
    layoutMap.toJson.compactPrint + System.lineSeparator()
  }

  def sanitize(text: String): String = {
    val lineSepratorNormalized = text.replaceAll(lineSeparator, "") //サニタイズ後に改行として扱う文字列があったら消す
    val ltsvEscapedString = lineSepratorNormalized.replaceAll("\n", TEMP_LINE_SEPARATOR).replaceAll("\t", "    ").replaceAll("\"", "").replaceAll("'", "") //LTSV的な制御文字の削除
    val escapedString = ltsvEscapedString.replaceAll("\\p{Cntrl}", "").replaceAll("""\\""", "") //制御文字とエスケープ文字を消す
    escapedString.replaceAll(TEMP_LINE_SEPARATOR, lineSeparator) // 仮の改行をエスケープした改行に戻す
  }

  def mdc(key: String)(implicit event: ILoggingEvent): String = {
    sanitize(Option(event.getMDCPropertyMap.get(key)).getOrElse(""))
  }

  def caller(levelForOutput: Level, depth: Int = 1)(implicit event: ILoggingEvent): String = {
    sanitize(if (levelForOutput.levelInt <= event.getLevel.levelInt) {
      val cda = event.getCallerData.take(depth)
      cda.map { c =>
        s"${c.getFileName}-${c.getMethodName}-${c.getLineNumber}"
      }.mkString("\n")
    } else {
      ""
    })
  }
}
