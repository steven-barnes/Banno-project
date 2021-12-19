package model

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

object implicits {
  implicit lazy val decoderWeatherData: Decoder[WeatherData] = deriveDecoder[WeatherData]
  implicit lazy val decoderCurrent: Decoder[Current] = deriveDecoder[Current]
  implicit lazy val decoderRain: Decoder[Rain] = deriveDecoder[Rain]
  implicit lazy val decoderWeather: Decoder[Weather] = deriveDecoder[Weather]
  implicit lazy val decoderAlert: Decoder[Alert] = deriveDecoder[Alert]

  implicit lazy val encodeWeatherResponse: Encoder[WeatherResponse] = deriveEncoder[WeatherResponse]

}
