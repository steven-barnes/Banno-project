package model

case class WeatherData(
  lat: Double,
  lon: Double,
  timezone: String,
  timezone_offset: Int,
  current: Current,
  rain: Option[Rain],
  alerts: Option[List[Alert]]
) {

  def generateResponse(utcTime: Long): WeatherResponse = {
    val conditions = current.weather.headOption.map(_.description).getOrElse("clear sky")
    val temp = current.feels_like match {
      case t if t < 60f => "cold"
      case t if t < 80f => "moderate"
      case _ => "hot"
    }
    val as = alerts.map { as =>
      as.filter(_.isActive(utcTime)).map(_.description)
    }
    WeatherResponse(conditions, temp, as)
  }

}

case class Rain(
  `1h`: String
)

case class Current(
  dt: Long,
  sunrise: Long,
  sunset: Long,
  temp: Double,
  feels_like: Double,
  pressure: Double,
  humidity: Double,
  dew_point: Double,
  uvi: Double,
  clouds: Int,
  visibility: Int,
  wind_speed: Double,
  wind_deg: Int,
  wind_gust: Double,
  weather: List[Weather]
)

case class Weather(
  id: Int,
  main: String,
  description: String,
  icon: String
)

case class Alert(
  sender_name: String,
  event: String,
  start: Long,
  end: Long,
  description: String,
  tags: List[String]
) {
  def isActive(utcTime: Long): Boolean = {
    start <= utcTime && end >= utcTime
  }
}
