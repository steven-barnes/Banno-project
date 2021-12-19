package model

import io.circe.parser
import model.implicits._
import org.junit.Assert._
import org.scalatest.EitherValues
import org.scalatest.funsuite.AnyFunSuite

import scala.io.Source
import scala.util.Using

class ParseSpec extends AnyFunSuite with EitherValues {

  test("model should parse openweather JSON structure") {
    val text: String = Using.resource(getClass.getResourceAsStream("/response.json")) { stream =>
      Source.fromInputStream(stream).mkString
    }

    val data = parser.decode[WeatherData](text)
    println(data)
    assert(data.isRight)
  }

  test("model should parse JSON with no alerts") {
    val data = parser.decode[WeatherData](jsonNoAlerts)
    assertTrue(data.isRight)
    assertEquals(None, data.value.alerts)
  }

  val jsonNoAlerts =
    """
      |{
      |  "lat": 37.8715,
      |  "lon": -122.273,
      |  "timezone": "America/Los_Angeles",
      |  "timezone_offset": -28800,
      |  "current": {
      |    "dt": 1639888572,
      |    "sunrise": 1639840785,
      |    "sunset": 1639875126,
      |    "temp": 279.12,
      |    "feels_like": 279.12,
      |    "pressure": 1020,
      |    "humidity": 83,
      |    "dew_point": 276.46,
      |    "uvi": 0,
      |    "clouds": 40,
      |    "visibility": 10000,
      |    "wind_speed": 0.45,
      |    "wind_deg": 248,
      |    "wind_gust": 0.45,
      |    "weather": [
      |      {
      |        "id": 802,
      |        "main": "Clouds",
      |        "description": "scattered clouds",
      |        "icon": "03n"
      |      }
      |    ]
      |  }
      |}""".stripMargin

}
