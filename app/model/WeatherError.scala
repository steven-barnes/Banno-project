package model

sealed trait WeatherError

case class ValidationError(message: String) extends WeatherError
case class HttpError(status: Int, message: String) extends WeatherError

