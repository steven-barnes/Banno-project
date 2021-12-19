package controllers

import model.{ValidationError, WeatherError}

import scala.util.Try

object Validator {

  def validate(lat: String, lon: String): Either[WeatherError, Unit] = {
    for {
      _ <- validateLatitude(lat)
      _ <- validateLongitude(lon)
    } yield ()
  }

  def validateLatitude(l: String) = isValidCoord(l, 90, "latitude")

  def validateLongitude(l: String) = isValidCoord(l, 180, "longitude")

  def isValidCoord(s: String, max: Float, name: String): Either[WeatherError, Unit] = {
    lazy val invalid = s"Invalid $name"
    val coord = Try(s.toFloat).toEither.left.map(_ => invalid)
    coord.fold(
      left => Left(ValidationError(left)),
      right => if (right >= -max && right <= max)
        Right(())
      else
        Left(ValidationError(invalid))
    )
  }

}
