package model

case class WeatherResponse(conditions: String, temperature: String, alerts: Option[List[String]])
