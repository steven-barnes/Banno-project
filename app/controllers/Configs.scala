package controllers

import model.Config

trait Configs {

  // TODO: load from conf file
  val config = Config(
    apiKey = "3fc55c81f31733944aefe900e4efb42f",
    url = "https://api.openweathermap.org/data/2.5/onecall"
  )
}


