package controllers

import scala.concurrent.{ExecutionContext, Future}
import com.google.inject.{Inject, Singleton}
import io.circe.{Printer, parser}
import io.circe.syntax._
import model._
import model.implicits._
import play.api.libs.ws.{WSClient, WSResponse}
import play.api.mvc._

@Singleton
class WeatherController @Inject()(
  ws: WSClient,
  implicit val ec: ExecutionContext,
  val controllerComponents: ControllerComponents
) extends BaseController with Configs {

  def get(lat: String, lon: String) = Action.async { implicit request =>
    Validator.validate(lat, lon).fold(
      left => Future.successful(errorToResult(left)),
      _ => getResponse(lat, lon)
    )
  }

  def errorToResult(error: WeatherError): Result = error match {
    case e: ValidationError => BadRequest(e.message)
    case e: HttpError => InternalServerError(e.message)
  }

  val printer = Printer.noSpaces.copy(dropNullValues = true)

  def getResponse(lat: String, lon: String): Future[Result] = {
    getWeatherData(lat, lon).map { response =>
      println(s"response=$response")
      response.status match {
        case 200 =>
          parser.decode[WeatherData](response.body) match {
            case Right(data) =>
              val utcTime = System.currentTimeMillis() / 1000
              Ok(printer.print(data.generateResponse(utcTime).asJson))
            case Left(e: io.circe.Error) =>
              InternalServerError(s"Unable to parse weather data; ${e.getMessage}")
          }
        case s =>
          InternalServerError(s"Weather service error $s; message=${response.body}")
      }
    }
  }

  def getWeatherData(lat: String, lon: String): Future[WSResponse] = {
    val request = ws.url(config.url)
      .addQueryStringParameters("lat" -> lat)
      .addQueryStringParameters("lon" -> lon)
      .addQueryStringParameters("appid" -> config.apiKey)
      .addQueryStringParameters("exclude" -> "minutely,hourly,daily")
      .addQueryStringParameters("units" -> "imperial")

    request.get()
  }

}

